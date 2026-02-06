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

    // ----------------- Token -----------------
    @Given("{string} token is available")
    public void token_is_available(String role) {
        token = AuthTokenUtil.authenticate(role.toLowerCase());
        assertNotNull(token, role + " token should not be null");
        System.out.println(role + " token: " + token);
    }

    // ----------------- GET request -----------------
    @When("{string} sends GET request to {string}")
    public void sends_get_sales(String role, String endpoint) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint);
    }

    @Then("Response status code should be {int}")
    public void response_status_code_should_be(Integer expectedStatus) {
        assertEquals(response.getStatusCode(), expectedStatus.intValue(),
                "Status code mismatch");
    }

    @Then("Response should contain sales list")
    public void response_should_contain_sales_list() {
        assertNotNull(response.jsonPath().getList("$"), "Sales list should not be null");
    }

    // ----------------- POST request -----------------
    @Given("A plant with available stock exists")
    public void a_plant_with_available_stock_exists() {
        plantId = 3L;
        assertNotNull(plantId);
    }

    @Given("A plant with no stock exists")
    public void a_plant_with_no_stock_exists() {
        plantId = 1L;
        assertNotNull(plantId);
    }

    @When("{string} sends POST request to create sale with quantity {int}")
    public void sends_post_request_create_sale(String role, int quantity) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("plantId", plantId)
                .queryParam("quantity", quantity)
                .when()
                .post("/api/sales/plant/{plantId}");

    }

    @When("{string} sends POST request to create sale with plantId {int} and quantity {int}")
    public void sends_post_request_to_create_sale_with_plantId_and_quantity(String role, int plantId, int quantity) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("plantId", plantId)
                .queryParam("quantity", quantity)
                .when()
                .post("/api/sales/plant/{plantId}");
    }

    @Then("Sale should be created via api successfully")
    public void sale_should_be_created_via_api_successfully() {
        saleId = response.jsonPath().getLong("id");
        assertNotNull(saleId, "Sale ID should be generated");
        assertTrue(response.jsonPath().getInt("quantity") > 0, "Sold quantity > 0");
    }

    @Then("Error message should indicate stock unavailability")
    public void error_message_should_indicate_stock_unavailability() {
        String msg = response.jsonPath().getString("message");
        assertTrue(msg.toLowerCase().contains("stock"));
    }

    @Then("Error message should indicate invalid quantity")
    public void error_message_should_indicate_invalid_quantity() {
        String msg = response.jsonPath().getString("message");
        assertTrue(msg.toLowerCase().contains("quantity"));
    }

    // ----------------- DELETE request -----------------
    @Given("A sale record exists")
    public void a_sale_record_exists() {
        plantId = 2L;
        response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("plantId", plantId)
                .queryParam("quantity", 1)
                .when()
                .post("/api/sales/plant/{plantId}");
        saleId = response.jsonPath().getLong("id");
        assertNotNull(saleId);
    }

    @When("{string} sends DELETE request for the sale")
    public void sends_delete_request_for_sale(String role) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("id", saleId)
                .when()
                .delete("/api/sales/{id}");
    }

    @Then("Sale should be deleted via api successfully")
    public void sale_should_be_deleted_via_api_successfully() {
        Response getResponse = given()
                .header("Authorization", "Bearer " + token)
                .pathParam("saleId", saleId)
                .when()
                .get("/api/sales/{saleId}");
        assertEquals(getResponse.getStatusCode(), 404);
    }

    // ----------------- GET Sales with pagination -----------------
    @When("{string} sends GET request to {string} with page {int} and size {int}")
    public void sends_get_request_with_page_and_size(String role, String endpoint, int page, int size) {
        response = given()
                .header("Authorization", "Bearer " + token)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", "soldAt")
                .when()
                .get(endpoint);
    }

    @Then("Response should contain maximum {int} sales records")
    public void response_should_contain_maximum_sales_records(Integer maxRecords) {
        List<Object> salesList = response.jsonPath().getList("content");
        assertNotNull(salesList, "Sales list should not be null");
        assertTrue(salesList.size() <= maxRecords,
                "Sales list should have at most " + maxRecords + " records");
    }

    @Then("Response should contain an empty sales list")
    public void response_should_contain_empty_sales_list() {
        List<Object> salesList = response.jsonPath().getList("content");
        assertNotNull(salesList, "Sales list should not be null");
        assertEquals(salesList.size(), 0, "Sales list should be empty");
    }

    @Then("Response should indicate access denied")
    public void response_should_indicate_access_denied() {
        String msg = response.jsonPath().getString("message");
        assertNotNull(msg, "Error message should not be null");
        assertTrue(msg.toLowerCase().contains("access") || msg.toLowerCase().contains("unauthorized"),
                "Message should indicate access denied or unauthorized");
    }
}
