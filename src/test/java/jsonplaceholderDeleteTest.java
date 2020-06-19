import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class jsonplaceholderDeleteTest {

    private final String BASE_URL = "http://localhost:3000";
    private final String PHOTOS = "photos";

    @Test
    public void jsonplaceholderDeletePhoto() {

        Response response = given()
                .when()
                .delete(BASE_URL + "/" + PHOTOS + "/1")
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();
    }
}
