@ui
@user
@sales
Feature: User sales management

  @viewSalesList
  #TC_UI_Sales_18
  Scenario: Verify that a User can view the sales list page
    Given User is logged in
    When User navigates to the sales list page
    Then Sales list should be visible to the user

  @userPagination
  #TC_UI_Sales_19
  Scenario: Verify that Pagination works when more than 10 sales exist
    Given User is logged in
    When User navigates to the sales list page
    And Pagination controls are visible
    And User clicks the next pagination button
    Then Next set of sales records should be displayed

  @noSalesMessage
  #TC_UI_Sales_20
  Scenario: Verify that a User sees no sales message when no sales exist
    Given User is logged in
    When User navigates to the sales list page
    Then No sales message should be displayed to the user

  @hiddenSellPlantButton
  #TC_UI_Sales_25
  Scenario: Verify that Sell Plant button is not visible to user
    Given User is logged in
    When User navigates to the sales list page
    Then Sell Plant button should not be visible to the user

  @sortSales
  #TC_UI_Sales_24
  Scenario: Verify that Sales are sorted by Sold Date in descending order
    Given User is logged in
    When User navigates to the sales list page
    Then Sales should be sorted by Sold Date in descending order

