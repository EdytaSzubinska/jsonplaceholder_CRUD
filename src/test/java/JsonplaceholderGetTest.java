import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonplaceholderGetTest {

    private final String BASE_URL = "http://localhost:3000";
    private final String PHOTOS = "photos";

    @Test
    public void jsonplaceholderReadAllPhotos() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + PHOTOS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        System.out.println(response.asString());

        JsonPath json = response.jsonPath();
        List<String> photos = json.getList("photos");
        assertEquals(5000, photos.size());

        photos.stream()
                .forEach(System.out::println);
    }

    @Test
    public void jsonplaceholderReadOnePhoto() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + PHOTOS + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("accusamus beatae ad facilis cum similique qui sunt", json.get("title"));
        assertEquals("https://via.placeholder.com/600/92c952", json.get("url"));
        assertEquals("https://via.placeholder.com/150/92c952", json.get("thumbnailUrl"));
    }

    @Test
    public void jsonplaceholderReadOnePhotosWithPathVariables() {

        Response response = given()
                .pathParam("albumId", 1)
                .when()
                .get(BASE_URL + "/" + PHOTOS + "/{albumId}")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("accusamus beatae ad facilis cum similique qui sunt", json.get("title"));
        assertEquals("https://via.placeholder.com/600/92c952", json.get("url"));
        assertEquals("https://via.placeholder.com/150/92c952", json.get("thumbnailUrl"));
    }

    @Test
    public void jsonplaceholderReadPhotosWithQueryParams() {

        Response response = given()
                .queryParam("title", "accusamus beatae ad facilis cum similique qui sunt")
                .when()
                .get(BASE_URL + "/" + PHOTOS)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        assertEquals("accusamus beatae ad facilis cum similique qui sunt", json.getList("title").get(0));
        assertEquals("https://via.placeholder.com/600/92c952", json.getList("url").get(0));
        assertEquals("https://via.placeholder.com/150/92c952", json.getList("thumbnailUrl").get(0));
    }
}
