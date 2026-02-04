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

  @ActionsColumnHidden
  Scenario: Verify Action Column is hidden and the Edit and Delete action buttons are hidden for non-admin user
    Given user is logged in successfully as a normal user
    When user navigates to Plants page "/ui/plants"
    Then Plants page should be displayed
    And Actions column should not be visible for normal user
    And Edit icons should not be visible for normal user
    And Delete icons should not be visible for normal user

  @LowStockBadge
  Scenario: Verify Low badge display for low stock plants for normal user
    Given user is logged in successfully as a normal user
    When user navigates to Plants page "/ui/plants"
    Then Plants page should be displayed
    And at least one plant with quantity less than 5 should exist
    And Low badge should be displayed for plants with low stock
    And Low badge should not be displayed for plants with sufficient stock



