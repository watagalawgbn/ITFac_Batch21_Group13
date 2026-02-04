Feature: User sales management

  Scenario: User can view the sales list page
    Given User is logged in
    When User navigates to the sales list page
    Then Sales list should be visible to the user