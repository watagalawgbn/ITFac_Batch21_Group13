Feature: Sell plant button visibility

  Scenario: Sell plant button is visible only to admin
    Given Admin is logged in
    When Admin navigates to the sales list page
    Then Sell plant button should be visible