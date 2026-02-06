@ui
Feature: Admin Dashboard UI Validation

  @admin
  Scenario: Admin dashboard loads successfully
    Given admin is logged into the dashboard
    Then dashboard page should load successfully for admin

  @admin
  Scenario: Admin navigation menu visibility
    Given admin is logged into the dashboard
    Then all admin navigation menu items should be visible

  @admin
  Scenario: Admin dashboard menu highlight
    Given admin is logged into the dashboard
    Then dashboard menu should be highlighted for admin

  @admin
  Scenario: Admin navigates to Categories page via dashboard card
    Given admin is logged into the dashboard
    When admin clicks on Categories dashboard card
    Then admin should be navigated to Categories page

  @admin
  Scenario: Admin navigates to Plants page via dashboard card
    Given admin is logged into the dashboard
    When admin clicks on Plants dashboard card
    Then admin should be navigated to Plants page

  @admin
  Scenario: Admin navigates to Sales page via dashboard card
    Given admin is logged into the dashboard
    When admin clicks on Sales dashboard card
    Then admin should be navigated to Sales page

  @admin
  Scenario: Categories menu highlight on sidebar
    Given admin is logged into the dashboard
    When admin clicks on Categories menu
    Then Categories menu should be highlighted

  @admin
  Scenario: Plants menu highlight on sidebar
    Given admin is logged into the dashboard
    When admin clicks on Plants menu
    Then Plants menu should be highlighted
