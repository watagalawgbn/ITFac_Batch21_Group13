package stepdefinitions.category.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import utils.ConfigReader;

import org.testng.Assert;

import utils.AuthTokenUtil;

import static io.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

public class CategoryApiSteps {

    private String token;
    private Response response;
    private String createdCategoryId;

    // ========== AUTHENTICATION ==========
    @Given("{string} is authenticated")
    public void user_or_admin_is_authenticated(String role) {
        token = AuthTokenUtil.authenticate(role);
        System.out.println(role + " token: " + token);
    }

    // ========== GET REQUEST ==========
    @When("{string} sends GET request to categories endpoint {string}")
    public void sends_get_categories(String role, String endpoint) {
        response =
            given()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token) 
                .log().all()
            .when()
                .get(endpoint)
            .then()
                .log().all()
                .extract().response();
    }

    // ========== POST REQUEST ==========
    @When("{string} sends POST request to {string} with category details {string}, {string}")
    public void sends_post_request(String role, String endpoint, String name, String parent) {
        
        // Only Admin can create categories
        if(role.equalsIgnoreCase("User")) {
            System.out.println("User role is not allowed to create categories. Sending request to test restriction.");
        }

        Map<String, Object> category = new HashMap<>();
        category.put("name", name);
        category.put("parent", parent);

        response =
            given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(category)
                .log().all()
            .when()
                .post(endpoint)
            .then()
                .log().all()
                .extract().response();
    }


    // ========== POST WITH EMPTY NAME ==========
    @When("{string} sends POST request to {string} with empty category name")
    public void sends_post_request_with_empty_name(String role, String endpoint) {
        Map<String, Object> category = new HashMap<>();
        category.put("name", "");
        category.put("parentName", "Roses");

        response =
            given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(category)
                .log().all()
            .when()
                .post(endpoint)
            .then()
                .log().all()
                .extract().response();
    }

    // ========== POST AS MAIN CATEGORY ==========
    @When("\"Admin\" sends POST request to {string} with category name {string} and empty parent")
    public void admin_sends_post_request_with_empty_parent(String endpoint, String categoryName) {

        Map<String, Object> category = new HashMap<>();
        category.put("name", categoryName);
        category.put("parentName", "");

        response =
            given()
                .header("Authorization", "Bearer " + token)
                .contentType("application/json")
                .body(category)
            .when()
                .post(endpoint)
            .then()
                .log().all()
                .extract().response();
    }


    // ========== UPDATE CATEGORY ==========
    @When("{string} updates category with hardcoded ID")
    public void updates_category_with_hardcoded_id(String role) {
        String categoryId = "17"; 

        Map<String, Object> parent = new HashMap<>();
        parent.put("id", 14); 

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("categoryname", "mini roses Updated");
        updateBody.put("parent", parent);

        
        if(role.equalsIgnoreCase("User")) {
            System.out.println("User role is not allowed to update categories. Sending request to test restriction.");
        }

        response =
            given()
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .body(updateBody)
                .log().all()
            .when()
                .put("/api/categories/" + categoryId)
            .then()
                .log().all()
                .extract().response();
    }

    // ========== DELETE CATEGORY ==========
    @When("{string} sends DELETE request to {string}")
    public void sends_delete_request(String role, String endpoint) {
        
        if(role.equalsIgnoreCase("User")) {
            System.out.println("User role is not allowed to delete categories. Sending request to test restriction.");
        }

        response =
            given()
                .header("Authorization", "Bearer " + token)
                .log().all()
            .when()
                .delete(endpoint)
            .then()
                .log().all()
                .extract().response();
    }

    // ========== GET CATEGORY BY ID ==========
    @When("{string} sends GET request to {string} with category ID {int}")
    public void sends_get_request_with_id(String role, String endpoint, Integer categoryId) {
        String url = endpoint.endsWith("/") ? endpoint + categoryId : endpoint + "/" + categoryId;

        response =
            given()
                .header("Accept", "application/json")
                .header("Authorization", "Bearer " + token)
                .log().all()
            .when()
                .get(url)
            .then()
                .log().all()
                .extract().response();
    }


    // ========== VERIFICATIONS ==========
    @Then("API response status should be {int}")
    public void api_response_status_should_be(Integer expectedStatus) {
        Assert.assertEquals(response.getStatusCode(), expectedStatus.intValue());
    }

    @Then("response should contain categories")
    public void response_should_contain_categories() {
        Assert.assertTrue(
            response.jsonPath().getList("$").size() > 0,
            "Category list should not be empty"
        );
    }

    @Then("response message should be {string}")
    public void response_message_should_be(String expectedMessage) {
        String actualMessage = response.jsonPath().getString("message");
        Assert.assertEquals(actualMessage, expectedMessage);
    }

    @Then("response should contain category details with ID {int}")
    public void response_should_contain_category_details(Integer categoryId) {
        Integer actualId = response.jsonPath().getInt("id");
        Assert.assertEquals(actualId, categoryId, "Returned category ID does not match requested ID");

        
        String name = response.jsonPath().getString("name");
        Assert.assertNotNull(name, "Category name should not be null");
    }

    @Then("the category should be saved as a main category")
    public void category_should_be_saved_as_main_category() {
        Object parentId = response.jsonPath().get("data.parentCategoryId");
        Assert.assertNull(parentId, "Parent category ID should be null for main category");
    }

}
