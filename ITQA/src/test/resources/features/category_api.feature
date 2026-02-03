Feature: Category API

  Scenario: Verify Admin can retrieve all categories
    Given Admin is authenticated
    When Admin sends GET request to "/api/categories/"
    Then API response status should be 200
    And response should contain categories
