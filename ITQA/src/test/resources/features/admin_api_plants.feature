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