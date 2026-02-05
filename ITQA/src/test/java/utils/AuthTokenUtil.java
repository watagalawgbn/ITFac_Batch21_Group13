package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthTokenUtil {

    public static String getAdminToken() {
        Response response = RestAssured
            .given()
                .contentType("application/json")
                .body("{\"username\":\"admin\",\"password\":\"admin123\"}")
            .post("/api/auth/login");

        return response.jsonPath().getString("token");
    }

    public static String getUserToken() {
        Response response = RestAssured
            .given()
                .contentType("application/json")
                .body("{\"username\":\"testuser\",\"password\":\"test123\"}")
            .post("/api/auth/login");

        return response.jsonPath().getString("token");
    }
}
