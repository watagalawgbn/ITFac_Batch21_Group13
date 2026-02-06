Feature: Sales Page UI Validation - User Role
  As a user
  I want to view sales records on the Sales page
  So that I can see sales information without editing capabilities

  Background:
    Given the browser is opened
    And the base URL is configured
    And the user has successfully logged in

  @ui @Sales @TC-UI-SALES-01 @User @SalesAccess
  Scenario: TC-UI-SALES-01 - Verify that a user can successfully access the Sales page
    When the user navigates to the Sales page "/ui/sales"
    Then the Sales page should load successfully
    And the user should be on the Sales page

  @ui @Sales @TC-UI-SALES-02 @User @SalesRecords
  Scenario: TC-UI-SALES-02 - Verify that sales records are displayed correctly in the sales table
    Given at least one sale record exists in the system
    When the user navigates to the Sales page "/ui/sales"
    Then the Sales page should load successfully
    And all existing sales records should be displayed in the sales table
    And each record should be shown as a separate row

  @ui @Sales @TC-UI-SALES-03 @User @TotalPrice @Calculation
  Scenario: TC-UI-SALES-03 - Verify that the total price is calculated and displayed correctly for each sales record
    When the user navigates to the Sales page "/ui/sales"
    Then the Sales page should load successfully
    And the Total Price column should display calculated values
    And the Total Price should equal Quantity multiplied by Unit Price for each record

  @ui @Sales @TC-UI-SALES-04 @User @EditButton @ReadOnly
  Scenario: TC-UI-SALES-04 - Verify that the Edit button is not displayed on the Sales page for the user role
    When the user navigates to the Sales page "/ui/sales"
    Then the Sales page should load successfully
    And the Edit button should not be visible for the user role
    And the user should be unable to modify any sales record

