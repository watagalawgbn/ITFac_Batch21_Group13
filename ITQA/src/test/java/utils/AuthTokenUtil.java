package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthTokenUtil {

    private static String token;

    /**
     * Authenticate a role (Admin/User) via API
     * Returns the JWT token
     */
    public static String authenticate(String role) {

        String username, password;

        if (role.equalsIgnoreCase("admin")) {
            username = ConfigReader.get("admin.username");
            password = ConfigReader.get("admin.password");
        } else {
            username = ConfigReader.get("user.username");
            password = ConfigReader.get("user.password");
        }

        RestAssured.baseURI = ConfigReader.get("base.url");

        Response authResponse = given()
                .contentType("application/json")
                .body(Map.of(
                        "username", username,
                        "password", password
                ))
                .when()
                .post("/api/auth/login");

        token = authResponse.jsonPath().getString("token");
        Assert.assertNotNull(token, role + " token should not be null");

        return token;
    }

    public static String getToken() {
        return token;
    }
}
