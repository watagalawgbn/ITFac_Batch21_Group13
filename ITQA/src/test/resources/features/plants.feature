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
