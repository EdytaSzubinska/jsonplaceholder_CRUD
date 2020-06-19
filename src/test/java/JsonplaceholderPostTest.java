import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class JsonplaceholderPostTest {

    private final String BASE_URL = "http://localhost:3000";
    private final String PHOTOS = "photos";

    private static Faker faker;
    private String fakeTitle;
    private String fakeUrl;
    private String fakeThumbnailUrl;
    private Integer fakeAlbumId;

    @BeforeAll
    public static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    public void beforeEach() {
        fakeTitle = faker.name().title();
        fakeUrl = faker.internet().url();
        fakeAlbumId = faker.number().randomDigit();
        fakeThumbnailUrl = faker.internet().url();
    }

    @Test
    public void jsonplaceholderCreateNewPhoto() {

        JSONObject photo = new JSONObject();
        photo.put("albumId", fakeAlbumId);
        photo.put("title", fakeTitle);
        photo.put("url", fakeUrl);
        photo.put("thumbnailUrl", fakeThumbnailUrl);

//            System.out.println(photo.toString());

        Response response = given()
                .contentType("application/json")
                .body(photo.toString())
                .when()
                .post(BASE_URL + "/" + PHOTOS)
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        assertEquals(fakeAlbumId, json.get("albumId"));
        assertEquals(fakeTitle, json.get("title"));
        assertEquals(fakeUrl, json.get("url"));
        assertEquals(fakeThumbnailUrl, json.get("thumbnailUrl"));
        assertEquals("5001", json.get("id").toString());
    }
}

