@api
Feature: Category API - User

  As a User
  I want to view categories via API
  So that I can browse available categories without modification access

  # ========== USER SCENARIOS ==========
  
  Scenario: TC_API_CAT_22 - Verify User can retrieve all categories
    Given "User" is authenticated
    When "User" sends GET request to "/api/categories"
    Then API response status should be 200
    And response should contain categories
  
  Scenario: TC_API_CAT_23 - Verify User cannot add a new category
    Given "User" is authenticated
    When "User" sends POST request to "/api/categories" with category details "blue roses", "Roses"
    Then API response status should be 403

  Scenario: TC_API_CAT_24 - Verify User cannot update a category
    Given "User" is authenticated
    When "User" updates category with hardcoded ID
    Then API response status should be 403

  Scenario: TC_API_CAT_25 - Verify User cannot delete a category
    Given "User" is authenticated
    When "User" sends DELETE request to "/api/categories/16"
    Then API response status should be 403

  Scenario: TC_API_CAT_26 - Verify User can get a category by ID
    Given "User" is authenticated
    When "User" sends GET request to "/api/categories" with category ID 16
    Then API response status should be 200
    And response should contain category details with ID 16