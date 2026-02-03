Feature: Category API

# ========== ADMIN SCENARIOS ==========


Scenario: TC_API_CAT_01 - Verify Admin can retrieve all categories
  Given "Admin" is authenticated
  When "Admin" sends GET request to "/api/categories"
  Then API response status should be 200
  And response should contain categories

Scenario: TC_API_CAT_02 - Verify Admin can add a new category
  Given "Admin" is authenticated
  When "Admin" sends POST request to "/api/categories" with category details "red roses", "Roses"
  Then API response status should be 201

Scenario: TC_API_CAT_03 - Verify Admin cannot add category with empty name
  Given "Admin" is authenticated
  When "Admin" sends POST request to "/api/categories" with empty category name
  Then API response status should be 400
  And response message should be "Category name is mandatory"

Scenario: TC_API_CAT_04 - Verify Admin can update category name and parent
  Given "Admin" is authenticated
  When "Admin" updates category with hardcoded ID
  Then API response status should be 200
  And response message should be "Category updated successfully"

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




