@api
Feature: Category API - Admin

  As an Admin
  I want to manage categories via API
  So that I can create, update, and view categories

  # ========== ADMIN SCENARIOS ==========

  Scenario: TC_API_CAT_01 - Verify Admin can retrieve all categories
    Given "Admin" is authenticated
    When "Admin" sends GET request to "/api/categories"
    Then API response status should be 200
    And response should contain categories

  Scenario: TC_API_CAT_02 - Verify Admin can add a new category
    Given "Admin" is authenticated
    When "Admin" sends POST request to "/api/categories" with category details "pink roses", "Roses"
    Then API response status should be 201

  Scenario: TC_API_CAT_03 - Verify Admin cannot add category with empty name
    Given "Admin" is authenticated
    When "Admin" sends POST request to "/api/categories" with empty category name
    Then API response status should be 400
    And response message should be "Category name is mandatory"

  Scenario: TC_API_CAT_04 - Verify Admin can add a new category with empty parent category
    Given "Admin" is authenticated
    When "Admin" sends POST request to "/api/categories" with category name "Decos" and empty parent
    Then API response status should be 201
    And the category should be saved as a main category

  Scenario: TC_API_CAT_05 - Verify Admin can update category name and parent
    Given "Admin" is authenticated
    When "Admin" updates category with hardcoded ID
    Then API response status should be 200