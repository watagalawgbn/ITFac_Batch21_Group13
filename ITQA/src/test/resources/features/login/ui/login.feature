Feature: Login Page UI Validation
  As a user
  I want to verify the login page UI elements
  So that I can ensure all required elements are displayed correctly

  Background:
    Given the browser is opened
    And the base URL is configured

  @ui @Login @TC-UI-LOGIN-01
  Scenario: TC-UI-LOGIN-01 - Verify that the login page displays all required UI elements correctly
    When I navigate to the login page "/ui/login"
    Then the login page should be opened successfully
    And the username input field should be displayed
    And the password input field should be displayed
    And the login button should be displayed
    And all required UI elements should be properly aligned and visible

  @ui @Login
  Scenario: Verify login page URL is accessible
    When I navigate to the login page "/ui/login"
    Then the login page URL should be reachable in the browser
    And the page title should contain login information

  @ui @Login
  Scenario: Verify login page elements are enabled
    When I navigate to the login page "/ui/login"
    Then the username input field should be enabled
    And the password input field should be enabled
    And the login button should be enabled

  @ui @Login @TC-UI-LOGIN-02 @Validation
  Scenario: TC-UI-LOGIN-02 - Verify validation messages for empty username and password fields
    When I navigate to the login page "/ui/login"
    And I clear all input fields
    And I click the login button without entering credentials
    Then the login button should be clicked without entering any credentials
    And the system should validate empty input fields
    And validation message should contain "Username is required"
    And validation message should contain "Password is required"
    And the user should remain on the login page

  @ui @Login @TC-UI-LOGIN-03 @InvalidLogin
  Scenario: TC-UI-LOGIN-03 - Verify error message for invalid login credentials
    When I navigate to the login page "/ui/login"
    And I enter invalid username "invaliduser@test.com"
    And I enter invalid password "wrongpassword123"
    And I click the login button
    Then the system should validate the credentials
    And error message should contain "Invalid username or password"
    And the user should not be logged in
    And the user should remain on the login page

  @ui @Login @TC-UI-LOGIN-04 @ValidLogin @Admin
  Scenario: TC-UI-LOGIN-04 - Verify admin can login with valid credentials
    When I navigate to the login page "/ui/login"
    And I enter valid admin username "admin"
    And I enter valid admin password "admin123"
    And I click the login button
    Then the admin should be authenticated
    And the admin should be redirected to admin dashboard
    And the admin should be logged in successfully

  @ui @Login @TC-UI-LOGIN-05 @AdminAccess @Dashboard
  Scenario: TC-UI-LOGIN-05 - Verify admin can view and access all admin-authorized features
    Given the admin has successfully logged in
    When the admin navigates through menu options
    Then all admin-authorized menus should be visible
    And the admin should be able to view Dashboard
    And the admin should be able to view Categories menu
    And the admin should be able to access Categories feature
    And the admin should be able to view Plants menu
    And the admin should be able to access Plants feature
    And the admin should be able to view Sales menu
    And the admin should be able to access Sales feature
    And the admin can access each feature according to permissions

  @ui @Login @TC-UI-LOGIN-07 @ValidLogin @User
  Scenario: TC-UI-LOGIN-07 - Verify user can login with valid credentials
    When I navigate to the login page "/ui/login"
    And I enter valid user username "testuser"
    And I enter valid user password "test123"
    And I click the login button
    Then the user should be authenticated
    And the user should be redirected to user dashboard
    And the user should be logged in successfully

  @ui @Login @TC-UI-LOGIN-08 @UserAccess @Dashboard @LimitedPermissions
  Scenario: TC-UI-LOGIN-08 - Verify user can view but has limited access to features
    Given the user has successfully logged in
    When the user navigates through menu options
    Then the user authorized menus should be visible
    And the user should be able to view Dashboard
    And the user should be able to view Categories menu in read-only mode
    And the user should NOT be able to add edit or delete categories
    And the user should be able to view Plants menu in read-only mode
    And the user should NOT be able to add edit or delete plants
    And the user should NOT be able to view Sales menu
    And the user should NOT be able to create or delete sales
    And the user can only access features according to limited permissions

