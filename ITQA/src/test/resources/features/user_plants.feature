@NormalUserTests
Feature: Normal User Plant Management

  Background:
    Given user navigates to the application

  @UserLogin
  Scenario: Normal user login with valid credentials
    Given user is on the login page
    When user enters username "testuser"
    And user enters password "test123"
    And user clicks login button
    Then user should be logged in successfully

  @ViewPlantList
  Scenario: Verify that non-admin user can view plant list
    Given user is logged in successfully as a normal user
    When user navigates to Plants page "/ui/plants"
    Then Plants page should be displayed
    And Plant list table is displayed
    And user can view all available plants

  @AddPlantButtonHidden
  Scenario: Verify that Add Plant button is hidden for non-admin user
    Given user is logged in successfully as a normal user
    When user navigates to Plants page "/ui/plants"
    Then Plants page should be displayed
    And Add Plant button should not be visible for normal user
    And normal user cannot add plants

