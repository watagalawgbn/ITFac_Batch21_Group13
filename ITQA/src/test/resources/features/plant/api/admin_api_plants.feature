@api
@AdminTests
Feature: Admin Plant API

  Scenario: Verify admin can successfully retrieve all plants via API
    Given admin API base URL is set
    And admin token is available
    When admin sends GET request to "/api/plants"
    Then admin receives status code 200
    And admin receives list of plants in response

  Scenario: Verify admin can successfully add a plant via API
    Given admin API base URL is set
    And admin token is available
    When admin sends POST request to add a plant
    Then plant should be created successfully with status code 201

  Scenario: Verify adding plant with duplicate name via API
    Given admin API base URL is set
    And admin token is available
    When admin sends POST request to add a plant with duplicate name
    Then API should return status code 400
    And duplicate name validation message is returned

  Scenario: Verify admin can update plant via API
    Given admin API base URL is set
    And admin token is available
    When admin sends PUT request to update an existing plant
    Then plant should be updated successfully with status code 200
    And updated plant details are returned

  Scenario: Verify admin can delete plant via API
    Given admin API base URL is set
    And admin token is available
    When admin sends DELETE request to remove the plant
    Then plant should be deleted successfully with status code 204