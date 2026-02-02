Feature: Sales List Page

  Scenario: Admin can view the sales list page
    Given Admin is logged in
    When Admin navigates to the sales list page
    Then Sales list should be displayed
