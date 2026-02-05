Feature: Dashboard API Testing

  # ===================== ADMIN DASHBOARD =====================

  Scenario: TC-API-DASH-01 - Admin retrieves category summary
    Given "Admin" token is available
    When "Admin" sends GET request to "/api/categories/summary"
    Then Response status should be 200
    And Response should contain summary data for "categories"

  Scenario: TC-API-DASH-02 - Admin retrieves plant summary
    Given "Admin" token is available
    When "Admin" sends GET request to "/api/plants/summary"
    Then Response status should be 200
    And Response should contain summary data for "plants"

  Scenario: TC-API-DASH-03 - Admin retrieves sales summary
    Given "Admin" token is available
    When "Admin" sends GET request to "/api/sales"
    Then Response status should be 200
    And Response should contain summary data for "sales"

  Scenario: TC-API-DASH-04 - Admin retrieves category list
    Given "Admin" token is available
    When "Admin" sends GET request to "/api/categories"
    Then Response status should be 200
    And Response should contain list data for "categories"

  Scenario: TC-API-DASH-05 - Admin retrieves plant list
    Given "Admin" token is available
    When "Admin" sends GET request to "/api/plants"
    Then Response status should be 200
    And Response should contain list data for "plants"

  Scenario: TC-API-DASH-06 - Admin retrieves sales list
    Given "Admin" token is available
    When "Admin" sends GET request to "/api/sales"
    Then Response status should be 200
    And Response should contain list data for "sales"

  # ===================== USER DASHBOARD =====================

  Scenario: TC-API-DASH-07 - User retrieves category summary
    Given "User" token is available
    When "User" sends GET request to "/api/categories/summary"
    Then Response status should be 200
    And Response should contain summary data for "categories"

  Scenario: TC-API-DASH-08 - User retrieves plant summary
    Given "User" token is available
    When "User" sends GET request to "/api/plants/summary"
    Then Response status should be 200
    And Response should contain summary data for "plants"

  Scenario: TC-API-DASH-09 - User retrieves sales summary
    Given "User" token is available
    When "User" sends GET request to "/api/sales"
    Then Response status should be 200
    And Response should contain summary data for "sales"

  Scenario: TC-API-DASH-10 - User retrieves category list
    Given "User" token is available
    When "User" sends GET request to "/api/categories"
    Then Response status should be 200
