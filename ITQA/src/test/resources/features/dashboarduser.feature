Feature: User Dashboard UI Validation

@user
Scenario: User dashboard loads successfully
  Given regular user is logged into the dashboard
  Then dashboard page should load successfully for user

@user
Scenario: User dashboard summary sections
  Given regular user is logged into the dashboard
  Then user dashboard cards should be visible

@user
Scenario: User dashboard menu highlight
  Given regular user is logged into the dashboard
  Then dashboard menu should be highlighted for user
