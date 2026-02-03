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

        return "Bearer " + response.jsonPath().getString("token");
    }
}