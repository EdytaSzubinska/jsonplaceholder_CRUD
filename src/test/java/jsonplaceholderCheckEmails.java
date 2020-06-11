import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class jsonplaceholderCheckEmails {

    private final String BASE_URL = "http://localhost:3000";
    private final String USER = "users";

    @Test
    public void checkEmails() {

        Response response = given()
                .when()
                .get(BASE_URL + "/" + USER)
                .then()
                .statusCode(HttpStatus.SC_OK)
                .extract()
                .response();

        JsonPath json = response.jsonPath();

        List<String> emails = json.getList("email");

        boolean polishAddressOnList = emails.stream()
                .anyMatch(email -> email.endsWith("pl"));

        Assertions.assertFalse(polishAddressOnList);
    }
}
