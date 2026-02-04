@NormalUserTests
Feature: User Plant API

  Scenario: Verify user can retrieve plants via API
    Given user API base URL is set
    And user token is available
    When user sends GET request to "/api/plants"
    Then user receives status code 200
    And user receives list of plants in response
