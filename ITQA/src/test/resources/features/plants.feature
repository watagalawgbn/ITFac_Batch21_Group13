Feature: Admin User Plant Management

  Background:
    Given user navigates to the application

  Scenario: Admin login with valid credentials
    Given admin user is on the login page
    When admin user enters username "admin"
    And admin user enters password "admin123"
    And admin user clicks login button
    Then admin user should be logged in successfully

  Scenario: Verify visibility of Add Plant button for admin user
    Given admin user is logged in successfully
    When admin user navigates to Plants page "/ui/plants"
    Then Plants page should be displayed
    And Add Plant button should be visible
    And Add Plant button should be enabled for admin user

  Scenario: Verify navigation to Add Plant form when Add a Plant button is clicked
    Given admin user is logged in successfully
    And admin user is on the Plants page "/ui/plants"
    When admin user clicks on Add a Plant button
    Then user should be redirected to "/ui/plants/add" page
    And Add Plant form should be displayed successfully

  Scenario: Verify default values of Add Plant form fields
    Given admin user is logged in successfully
    And admin user is on the Plants page "/ui/plants"
    When admin user clicks on Add a Plant button
    Then user should be redirected to "/ui/plants/add" page
    And Add Plant form should be displayed successfully
    And all text fields should be empty by default
    And category dropdown should display default value "-- Select Sub Category --"
    And no pre-filled data should be visible in the form

  Scenario: Verify visibility of Save and Cancel buttons in the Add Plant page
    Given admin user is logged in successfully
    And admin user is on the Plants page "/ui/plants"
    When admin user clicks on Add a Plant button
    Then user should be redirected to "/ui/plants/add" page
    And Add Plant form should be displayed successfully
    And Save button should be visible
    And Cancel button should be visible
    And both Save and Cancel buttons should be enabled for interaction

