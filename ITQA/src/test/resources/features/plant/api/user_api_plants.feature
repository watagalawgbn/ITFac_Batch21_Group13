@api
@NormalUserTests
Feature: User Plant API

  Scenario: Verify user can retrieve plants via API
    Given user API base URL is set
    And user token is available
    When user sends GET request to "/api/plants"
    Then user receives status code 200
    And user receives list of plants in response

  Scenario: Verify unauthorized plant addition by normal user via API
    Given user API base URL is set
    And user token is available
    When user sends POST request to add a plant
    Then user receives status code 403

  Scenario: Verify unauthorized plant update via API
    Given user API base URL is set
    And user token is available
    And a plant id is available
    When user sends PUT request to update a plant
    Then user receives status code 403

  Scenario: Verify unauthorized plant deletion via API
    Given user API base URL is set
    And user token is available
    And a plant id is available
    When user sends DELETE request to delete a plant
    Then user receives status code 403

  Scenario: Verify filtering plants by category via API
    Given user API base URL is set
    And user token is available
    When user sends GET request to plants by category
    Then user receives status code 200
    And only plants from selected category are returned