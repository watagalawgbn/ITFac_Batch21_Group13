Feature: Plant dropdown stock validation

  Scenario: Admin sees only plants with available stock
    Given Admin is logged in
    When Admin navigates to the sales list page
    And Admin navigates to sell plant page
    Then Plant dropdown show only plants with stock greater than zero