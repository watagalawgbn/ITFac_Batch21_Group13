@ui
Feature: Category Management - Admin

  Scenario: TC_UI_CAT_01 - Verify "No Category found" message when no categories exist
    Given admin user is logged into the system
    When admin navigates to the Category List page
    And no categories exist in the system
    Then the system should display "No Category found" message

  Scenario: TC_UI_CAT_02 - Verify "Add Category" button is visible for Admin
    Given admin user is logged into the system
    When admin navigates to the Category List page
    Then the Add Category button should be visible

  Scenario: TC_UI_CAT_03 - Verify "Edit Category" button is visible for Admin
    Given admin user is logged into the system
    And at least one category exists in the system
    When admin navigates to the Category List page
    Then the Edit Category button should be visible for each category

  Scenario: TC_UI_CAT_04 - Verify "Delete Category" button is visible for Admin
    Given admin user is logged into the system
    And at least one category exists in the system
    When admin navigates to the Category List page
    Then the Delete Category button should be visible for each category

  Scenario:TC_UI_CAT_15 - Verify Admin can navigate to edit category page
    Given admin user is logged into the system
    And at least one category exists in the system
    When admin clicks an Edit icon
    Then admin should be navigated to the edit category page
