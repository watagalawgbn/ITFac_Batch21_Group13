@ui
Feature: Category Management - User

  Scenario: TC_UI_CAT_49 - Verify "Add Category" button is NOT visible for regular user
    Given regular user is logged into the system
    When user navigates to the Category List page
    Then the Add Category button should NOT be visible

  Scenario: TC_UI_CAT_50 - Verify "Edit Category" button is NOT visible for regular user
    Given regular user is logged into the system
    And at least one category exists in the system
    When user navigates to the Category List page
    Then the Edit Category button should NOT be visible

  Scenario: TC_UI_CAT_51 - Verify "Delete Category" button is NOT visible for regular user
    Given regular user is logged into the system
    And at least one category exists in the system
    When user navigates to the Category List page
    Then the Delete Category button should NOT be visible

  Scenario: TC_UI_CAT_52 - Verify User can search categories by name
    Given regular user is logged into the system
    When user navigates to the Category List page
    And user enters "Roses" in the category search field
    And user clicks the Search button
    Then only categories matching "Roses" should be displayed

  Scenario: TC_UI_CAT_53 - Verify User can filter categories by parent category
    Given regular user is logged into the system
    When user navigates to the Category List page
    And user selects "Roses" from the parent category dropdown
    And user clicks the Search button for parent category
    Then only categories under parent "Roses" should be displayed