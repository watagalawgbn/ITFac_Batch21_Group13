Feature: Sales API - User

  @user
  Scenario: Verify that a user can retrieve the sales list successfully
    Given User token is available
    When User sends GET request to "/api/sales"
    Then Response status code should be 200
    And Response should contain sales list

  @user
  Scenario: Verify that a user is not authorized to create sales
    Given User token is available
    When User sends POST request to create sale with plantId 2 and quantity 4
    Then Response status code should be 403
    And Response should indicate access denied

  @user
  Scenario: Verify that a user is not authorized to delete a sale
    Given User token is available
    And A sale record exists
    When User sends DELETE request for the sale
    Then Response status code should be 403
    And Response should indicate access denied

  @user
  Scenario: Verify that the first page of sales records is returned correctly
    Given User token is available
    When User sends GET request to "/api/sales/page" with page 0 and size 10
    Then Response status code should be 200
    And Response should contain maximum 10 sales records

  @user
  Scenario: Verify that an empty response is returned for out of range page numbers
    Given User token is available
    When User sends GET request to "/api/sales/page" with page 100 and size 10
    Then Response status code should be 200
    And Response should contain an empty sales list