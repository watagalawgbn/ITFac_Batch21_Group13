Feature: User sales management

  @user @ui
  #TC_UI_Sales_18
  Scenario: User can view the sales list page
    Given User is logged in
    When User navigates to the sales list page
    Then Sales list should be visible to the user

  @user @ui
  #TC_UI_Sales_19
  Scenario: Pagination works when more than 10 sales exist
    Given User is logged in
    When User navigates to the sales list page
    And Pagination controls are visible
    And User clicks the next pagination button
    Then Next set of sales records should be displayed

  @user @noSales @ui
  #TC_UI_Sales_20
  Scenario: User sees no sales message when no sales exist
    Given User is logged in
    When User navigates to the sales list page
    Then No sales message should be displayed to the user

  @user @ui
  #TC_UI_Sales_25
  Scenario: Sell Plant button is not visible to user
    Given User is logged in
    When User navigates to the sales list page
    Then Sell Plant button should not be visible to the user

  @user @ui
  #TC_UI_Sales_24
  Scenario: Sales are sorted by sold date in descending order
    Given User is logged in
    When User navigates to the sales list page
    Then Sales should be sorted by sold date in descending order
