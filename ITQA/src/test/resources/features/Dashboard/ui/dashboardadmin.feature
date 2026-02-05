Feature: Admin Dashboard UI Validation

  Scenario: Admin dashboard loads successfully
    Given admin is logged into the dashboard
    Then dashboard page should load successfully for admin

  Scenario: Admin navigation menu visibility
    Given admin is logged into the dashboard
    Then all admin navigation menu items should be visible

  Scenario: Admin dashboard menu highlight
    Given admin is logged into the dashboard
    Then dashboard menu should be highlighted for admin

  Scenario: Admin navigates to Categories page via dashboard card
    Given admin is logged into the dashboard
    When admin clicks on Categories dashboard card
    Then admin should be navigated to Categories page

  Scenario: Admin navigates to Plants page via dashboard card
    Given admin is logged into the dashboard
    When admin clicks on Plants dashboard card
    Then admin should be navigated to Plants page

  Scenario: Admin navigates to Sales page via dashboard card
    Given admin is logged into the dashboard
    When admin clicks on Sales dashboard card
    Then admin should be navigated to Sales page
