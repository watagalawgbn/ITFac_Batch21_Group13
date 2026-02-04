package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import org.testng.Assert;
import utils.AuthTokenUtil;

import static io.restassured.RestAssured.given;

public class DashboardApiSteps {

    private String token;
    private Response response;

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

    @Then("Response should contain summary data for {string}")
    public void response_should_contain_summary_data(String type) {
        // check at least one item is returned
        Assert.assertTrue(response.jsonPath().getList(type).size() > 0, type + " summary should not be empty");
    }

    @Then("Response should contain list data for {string}")
    public void response_should_contain_list_data(String type) {
        Assert.assertTrue(response.jsonPath().getList(type).size() > 0, type + " list should not be empty");
    }
}
