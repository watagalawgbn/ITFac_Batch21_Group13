Feature: Sales API - Admin

  @admin @api
  Scenario: Verify that an admin can retrieve the list of sales successfully
    Given "Admin" token is available
    When "Admin" sends GET request to "/api/sales"
    Then Response status code should be 200
    And Response should contain sales list

  @admin @api
  Scenario: Verify that an admin can create a sale successfully with valid data
    Given "Admin" token is available
    And A plant with available stock exists
    When "Admin" sends POST request to create sale with quantity 1
    Then Response status code should be 201
    And Sale should be created via api successfully

  @admin @api
  Scenario: Verify that the system prevents sale creation when stock is unavailable
    Given "Admin" token is available
    And A plant with no stock exists
    When "Admin" sends POST request to create sale with quantity 1
    Then Response status code should be 400
    And Error message should indicate stock unavailability

  @admin @api
  Scenario: Verify that the system rejects sale creation when quantity is zero
    Given "Admin" token is available
    And A plant with available stock exists
    When "Admin" sends POST request to create sale with quantity 0
    Then Response status code should be 400
    And Error message should indicate invalid quantity

  @admin @api
  Scenario: Verify that an admin can delete a sales record successfully
    Given "Admin" token is available
    And A sale record exists
    When "Admin" sends DELETE request for the sale
    Then Response status code should be 204
    And Sale should be deleted via api successfully

