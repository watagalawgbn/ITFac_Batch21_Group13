package stepdefinitions.sales.api;

import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.AuthTokenUtil;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

public class AdminSalesApiSteps {

    private String token;
    private Response response;
    private Long plantId;
    private Long saleId;


    // TC-1: Get sales
    @Given("Admin token is available")
    public void admin_token_is_available() {
        token = AuthTokenUtil.authenticate("admin");
        assertNotNull(token, "Admin token should not be null");
    }

    @When("Admin sends GET request to {string}")
    public void admin_sends_get_request(String endpoint) {
        response =
                given()
                        .header("Authorization", "Bearer " + token)
                        .when()
                        .get(endpoint);
    }

    @Then("Response status code should be {int}")
    public void response_status_code_should_be(Integer expectedStatus) {
        assertEquals(
                response.getStatusCode(),
                expectedStatus.intValue(),
                "Status code mismatch"
        );
    }

    @Then("Response should contain sales list")
    public void response_should_contain_sales_list() {
        List<Object> salesList = response.jsonPath().getList("$");

        assertNotNull(salesList, "Sales list should not be null");
        assertTrue(salesList.size() > 0, "Sales list should not be empty");
    }

    // TC-3: Create sale
    @Given("A plant with available stock exists")
    public void a_plant_with_available_stock_exists() {
        // Temporary hardcoded plantId
        plantId = 2L;
        assertNotNull(plantId, "Plant ID should be available");
    }

    @When("Admin sends POST request to create sale with quantity {int}")
    public void admin_sends_post_request_to_create_sale_with_quantity(int quantity) {
        response =
                RestAssured
                        .given()
                        .header("Authorization", "Bearer " + token)
                        .pathParam("plantId", plantId)
                        .queryParam("quantity", quantity)
                        .when()
                        .post("/api/sales/plant/{plantId}");
    }

    @Then("Sale should be created successfully")
    public void sale_should_be_created_successfully() {
        Long saleId = response.jsonPath().getLong("id");
        assertNotNull(saleId, "Sale ID should be generated");

        Integer soldQuantity = response.jsonPath().getInt("quantity");
        assertTrue(soldQuantity > 0, "Sold quantity should be greater than zero");
    }

    //TC -4 - prevents sales when stocks is unavailable
    @Given("A plant with no stock exists")
    public void a_plant_with_no_stock_exists() {
        // Hardcoded plantId that has 0 stock in DB
        plantId = 1L;
        assertNotNull(plantId, "Plant ID with no stock should exist");
    }



    @Then("Error message should indicate stock unavailability")
    public void error_message_should_indicate_stock_unavailability() {
        String errorMessage = response.jsonPath().getString("message");

        assertNotNull(errorMessage, "Error message should not be null");
        assertTrue(
                errorMessage.toLowerCase().contains("stock"),
                "Error message should mention stock unavailability"
        );
    }

    //TC - 7
    @Then("Error message should indicate invalid quantity")
    public void error_message_should_indicate_invalid_quantity() {
        String errorMessage = response.jsonPath().getString("message");

        assertNotNull(errorMessage, "Error message should not be null");
        assertTrue(
                errorMessage.toLowerCase().contains("quantity"),
                "Error message should mention quantity validation"
        );
    }

    //TC - 9
    @Given("A sale record exists")
    public void a_sale_record_exists() {

        // Use plant with stock
        plantId = 2L;

        response =
                RestAssured
                        .given()
                        .header("Authorization", "Bearer " + token)
                        .pathParam("plantId", plantId)
                        .queryParam("quantity", 1)
                        .when()
                        .post("/api/sales/plant/{plantId}");

        assertEquals(response.getStatusCode(), 201, "Sale creation failed");

        saleId = response.jsonPath().getLong("id");
        assertNotNull(saleId, "Sale ID should be created");
    }

    @When("Admin sends DELETE request for the sale")
    public void admin_sends_delete_request_for_the_sale() {

        response =
                RestAssured
                        .given()
                        .header("Authorization", "Bearer " + token)
                        .pathParam("id", saleId)
                        .when()
                        .delete("/api/sales/{id}");
    }

    @Then("Sale should be deleted successfully")
    public void sale_should_be_deleted_successfully() {
        // Try to GET the deleted sale
        Response getResponse = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("saleId", saleId) // saleId saved from creation
                .when()
                .get("/api/sales/{saleId}");

        // Expect 404 Not Found
        assertEquals(getResponse.getStatusCode(), 404, "Sale was not deleted properly");
    }


}
