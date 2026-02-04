package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ApiConfig;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class UserApiSteps {

    private Response response;
    private String userToken;
    private final Long CATEGORY_ID = 4L;

    @Given("user API base URL is set")
    public void user_api_base_url_is_set() {
        ApiConfig.setBaseURI();
    }

    @And("user token is available")
    public void user_token_is_available() {

        userToken =
                given()
                        .header("Content-Type", "application/json")
                        .body("""
                    {
                      "username": "testuser",
                      "password": "test123"
                    }
                """)
                        .when()
                        .post("/api/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        assertNotNull(userToken, "User token is missing");
        System.out.println("User token generated successfully");
    }

    @When("user sends GET request to {string}")
    public void user_sends_get_request_to(String endpoint) {
        response =
                given()
                        .header("Authorization", "Bearer " + userToken)
                        .header("Content-Type", "application/json")
                        .when()
                        .get(endpoint);
    }

    @Then("user receives status code {int}")
    public void user_receives_status_code(Integer statusCode) {
        assertEquals(response.getStatusCode(), statusCode.intValue(),
                "Unexpected status code");
    }

    @And("user receives list of plants in response")
    public void user_receives_list_of_plants_in_response() {
        assertNotNull(response.getBody(), "Response body is null");

        int plantCount = response.jsonPath().getList("$").size();
        assertTrue(plantCount > 0, "Plant list is empty");

        System.out.println("User received plant list. Total plants: " + plantCount);
    }

    @When("user sends POST request to add a plant")
    public void user_sends_post_request_to_add_a_plant() {

        String requestBody = """
    {
      "id": 0,
      "name": "Unauthorized_Plant",
      "price": 50,
      "quantity": 10,
      "category": {
        "id": %d,
        "name": "Flowers",
        "parent": null,
        "subCategories": []
      }
    }
    """.formatted(CATEGORY_ID);

        response =
                given()
                        .header("Authorization", "Bearer " + userToken)
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/plants/category/" + CATEGORY_ID);
    }
}
