import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class jsonplaceholderReadAllUsers {

    private final String BASE_URL = "http://localhost:3000";
    private final String USER = "users";

    @Test
    public void jsonplaceholderReadAllUsers() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + USER)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        System.out.println(response.asString());

        JsonPath json = response.jsonPath();
        List<String> user = json.getList("users");
        assertEquals(10, user.size());

        user.stream()
                .forEach(System.out::println);
    }
}
