Feature: User sales management

  Scenario: User can view the sales list page
    Given User is logged in
    When User navigates to the sales list page
    Then Sales list should be visible to the user

  Scenario: Pagination works when more than 10 sales exist
    Given User is logged in
    When User navigates to the sales list page
    And Pagination controls are visible
    And User clicks the next pagination button
    Then Next set of sales records should be displayed


