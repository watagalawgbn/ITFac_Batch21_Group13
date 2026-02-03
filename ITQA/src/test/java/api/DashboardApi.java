package api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class DashboardApi {

    public static Response getCategorySummary(String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/api/categories/summary");
    }
}
