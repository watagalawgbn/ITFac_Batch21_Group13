package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthTokenUtil {

    // ===== Existing Admin Token Method (unchanged) =====
    public static String getAdminToken() {
        Response response = RestAssured
            .given()
                .contentType("application/json")
                .body("{\"username\":\"admin\",\"password\":\"admin123\"}")
            .post("/api/auth/login");

        return "Bearer " + response.jsonPath().getString("token");
    }

    // ===== New Method for User Token =====
    public static String getUserToken() {
        Response response = RestAssured
            .given()
                .contentType("application/json")
                .body("{\"username\":\"user\",\"password\":\"user123\"}")
            .post("/api/auth/login");

        return "Bearer " + response.jsonPath().getString("token");
    }
}
