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

    @Before
    public void setup() {
        RestAssured.baseURI = "http://localhost:8081";
    }

    @Given("{string} token is available")
    public void token_is_available(String role) {
        if (role.equalsIgnoreCase("Admin")) {
            token = AuthTokenUtil.getAdminToken();
        } else {
            token = AuthTokenUtil.getUserToken();
        }
        Assert.assertNotNull(token, role + " token should not be null");
    }

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

    @Then("Response status should be {int}")
    public void response_status_should_be(int expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
    }

    // ✅ Handles both JSON objects (summary) and JSON arrays (lists)
    @Then("Response should contain summary data for {string}")
    public void response_should_contain_summary_data(String type) {
        Object json = response.jsonPath().get("$"); // Get root JSON
        if (json instanceof Map) {
            // JSON Object
            Map<String, Object> summary = (Map<String, Object>) json;
            Assert.assertNotNull(summary, "Summary response should not be null");
            Assert.assertFalse(summary.isEmpty(), type + " summary should not be empty");
        } else if (json instanceof List) {
            // JSON Array
            List<Object> list = (List<Object>) json;
            Assert.assertNotNull(list, "List response should not be null");
            Assert.assertTrue(list.size() > 0, type + " list should not be empty");
        } else {
            Assert.fail("Unexpected response type for " + type);
        }
    }

    // ✅ For list APIs explicitly
    @Then("Response should contain list data for {string}")
    public void response_should_contain_list_data(String type) {
        List<Object> list = response.jsonPath().getList("$");
        Assert.assertNotNull(list, "List response should not be null");
        Assert.assertTrue(list.size() > 0, type + " list should not be empty");
    }
}
