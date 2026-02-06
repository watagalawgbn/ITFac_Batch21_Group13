Feature: User Dashboard UI Validation

  @user
  Scenario: User dashboard loads successfully
    Given regular user is logged into the dashboard
    Then dashboard page should load successfully for user

  @user
  Scenario: User dashboard menu highlight
    Given regular user is logged into the dashboard
    Then dashboard menu should be highlighted for user

  @user
  Scenario: User navigates to Categories page via dashboard card
    Given regular user is logged into the dashboard
    When user clicks on Categories dashboard card
    Then user should be navigated to Categories page

  @user
  Scenario: User navigates to Plants page via dashboard card
    Given regular user is logged into the dashboard
    When user clicks on Plants dashboard card
    Then user should be navigated to Plants page

  @user
  Scenario: User navigates to Sales page via dashboard card
    Given regular user is logged into the dashboard
    When user clicks on Sales dashboard card
    Then user should be navigated to Sales page

  
  @user
  Scenario: Categories menu highlight on sidebar
    Given regular user is logged into the dashboard
    When user clicks on Categories menu
    Then Categories menu should be highlighted for user

  @user
  Scenario: Plants menu highlight on sidebar
    Given regular user is logged into the dashboard
    When user clicks on Plants menu
    Then Plants menu should be highlighted for user
