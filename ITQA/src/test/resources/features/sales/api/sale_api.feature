Feature: Sales API - Admin

  @admin
  Scenario: Verify that an admin can retrieve the list of sales successfully
    Given Admin token is available
    When Admin sends GET request to "/api/sales"
    Then Response status code should be 200
    And Response should contain sales list

  @admin
  Scenario: Verify that an admin can create a sale successfully with valid data
    Given Admin token is available
    And A plant with available stock exists
    When Admin sends POST request to create sale with quantity 1
    Then Response status code should be 201
    And Sale should be created successfully
