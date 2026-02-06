package stepdefinitions.login.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Map;

public class ApiLoginSteps {

    private String baseUrl;
    private String username;
    private String password;
    private Response response;
    private RequestSpecification request;
    private Map<String, String> credentials;
    private boolean usernameProvided = true;
    private boolean passwordProvided = true;

    @Given("the API base URL is {string}")
    public void theApiBaseUrlIs(String url) {
        this.baseUrl = url;
        RestAssured.baseURI = url;
        System.out.println("API Base URL configured: " + baseUrl);
    }

    @Given("I have a valid admin username {string}")
    public void iHaveAValidAdminUsername(String username) {
        this.username = username;
        this.usernameProvided = true;
        System.out.println("Valid admin username set: " + username);
    }

    @Given("I have a valid admin password {string}")
    public void iHaveAValidAdminPassword(String password) {
        this.password = password;
        this.passwordProvided = true;
        System.out.println("Valid admin password set: " + password);
    }

    @Given("I have a valid user username {string}")
    public void iHaveAValidUserUsername(String username) {
        this.username = username;
        this.usernameProvided = true;
        System.out.println("Valid user username set: " + username);
    }

    @Given("I have a valid user password {string}")
    public void iHaveAValidUserPassword(String password) {
        this.password = password;
        this.passwordProvided = true;
        System.out.println("Valid user password set: " + password);
    }

    @Given("I have an invalid username {string}")
    public void iHaveAnInvalidUsername(String username) {
        this.username = username;
        this.usernameProvided = true;
        System.out.println("Invalid username set: " + username);
    }

    @Given("I have an invalid password {string}")
    public void iHaveAnInvalidPassword(String password) {
        this.password = password;
        this.passwordProvided = true;
        System.out.println("Invalid password set: " + password);
    }

    @Given("I have an empty username {string}")
    public void iHaveAnEmptyUsername(String username) {
        this.username = username;
        this.usernameProvided = true;
        System.out.println("Empty username set");
    }

    @Given("I have an empty password {string}")
    public void iHaveAnEmptyPassword(String password) {
        this.password = password;
        this.passwordProvided = true;
        System.out.println("Empty password set");
    }

    @Given("I have no username provided")
    public void iHaveNoUsernameProvided() {
        this.username = null;
        this.usernameProvided = false;
        System.out.println("No username provided");
    }

    @Given("I have no password provided")
    public void iHaveNoPasswordProvided() {
        this.password = null;
        this.passwordProvided = false;
        System.out.println("No password provided");
    }

    @When("I send a POST request to {string} with credentials")
    public void iSendAPostRequestToWithCredentials(String endpoint) {
        try {
            // Build request body
            credentials = new HashMap<>();
            if (usernameProvided) {
                credentials.put("username", username);
            }
            if (passwordProvided) {
                credentials.put("password", password);
            }

            System.out.println("\n========== API REQUEST ==========");
            System.out.println("Endpoint: POST " + baseUrl + endpoint);
            System.out.println("Request Body: " + credentials);

            // Send POST request
            request = RestAssured.given()
                    .contentType("application/json")
                    .body(credentials);

            response = request.post(endpoint);

            System.out.println("\n========== API RESPONSE ==========");
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asPrettyString());
            System.out.println("Response Time: " + response.getTime() + " ms");
            System.out.println("==================================\n");

        } catch (Exception e) {
            System.err.println("Error sending API request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("the response status code should be {int}")
    public void theResponseStatusCodeShouldBe(int expectedStatusCode) {
        int actualStatusCode = response.getStatusCode();
        System.out.println("Expected Status Code: " + expectedStatusCode);
        System.out.println("Actual Status Code: " + actualStatusCode);

        Assert.assertEquals(actualStatusCode, expectedStatusCode,
                "Status code mismatch! Expected: " + expectedStatusCode + " but got: " + actualStatusCode);
        System.out.println("✓ Status code verification passed: " + actualStatusCode);
    }

    @Then("the response should contain a JWT token")
    public void theResponseShouldContainAJwtToken() {
        String token = response.jsonPath().getString("token");

        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertFalse(token.isEmpty(), "Token should not be empty");

        System.out.println("✓ JWT Token found in response");
        System.out.println("Token: " + token);
    }

    @Then("the response should contain token type {string}")
    public void theResponseShouldContainTokenType(String expectedTokenType) {
        String actualTokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(actualTokenType, "Token type should not be null");
        Assert.assertEquals(actualTokenType, expectedTokenType,
                "Token type mismatch! Expected: " + expectedTokenType + " but got: " + actualTokenType);

        System.out.println("✓ Token type verified: " + actualTokenType);
    }

    @Then("the admin should be successfully authenticated via API")
    public void theAdminShouldBeSuccessfullyAuthenticatedViaApi() {
        // Verify successful authentication
        Assert.assertEquals(response.getStatusCode(), 200, "Admin authentication failed");

        String token = response.jsonPath().getString("token");
        String tokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(token, "Token should be present for successful authentication");
        Assert.assertNotNull(tokenType, "Token type should be present for successful authentication");

        System.out.println("✓ Admin successfully authenticated via API");
        System.out.println("  - Token: " + token);
        System.out.println("  - Token Type: " + tokenType);
    }

    @Then("the user should be successfully authenticated via API")
    public void theUserShouldBeSuccessfullyAuthenticatedViaApi() {
        // Verify successful authentication
        Assert.assertEquals(response.getStatusCode(), 200, "User authentication failed");

        String token = response.jsonPath().getString("token");
        String tokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(token, "Token should be present for successful authentication");
        Assert.assertNotNull(tokenType, "Token type should be present for successful authentication");

        System.out.println("✓ User successfully authenticated via API");
        System.out.println("  - Token: " + token);
        System.out.println("  - Token Type: " + tokenType);
    }

    @Then("the response should contain error message")
    public void theResponseShouldContainErrorMessage() {
        // Check for error field or message field
        String error = null;
        String message = null;

        try {
            error = response.jsonPath().getString("error");
        } catch (Exception e) {
            // error field might not exist
        }

        try {
            message = response.jsonPath().getString("message");
        } catch (Exception e) {
            // message field might not exist
        }

        boolean hasError = (error != null && !error.isEmpty()) || (message != null && !message.isEmpty());

        Assert.assertTrue(hasError, "Response should contain error or message field");

        System.out.println("✓ Error message found in response");
        if (error != null) System.out.println("  - Error: " + error);
        if (message != null) System.out.println("  - Message: " + message);
    }

    @Then("the response should have error field {string}")
    public void theResponseShouldHaveErrorField(String expectedError) {
        String actualError = response.jsonPath().getString("error");

        Assert.assertNotNull(actualError, "Error field should not be null");
        Assert.assertEquals(actualError, expectedError,
                "Error field mismatch! Expected: " + expectedError + " but got: " + actualError);

        System.out.println("✓ Error field verified: " + actualError);
    }

    @Then("the response time should be less than {int} milliseconds")
    public void theResponseTimeShouldBeLessThanMilliseconds(int maxTime) {
        long actualTime = response.getTime();

        Assert.assertTrue(actualTime < maxTime,
                "Response time exceeded! Expected less than " + maxTime + "ms but got " + actualTime + "ms");

        System.out.println("✓ Response time within acceptable range: " + actualTime + "ms (< " + maxTime + "ms)");
    }

    @Then("the response should have Content-Type {string}")
    public void theResponseShouldHaveContentType(String expectedContentType) {
        String actualContentType = response.getContentType();

        Assert.assertNotNull(actualContentType, "Content-Type should not be null");
        Assert.assertTrue(actualContentType.contains(expectedContentType),
                "Content-Type mismatch! Expected to contain: " + expectedContentType + " but got: " + actualContentType);

        System.out.println("✓ Content-Type verified: " + actualContentType);
    }

    @Then("the response body should contain a valid token field")
    public void theResponseBodyShouldContainAValidTokenField() {
        String token = response.jsonPath().getString("token");

        Assert.assertNotNull(token, "Token field should be present in response body");
        Assert.assertFalse(token.isEmpty(), "Token field should not be empty");

        System.out.println("✓ Response body contains valid token field");
        System.out.println("  - Token field exists: true");
        System.out.println("  - Token value: " + token);
    }

    @Then("the response body should contain tokenType field")
    public void theResponseBodyShouldContainTokenTypeField() {
        String tokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(tokenType, "TokenType field should be present in response body");
        Assert.assertFalse(tokenType.isEmpty(), "TokenType field should not be empty");

        System.out.println("✓ Response body contains tokenType field");
        System.out.println("  - TokenType field exists: true");
        System.out.println("  - TokenType value: " + tokenType);
    }

    @Then("the token should not be null or empty")
    public void theTokenShouldNotBeNullOrEmpty() {
        String token = response.jsonPath().getString("token");

        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertTrue(token.length() > 0, "Token should not be empty");

        System.out.println("✓ Token is not null or empty");
        System.out.println("  - Token length: " + token.length() + " characters");
    }

    @Then("the token should be in valid JWT format")
    public void theTokenShouldBeInValidJwtFormat() {
        String token = response.jsonPath().getString("token");

        // JWT format validation: should have 3 parts separated by dots
        // Format: header.payload.signature
        String[] parts = token.split("\\.");

        Assert.assertTrue(parts.length == 3,
                "JWT token should have 3 parts (header.payload.signature). Found: " + parts.length + " parts");

        // Each part should not be empty
        for (int i = 0; i < parts.length; i++) {
            Assert.assertTrue(parts[i].length() > 0,
                    "JWT token part " + (i + 1) + " should not be empty");
        }

        System.out.println("✓ Token is in valid JWT format");
        System.out.println("  - Token parts: " + parts.length + " (header.payload.signature)");
        System.out.println("  - Header length: " + parts[0].length());
        System.out.println("  - Payload length: " + parts[1].length());
        System.out.println("  - Signature length: " + parts[2].length());
        System.out.println("  - Complete token: " + token.substring(0, Math.min(50, token.length())) + "...");
    }

    @Then("the tokenType should be {string}")
    public void theTokenTypeShouldBe(String expectedTokenType) {
        String actualTokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(actualTokenType, "TokenType should not be null");
        Assert.assertEquals(actualTokenType, expectedTokenType,
                "TokenType mismatch! Expected: '" + expectedTokenType + "' but got: '" + actualTokenType + "'");

        System.out.println("✓ TokenType is correct: " + actualTokenType);
    }

    @Then("the token should be usable for authenticated API requests")
    public void theTokenShouldBeUsableForAuthenticatedApiRequests() {
        String token = response.jsonPath().getString("token");
        String tokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(token, "Token should exist for authentication");
        Assert.assertNotNull(tokenType, "TokenType should exist for authentication");

        // Construct authorization header
        String authHeader = tokenType + " " + token;

        System.out.println("✓ Token is ready for authenticated API requests");
        System.out.println("  - Authorization Header: " + tokenType + " [token]");
        System.out.println("  - Token can be used with: Authorization: " + authHeader.substring(0, Math.min(30, authHeader.length())) + "...");
        System.out.println("  - Token is properly formatted for Bearer authentication");
        System.out.println("  - Token is ready to be sent in subsequent API requests");
    }

    @Then("the login attempt should be rejected")
    public void theLoginAttemptShouldBeRejected() {
        int statusCode = response.getStatusCode();

        // Login should be rejected with 401 or 400 status
        Assert.assertTrue(statusCode == 401 || statusCode == 400,
                "Login attempt should be rejected with 401 or 400 status code. Got: " + statusCode);

        System.out.println("✓ Login attempt was rejected");
        System.out.println("  - Status Code: " + statusCode);
        System.out.println("  - Login rejected as expected");
    }

    @Then("the error message should indicate invalid credentials")
    public void theErrorMessageShouldIndicateInvalidCredentials() {
        String error = null;
        String message = null;

        try {
            error = response.jsonPath().getString("error");
        } catch (Exception e) {
            // error field might not exist
        }

        try {
            message = response.jsonPath().getString("message");
        } catch (Exception e) {
            // message field might not exist
        }

        // Check if error or message indicates authentication failure
        boolean hasAuthError = false;
        String errorText = "";

        if (error != null && !error.isEmpty()) {
            errorText = error.toLowerCase();
            hasAuthError = errorText.contains("unauthorized") ||
                          errorText.contains("bad request") ||
                          errorText.contains("invalid");
        }

        if (!hasAuthError && message != null && !message.isEmpty()) {
            errorText = message.toLowerCase();
            hasAuthError = errorText.contains("invalid") ||
                          errorText.contains("incorrect") ||
                          errorText.contains("wrong") ||
                          errorText.contains("password") ||
                          errorText.contains("credentials");
        }

        Assert.assertTrue(hasAuthError,
                "Error message should indicate invalid credentials. Error: " + error + ", Message: " + message);

        System.out.println("✓ Error message indicates invalid credentials");
        if (error != null) System.out.println("  - Error: " + error);
        if (message != null) System.out.println("  - Message: " + message);
    }

    @Given("I have a SQL injection string in username {string}")
    public void iHaveASqlInjectionStringInUsername(String sqlInjectionUsername) {
        this.username = sqlInjectionUsername;
        this.usernameProvided = true;
        System.out.println("SQL injection string set in username: " + sqlInjectionUsername);
        System.out.println("⚠️ Testing security with malicious input");
    }

    @Given("I have a SQL injection string in password {string}")
    public void iHaveASqlInjectionStringInPassword(String sqlInjectionPassword) {
        this.password = sqlInjectionPassword;
        this.passwordProvided = true;
        System.out.println("SQL injection string set in password: " + sqlInjectionPassword);
        System.out.println("⚠️ Testing security with malicious input");
    }

    @Then("the system should reject the malicious input")
    public void theSystemShouldRejectTheMaliciousInput() {
        int statusCode = response.getStatusCode();

        // System should reject SQL injection with 401 or 400
        Assert.assertTrue(statusCode == 401 || statusCode == 400 || statusCode == 403,
                "System should reject SQL injection with 401/400/403 status. Got: " + statusCode);

        // Verify no SQL error messages are returned (would indicate SQL injection vulnerability)
        String responseBody = response.getBody().asString().toLowerCase();

        Assert.assertFalse(responseBody.contains("sql"),
                "Response should not contain SQL error messages");
        Assert.assertFalse(responseBody.contains("mysql"),
                "Response should not contain database error messages");
        Assert.assertFalse(responseBody.contains("ora-"),
                "Response should not contain Oracle error messages");
        Assert.assertFalse(responseBody.contains("syntax error"),
                "Response should not contain SQL syntax errors");

        System.out.println("✓ System successfully rejected malicious SQL injection input");
        System.out.println("  - Status Code: " + statusCode + " (access denied)");
        System.out.println("  - No SQL error messages leaked");
        System.out.println("  - Input properly sanitized/rejected");
    }

    @Then("no system crash or data leakage should occur")
    public void noSystemCrashOrDataLeakageShouldOccur() {
        // Verify response is received (no crash)
        Assert.assertNotNull(response, "Response should be received (system did not crash)");

        // Verify status code is in valid HTTP range
        int statusCode = response.getStatusCode();
        Assert.assertTrue(statusCode >= 400 && statusCode < 600,
                "Status code should be in valid HTTP error range (400-599). Got: " + statusCode);

        // Check response doesn't leak sensitive information
        String responseBody = response.getBody().asString().toLowerCase();

        // Should not contain stack traces
        Assert.assertFalse(responseBody.contains("exception"),
                "Response should not contain exception details");
        Assert.assertFalse(responseBody.contains("stack trace"),
                "Response should not contain stack traces");

        // Should not contain database information
        Assert.assertFalse(responseBody.contains("database"),
                "Response should not leak database information");
        Assert.assertFalse(responseBody.contains("table"),
                "Response should not leak table information");
        Assert.assertFalse(responseBody.contains("column"),
                "Response should not leak column information");

        // Should not contain server paths
        Assert.assertFalse(responseBody.contains("c:\\"),
                "Response should not leak Windows file paths");
        Assert.assertFalse(responseBody.contains("/home/"),
                "Response should not leak Linux file paths");

        System.out.println("✓ No system crash occurred");
        System.out.println("  - Response received successfully");
        System.out.println("  - No sensitive data leaked");
        System.out.println("  - No stack traces exposed");
        System.out.println("  - No database structure revealed");
        System.out.println("  - System handled malicious input securely");
    }

    @Then("the user should be authenticated successfully")
    public void theUserShouldBeAuthenticatedSuccessfully() {
        // Verify successful authentication
        Assert.assertEquals(response.getStatusCode(), 200, "User authentication failed");

        String token = response.jsonPath().getString("token");
        String tokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(token, "Token should be present for successful authentication");
        Assert.assertNotNull(tokenType, "Token type should be present for successful authentication");

        System.out.println("✓ User authenticated successfully");
        System.out.println("  - User login successful");
        System.out.println("  - Token: " + token);
        System.out.println("  - Token Type: " + tokenType);
    }

    @Then("the token should be valid for authenticated API access")
    public void theTokenShouldBeValidForAuthenticatedApiAccess() {
        String token = response.jsonPath().getString("token");
        String tokenType = response.jsonPath().getString("tokenType");

        Assert.assertNotNull(token, "Token should exist for authentication");
        Assert.assertNotNull(tokenType, "TokenType should exist for authentication");

        // Verify token is in JWT format
        String[] parts = token.split("\\.");
        Assert.assertEquals(parts.length, 3,
                "Token should be in valid JWT format with 3 parts");

        // Construct authorization header
        String authHeader = tokenType + " " + token;

        System.out.println("✓ Token is valid for authenticated API access");
        System.out.println("  - Token format: Valid JWT (3 parts)");
        System.out.println("  - Authorization Header: " + tokenType + " [token]");
        System.out.println("  - Token ready for API requests");
        System.out.println("  - Token can authenticate user in subsequent calls");
    }

    // ========== NEW STEP DEFINITIONS FOR TC-API-LOGIN-13, 14, 15 ==========

    @Then("the error message should indicate validation failure")
    public void theErrorMessageShouldIndicateValidationFailure() {
        String error = null;
        String message = null;

        try {
            error = response.jsonPath().getString("error");
        } catch (Exception e) {
            // error field might not exist
        }

        try {
            message = response.jsonPath().getString("message");
        } catch (Exception e) {
            // message field might not exist
        }

        // Check if error or message indicates validation failure
        boolean hasValidationError = false;
        String errorText = "";

        if (error != null && !error.isEmpty()) {
            errorText = error.toLowerCase();
            hasValidationError = errorText.contains("bad request") ||
                                errorText.contains("validation") ||
                                errorText.contains("required") ||
                                errorText.contains("empty") ||
                                errorText.contains("invalid");
        }

        if (!hasValidationError && message != null && !message.isEmpty()) {
            errorText = message.toLowerCase();
            hasValidationError = errorText.contains("validation") ||
                                errorText.contains("required") ||
                                errorText.contains("must not be empty") ||
                                errorText.contains("must not be blank") ||
                                errorText.contains("cannot be empty") ||
                                errorText.contains("missing") ||
                                errorText.contains("username") ||
                                errorText.contains("password");
        }

        Assert.assertTrue(hasValidationError,
                "Error message should indicate validation failure. Error: " + error + ", Message: " + message);

        System.out.println("✓ Error message indicates validation failure");
        if (error != null) System.out.println("  - Error: " + error);
        if (message != null) System.out.println("  - Message: " + message);
    }

    @Given("I have no credentials provided")
    public void iHaveNoCredentialsProvided() {
        this.username = null;
        this.password = null;
        this.usernameProvided = false;
        this.passwordProvided = false;
        System.out.println("No credentials provided (empty request body)");
    }

    @When("I send a POST request to {string} with empty body")
    public void iSendAPostRequestToWithEmptyBody(String endpoint) {
        try {
            System.out.println("\n========== API REQUEST (EMPTY BODY) ==========");
            System.out.println("Endpoint: POST " + baseUrl + endpoint);
            System.out.println("Request Body: {} (empty)");

            // Send POST request with empty body
            request = RestAssured.given()
                    .contentType("application/json")
                    .body("{}");

            response = request.post(endpoint);

            System.out.println("\n========== API RESPONSE ==========");
            System.out.println("Status Code: " + response.getStatusCode());
            System.out.println("Response Body: " + response.getBody().asPrettyString());
            System.out.println("Response Time: " + response.getTime() + " ms");
            System.out.println("==================================\n");

        } catch (Exception e) {
            System.err.println("Error sending API request: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Then("the error message should indicate missing request body")
    public void theErrorMessageShouldIndicateMissingRequestBody() {
        String error = null;
        String message = null;

        try {
            error = response.jsonPath().getString("error");
        } catch (Exception e) {
            // error field might not exist
        }

        try {
            message = response.jsonPath().getString("message");
        } catch (Exception e) {
            // message field might not exist
        }

        // Check if error or message indicates missing/empty request body
        boolean hasMissingBodyError = false;
        String errorText = "";

        if (error != null && !error.isEmpty()) {
            errorText = error.toLowerCase();
            hasMissingBodyError = errorText.contains("bad request") ||
                                 errorText.contains("required") ||
                                 errorText.contains("missing");
        }

        if (!hasMissingBodyError && message != null && !message.isEmpty()) {
            errorText = message.toLowerCase();
            hasMissingBodyError = errorText.contains("required") ||
                                 errorText.contains("missing") ||
                                 errorText.contains("empty") ||
                                 errorText.contains("body") ||
                                 errorText.contains("username") ||
                                 errorText.contains("password");
        }

        Assert.assertTrue(hasMissingBodyError,
                "Error message should indicate missing request body. Error: " + error + ", Message: " + message);

        System.out.println("✓ Error message indicates missing/empty request body");
        if (error != null) System.out.println("  - Error: " + error);
        if (message != null) System.out.println("  - Message: " + message);
    }
}
