Feature: API Login Validation
  As a system
  I want to verify the API login endpoints
  So that I can ensure authentication works correctly via API

  Background:
    Given the API base URL is "http://localhost:8081"

  @API @Login @TC-API-LOGIN-01 @Admin
  Scenario: TC-API-LOGIN-01 - Verify admin can login via API using valid credentials
    Given I have a valid admin username "admin"
    And I have a valid admin password "admin123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 200
    And the response should contain a JWT token
    And the response should contain token type "Bearer"
    And the admin should be successfully authenticated via API

  @API @Login @TC-API-LOGIN-02 @TokenValidation @Admin
  Scenario: TC-API-LOGIN-02 - Verify JWT token is generated for admin upon successful login
    Given I have a valid admin username "admin"
    And I have a valid admin password "admin123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 200
    And the response body should contain a valid token field
    And the response body should contain tokenType field
    And the token should not be null or empty
    And the token should be in valid JWT format
    And the tokenType should be "Bearer"
    And the token should be usable for authenticated API requests

  @API @Login @TC-API-LOGIN-02 @User
  Scenario: TC-API-LOGIN-02 - Verify user can login via API using valid credentials
    Given I have a valid user username "testuser"
    And I have a valid user password "test123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 200
    And the response should contain a JWT token
    And the response should contain token type "Bearer"
    And the user should be successfully authenticated via API

  @API @Login @TC-API-LOGIN-03 @InvalidCredentials
  Scenario: TC-API-LOGIN-03 - Verify API returns error for invalid credentials
    Given I have an invalid username "invaliduser@test.com"
    And I have an invalid password "wrongpassword"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the response should contain error message

  @API @Login @TC-API-LOGIN-04 @EmptyCredentials
  Scenario: TC-API-LOGIN-04 - Verify API returns error for empty credentials
    Given I have an empty username ""
    And I have an empty password ""
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the response should contain error message

  @API @Login @TC-API-LOGIN-05 @MissingFields
  Scenario: TC-API-LOGIN-05 - Verify API returns error for missing username
    Given I have no username provided
    And I have a valid admin password "admin123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the response should contain error message

  @API @Login @TC-API-LOGIN-06 @InvalidPassword @Admin
  Scenario: TC-API-LOGIN-06 - Verify error when admin logs in with invalid password
    Given I have a valid admin username "admin"
    And I have an invalid password "wrongpassword123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the login attempt should be rejected
    And the response should contain error message
    And the error message should indicate invalid credentials

  @API @Login @TC-API-LOGIN-07 @InvalidUsername @Admin
  Scenario: TC-API-LOGIN-07 - Verify error when admin logs in with invalid username
    Given I have an invalid username "invalidadmin"
    And I have a valid admin password "admin123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the login attempt should be rejected
    And the response should contain error message
    And the error message should indicate invalid credentials

  @API @Login @TC-API-LOGIN-08 @SQLInjection @Security @Admin
  Scenario: TC-API-LOGIN-08 - Verify system prevents SQL injection during admin API login
    Given I have a SQL injection string in username "admin' OR '1'='1"
    And I have a SQL injection string in password "' OR '1'='1' --"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the system should reject the malicious input
    And the response should contain error message
    And no system crash or data leakage should occur

  @API @Login @TC-API-LOGIN-09 @User @ValidLogin
  Scenario: TC-API-LOGIN-09 - Verify user can successfully log in via API using valid credentials
    Given I have a valid user username "testuser"
    And I have a valid user password "test123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 200
    And the user should be authenticated successfully
    And the response should contain a JWT token
    And the response should contain token type "Bearer"

  @API @Login @TC-API-LOGIN-10 @User @TokenValidation
  Scenario: TC-API-LOGIN-10 - Verify JWT token is generated for user upon successful login
    Given I have a valid user username "testuser"
    And I have a valid user password "test123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 200
    And the response body should contain a valid token field
    And the response body should contain tokenType field
    And the token should not be null or empty
    And the token should be in valid JWT format
    And the tokenType should be "Bearer"
    And the token should be valid for authenticated API access

  @API @Login @TC-API-LOGIN-11 @User @InvalidPassword
  Scenario: TC-API-LOGIN-11 - Verify error when user logs in with invalid password
    Given I have a valid user username "testuser"
    And I have an invalid password "wrongpassword456"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the login attempt should be rejected
    And the response should contain error message
    And the error message should indicate invalid credentials

  @API @Login @TC-API-LOGIN-12 @User @InvalidUsername
  Scenario: TC-API-LOGIN-12 - Verify error when user logs in with invalid username
    Given I have an invalid username "invalidtestuser"
    And I have a valid user password "test123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the login attempt should be rejected
    And the response should contain error message
    And the error message should indicate invalid credentials

  @API @Login @TC-API-LOGIN-13 @User @EmptyUsername @Validation
  Scenario: TC-API-LOGIN-13 - Verify that an error is returned when a user attempts to log in with an empty username
    Given I have an empty username ""
    And I have a valid user password "test123"
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the login attempt should be rejected
    And the response should contain error message
    And the error message should indicate invalid credentials

  @API @Login @TC-API-LOGIN-14 @User @EmptyPassword @Validation
  Scenario: TC-API-LOGIN-14 - Verify that an error is returned when a user attempts to log in with an empty password
    Given I have a valid user username "testuser"
    And I have an empty password ""
    When I send a POST request to "/api/auth/login" with credentials
    Then the response status code should be 401
    And the login attempt should be rejected
    And the response should contain error message
    And the error message should indicate invalid credentials

  @API @Login @TC-API-LOGIN-15 @EmptyRequestBody @Validation
  Scenario: TC-API-LOGIN-15 - Verify that an error is returned when a user attempts to log in with an empty request body
    Given I have no credentials provided
    When I send a POST request to "/api/auth/login" with empty body
    Then the response status code should be 401
    And the login attempt should be rejected
    And the response should contain error message
    And the error message should indicate invalid credentials
