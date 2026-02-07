package utils;

import io.restassured.RestAssured;

public class ApiConfig {

    public static void setBaseURI() {
        RestAssured.baseURI = "http://localhost:8081";
    }

}
