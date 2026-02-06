package utils;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseApiTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8081";
    }
}