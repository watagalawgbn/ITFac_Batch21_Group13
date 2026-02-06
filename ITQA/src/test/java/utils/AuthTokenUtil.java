package utils;

import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthTokenUtil {

    private static String token;

    public static String authenticate(String role) {

        String username;
        String password;

        if (role.equalsIgnoreCase("admin")) {
            username = ConfigReader.get("admin.username");
            password = ConfigReader.get("admin.password");
        } else {
            username = ConfigReader.get("user.username");
            password = ConfigReader.get("user.password");
        }

        Response response =
                given()
                        .contentType("application/json")
                        .body(Map.of(
                                "username", username,
                                "password", password
                        ))
                        .when()
                        .post("/api/auth/login");

        token = response.jsonPath().getString("token");
        Assert.assertNotNull(token, role + " token should not be null");

        return token;
    }

    public static String getToken() {
        return token;
    }
}
