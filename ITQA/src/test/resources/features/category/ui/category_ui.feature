Feature: Category Management

  Scenario: Verify "No Category found" message when no categories exist
    Given admin user is logged into the system
    When admin navigates to the Category List page
    And no categories exist in the system
    Then the system should display "No Category found" message

  Scenario: Verify "Add Category" button is visible for Admin
    Given admin user is logged into the system
    When admin navigates to the Category List page
    Then the Add Category button should be visible

  Scenario: Verify "Edit Category" button is visible for Admin
    Given admin user is logged into the system
    And at least one category exists in the system
    When admin navigates to the Category List page
    Then the Edit Category button should be visible for each category

  Scenario: Verify "Delete Category" button is visible for Admin
    Given admin user is logged into the system
    And at least one category exists in the system
    When admin navigates to the Category List page
    Then the Delete Category button should be visible for each category

   Scenario: Verify Admin can navigate to edit category page by clicking Edit icon
    Then admin should be navigated to the edit page when clicking an Edit icon

    Scenario: Verify "Add Category" button is NOT visible for regular user
    Given regular user is logged into the system
    When user navigates to the Category List page
    Then the Add Category button should NOT be visible

  Scenario: Verify "Edit Category" button is NOT visible for regular user
    Given regular user is logged into the system
    And at least one category exists in the system
    When user navigates to the Category List page
    Then the Edit Category button should NOT be visible

  Scenario: Verify "Delete Category" button is NOT visible for regular user
    Given regular user is logged into the system
    And at least one category exists in the system
    When user navigates to the Category List page
    Then the Delete Category button should NOT be visible

  Scenario: Verify that a User can search categories by category name
    Given regular user is logged into the system
    When user navigates to the Category List page
    And user enters "Roses" in the category search field
    And user clicks the Search button
    Then only categories matching "Roses" should be displayed
  
  Scenario: Verify that a User can filter categories by parent category
    Given regular user is logged into the system
    When user navigates to the Category List page
    And user selects "Roses" from the parent category dropdown
    And user clicks the Search button for parent category
    Then only categories under parent "Roses" should be displayed