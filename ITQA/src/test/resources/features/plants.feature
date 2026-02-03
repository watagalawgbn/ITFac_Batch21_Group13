Feature: Admin User Plant Management

  Background:
    Given user navigates to the application

  Scenario: Admin login with valid credentials
    Given admin user is on the login page
    When admin user enters username "admin"
    And admin user enters password "admin123"
    And admin user clicks login button
    Then admin user should be logged in successfully

  Scenario: Verify visibility of Add Plant button for admin user
    Given admin user is logged in successfully
    When admin user navigates to Plants page "/ui/plants"
    Then Plants page should be displayed
    And Add Plant button should be visible
    And Add Plant button should be enabled for admin user

  Scenario: Verify navigation to Add Plant form when Add a Plant button is clicked
    Given admin user is logged in successfully
    And admin user is on the Plants page "/ui/plants"
    When admin user clicks on Add a Plant button
    Then user should be redirected to "/ui/plants/add" page
    And Add Plant form should be displayed successfully

  Scenario: Verify default values of Add Plant form fields
    Given admin user is logged in successfully
    And admin user is on the Plants page "/ui/plants"
    When admin user clicks on Add a Plant button
    Then user should be redirected to "/ui/plants/add" page
    And Add Plant form should be displayed successfully
    And all text fields should be empty by default
    And category dropdown should display default value "-- Select Sub Category --"
    And no pre-filled data should be visible in the form

  Scenario: Verify visibility of Save and Cancel buttons in the Add Plant page
    Given admin user is logged in successfully
    And admin user is on the Plants page "/ui/plants"
    When admin user clicks on Add a Plant button
    Then user should be redirected to "/ui/plants/add" page
    And Add Plant form should be displayed successfully
    And Save button should be visible
    And Cancel button should be visible
    And both Save and Cancel buttons should be enabled for interaction

  Scenario: Verify mandatory field validations in Add Plant form
    Given admin user is logged in successfully
    And admin user is on the Add Plant page "/ui/plants/add"
    When admin user clicks Save button without entering any data
    Then validation messages should appear for mandatory fields
    And validation message "Plant name is required" should be displayed
    And validation message "Category is required" should be displayed
    And validation message "Price is required" should be displayed
    And validation message "Quantity is required" should be displayed
    And plant should not be saved
    And user should remain on the Add Plant page "/ui/plants/add"

  Scenario: Verify minimum length validation for plant name in Add Plant form
    Given admin user is logged in successfully
    And admin user is on the Add Plant page "/ui/plants/add"
    When admin user enters plant name with 2 characters "AB"
    And admin user enters price "100"
    And admin user enters quantity "5"
    And admin user selects category "Flowers"
    And admin user clicks Save button
    Then validation message "Plant name must be between 3 and 25 characters" should be displayed
    And plant should not be saved
    And user should remain on the Add Plant page "/ui/plants/add"

  Scenario: Verify maximum length validation for plant name in Add Plant form
    Given admin user is logged in successfully
    And admin user is on the Add Plant page "/ui/plants/add"
    When admin user enters plant name with 26 characters "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    And admin user enters price "100"
    And admin user enters quantity "5"
    And admin user selects category "Flowers"
    And admin user clicks Save button
    Then validation message "Plant name must be between 3 and 25 characters" should be displayed
    And plant should not be saved
    And user should remain on the Add Plant page "/ui/plants/add"

  Scenario: Verify sub-category selection functionality in Add Plant form
    Given admin user is logged in successfully
    And admin user is on the Add Plant page "/ui/plants/add"
    When admin user opens category dropdown
    Then category dropdown should be opened
    And only valid sub-categories should be displayed
    And parent categories should not be displayed

  Scenario: Verify that negative price values are not allowed in Add plant form
    Given admin user is logged in successfully
    And admin user is on the Add Plant page "/ui/plants/add"
    When admin user enters plant name "Rose Plant"
    And admin user enters price "-10"
    And admin user enters quantity "5"
    And admin user selects category "Flowers"
    And admin user clicks Save button
    Then validation message "Price must be greater than 0" should be displayed
    And plant should not be saved
    And user should remain on the Add Plant page "/ui/plants/add"

  Scenario: Verify that zero price is not allowed in Add plant form
    Given admin user is logged in successfully
    And admin user is on the Add Plant page "/ui/plants/add"
    When admin user enters plant name "Lily Plant"
    And admin user enters price "0"
    And admin user enters quantity "5"
    And admin user selects category "Flowers"
    And admin user clicks Save button
    Then validation message "Price must be greater than 0" should be displayed
    And plant should not be saved
    And user should remain on the Add Plant page "/ui/plants/add"

  Scenario: Verify cancel button navigation functionality in Add plant form
    Given admin user is logged in successfully
    And admin user is on the Add Plant page "/ui/plants/add"
    When admin user enters plant name "Test Plant"
    And admin user enters price "50"
    And admin user enters quantity "10"
    And admin user selects category "Flowers"
    And admin user clicks Cancel button
    Then user should be redirected to "/ui/plants" page
    And no data from Add Plant form should be saved
    And Plants page should be displayed

  Scenario: Verify successful addition of a new plant to the plant list by filling the Add Plant form accurately
    Given admin user is logged in successfully
    And admin user is on the Plants page "/ui/plants"
    When admin user clicks on Add a Plant button
    And admin user enters plant name "Sunflower"
    And admin user enters price "25"
    And admin user enters quantity "20"
    And admin user selects category "Flowers"
    And admin user clicks Save button
    Then user should be redirected to "/ui/plants" page
    And Plants page should be displayed

