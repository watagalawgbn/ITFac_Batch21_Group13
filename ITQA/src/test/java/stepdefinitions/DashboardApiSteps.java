package stepdefinitions;

import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.AuthTokenUtil;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class DashboardApiSteps {

    private String token;
    private Response response;

    // Base URI setup for all API requests
    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    // Get token for Admin or User
    @Given("{string} token is available")
    public void token_is_available(String role) {
        switch (role.toLowerCase()) {
            case "admin":
                token = AuthTokenUtil.getAdminToken();
                break;
            case "user":
                token = AuthTokenUtil.getUserToken();
                break;
            default:
                Assert.fail("Invalid role provided: " + role);
        }
        Assert.assertNotNull(token, role + " token should not be null");
    }

    // Send GET request to API endpoint
    @When("{string} sends GET request to {string}")
    public void sends_get_request(String role, String endpoint) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .header("Accept", "application/json")
                .log().all()
            .when()
                .get(endpoint)
            .then()
                .log().all()
                .extract().response();
    }

    // Validate HTTP response status
    @Then("Response status should be {int}")
    public void response_status_should_be(int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus, "Unexpected HTTP status code");
    }

    // Validate summary data for category/plant/sales APIs
    @Then("Response should contain summary data for {string}")
    public void response_should_contain_summary_data(String type) {
        Object json = response.jsonPath().get("$");

        if (json instanceof Map<?, ?> summary) {
            // JSON Object (Summary API)
            Assert.assertFalse(summary.isEmpty(), type + " summary should not be empty");
        } else if (json instanceof List<?> list) {
            // JSON Array (fallback)
            Assert.assertFalse(list.isEmpty(), type + " list should not be empty");
        } else {
            Assert.fail("Unexpected response type for " + type);
        }
    }

    // Validate list data for list APIs
    @Then("Response should contain list data for {string}")
    public void response_should_contain_list_data(String type) {
        List<Object> list = response.jsonPath().getList("$");
        Assert.assertNotNull(list, type + " list response should not be null");
        Assert.assertFalse(list.isEmpty(), type + " list should not be empty");
    }
}
