package utils;

import io.cucumber.java.Before;
import io.restassured.RestAssured;

import java.io.InputStream;
import java.util.Properties;

public class ApiHooks {

    @Before
    public void setUp() {
        RestAssured.baseURI = getBaseUrl();
    }

    private String getBaseUrl() {
        Properties properties = new Properties();
        try (InputStream input =
                     getClass().getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new RuntimeException("config.properties not found");
            }

            properties.load(input);
            return properties.getProperty("base.url");

        } catch (Exception e) {
            throw new RuntimeException("Failed to load base.url from config.properties", e);
        }
    }
}
