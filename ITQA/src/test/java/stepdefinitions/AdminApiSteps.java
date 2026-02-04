package stepdefinitions;

import io.cucumber.java.en.*;
import io.restassured.response.Response;
import utils.ApiConfig;

import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.testng.Assert.*;

public class AdminApiSteps {

    private Response response;
    private String adminToken;
    private final Long CATEGORY_ID = 4L;
    private String plantName;
    private Long plantId;

    @Given("admin API base URL is set")
    public void admin_api_base_url_is_set() {
        ApiConfig.setBaseURI();
    }

    @And("admin token is available")
    public void admin_token_is_available() {

        adminToken =
                given()
                        .header("Content-Type", "application/json")
                        .body("""
                    {
                      "username": "admin",
                      "password": "admin123"
                    }
                """)
                        .when()
                        .post("/api/auth/login")
                        .then()
                        .statusCode(200)
                        .extract()
                        .path("token");

        assertNotNull(adminToken, "Admin token is missing");

        System.out.println("Admin token generated successfully");
    }

    @When("admin sends GET request to {string}")
    public void admin_sends_get_request_to(String endpoint) {
        response =
                given()
                        .header("Authorization", "Bearer " + adminToken)
                        .header("Content-Type", "application/json")
                        .when()
                        .get(endpoint);
    }

    @Then("admin receives status code {int}")
    public void admin_receives_status_code(Integer statusCode) {
        assertEquals(response.getStatusCode(), statusCode.intValue(),
                "Unexpected status code");
    }

    @And("admin receives list of plants in response")
    public void admin_receives_list_of_plants_in_response() {
        assertNotNull(response.getBody(), "Response body is null");

        // Assuming response is a JSON array
        int plantCount = response.jsonPath().getList("$").size();

        assertTrue(plantCount > 0, "Plant list is empty");

        System.out.println("Total plants returned: " + plantCount);
    }

    @When("admin sends POST request to add a plant")
    public void admin_sends_post_request_to_add_a_plant() {
        // Generate a unique plant name to avoid duplicates
        plantName = "Plant_" + UUID.randomUUID().toString().substring(0, 8);

        String requestBody = """
        {
          "id": 0,
          "name": "%s",
          "price": 50,
          "quantity": 30,
          "category": {
            "id": %d,
            "name": "Flowers",
            "parent": null,
            "subCategories": []
          }
        }
        """.formatted(plantName, CATEGORY_ID);

        response =
                given()
                        .header("Authorization", "Bearer " + adminToken)
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/plants/category/" + CATEGORY_ID);

        // Save the created plant ID for later update
        plantId = response.jsonPath().getLong("id");
        System.out.println("Created plant ID: " + plantId);
    }

    @Then("plant should be created successfully with status code {int}")
    public void plant_should_be_created_successfully_with_status_code(Integer statusCode) {
        assertEquals(response.getStatusCode(), statusCode.intValue(),
                "Unexpected status code");

        String returnedName = response.jsonPath().getString("name");
        Integer quantity = response.jsonPath().getInt("quantity");

        assertEquals(returnedName, plantName, "Plant name mismatch");
        assertEquals(quantity, 30, "Plant quantity mismatch");

        System.out.println("Plant created successfully: " + returnedName);
    }

    @When("admin sends POST request to add a plant with duplicate name")
    public void admin_sends_post_request_to_add_plant_with_duplicate_name() {
        // Use an existing plant name from the database
        plantName = "Anthurium"; // or any plant already in DB

        String requestBody = """
        {
          "id": 0,
          "name": "%s",
          "price": 50,
          "quantity": 30,
          "category": {
            "id": %d,
            "name": "Flowers",
            "parent": null,
            "subCategories": []
          }
        }
        """.formatted(plantName, CATEGORY_ID);

        response =
                given()
                        .header("Authorization", "Bearer " + adminToken)
                        .header("Content-Type", "application/json")
                        .body(requestBody)
                        .when()
                        .post("/api/plants/category/" + CATEGORY_ID);
    }

    @Then("API should return status code {int}")
    public void api_should_return_status_code(Integer statusCode) {
        assertEquals(response.getStatusCode(), statusCode.intValue(),
                "Unexpected status code");
    }

    @Then("duplicate name validation message is returned")
    public void duplicate_name_validation_message_is_returned() {
        String message = response.jsonPath().getString("message");
        assertNotNull(message, "Validation message is missing");
        assertTrue(message.toLowerCase().contains("duplicate") || message.toLowerCase().contains("exists"),
                "Expected duplicate validation message, got: " + message);

        System.out.println("Duplicate name validation message: " + message);
    }

    @When("admin sends PUT request to update an existing plant")
    public void admin_sends_put_request_to_update_existing_plant() {

        // If plantId is null, fetch the last created plant dynamically
        if (plantId == null) {
            response = given()
                    .header("Authorization", "Bearer " + adminToken)
                    .header("Content-Type", "application/json")
                    .when()
                    .get("/api/plants");

            plantId = response.jsonPath().getLong("[-1].id"); // get the last plant ID
            plantName = response.jsonPath().getString("[-1].name");

            System.out.println("Fetched last plant ID for update: " + plantId);
        }

        plantName = plantName + "_Updated"; // Change name for update

        String updateRequest = """
    {
      "id": %d,
      "name": "%s",
      "price": 159,
      "quantity": 25
    }
    """.formatted(plantId, plantName);

        response =
                given()
                        .header("Authorization", "Bearer " + adminToken)
                        .header("Content-Type", "application/json")
                        .body(updateRequest)
                        .when()
                        .put("/api/plants/" + plantId);
    }

    @Then("plant should be updated successfully with status code {int}")
    public void plant_should_be_updated_successfully_with_status_code(Integer statusCode) {
        assertEquals(response.getStatusCode(), statusCode.intValue(),
                "Unexpected status code");
    }

    @Then("updated plant details are returned")
    public void updated_plant_details_are_returned() {

        String returnedName = response.jsonPath().getString("name");
        Double returnedPrice = response.jsonPath().getDouble("price");
        Integer returnedQuantity = response.jsonPath().getInt("quantity");

        assertEquals(returnedName, plantName, "Plant name mismatch after update");
        assertEquals(returnedPrice, 159.0, "Plant price mismatch after update");
        assertEquals(returnedQuantity.intValue(), 25, "Plant quantity mismatch after update");

        System.out.println("Plant updated successfully: " + returnedName);
    }
}
