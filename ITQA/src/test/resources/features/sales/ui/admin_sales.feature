@ui
@admin
@sales
Feature: Admin sales management

  @viewSalesList
  #TC_UI_Sales_1
  Scenario: Verify that an Admin can view the sales list page
    Given Admin is logged in
    When Admin navigates to the sales list page
    Then Sales list should be displayed

  @sellButtonHidden
  #TC_UI_Sales_8
  Scenario: Verify that Sell plant button is visible only to admin
    Given Admin is logged in
    When Admin navigates to the sales list page
    Then Sell plant button should be visible

  @plantsWithAvailableStock
  #TC_UI_Sales_10
  Scenario: Verify that an Admin sees only plants with available stock
    Given Admin is logged in
    When Admin navigates to the sales list page
    And Admin navigates to sell plant page
    Then Plant dropdown show only plants with stock greater than zero

  @createSale
  #TC_UI_Sales_11
  Scenario: Verify that an Admin can create a sale with valid plant and quantity
    Given Admin is logged in
    When Admin navigates to the sales list page
    And Admin navigates to sell plant page
    And Admin selects a plant with available stock
    And Admin notes the available stock for the selected plant
    And Admin enters a valid quantity
    And Admin clicks the sell button
    Then Sale should be created successfully
    And Admin should be redirected to the sales list page
    And Admin navigates to the plants list page
    And Stock should be reduced by the sold quantity


  @deleteSale
  #TC_UI_Sales_17
  Scenario: Verify that an Admin can delete a sale successfully
    Given Admin is logged in
    When Admin navigates to the sales list page
    And At least one sale record exists
    And Admin clicks delete button of a sale
    And Admin confirms the deletion
    Then Sale should be deleted successfully

