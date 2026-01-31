Feature: Sales page smoke test

  Scenario: Admin opens sales page
    Given admin is logged in
    When admin navigates to sales page
    Then sales list should be displayed
