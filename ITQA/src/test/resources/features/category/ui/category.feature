Feature: Category Management - Admin
  As an admin user
  I want to manage categories in the system
  So that I can organize products effectively

  Background:
    Given the admin is logged into the system with username "admin" and password "admin123"
    And the admin has access to the category page

  @ui @Category @TC-UI-CAT-01 @Admin @AddCategory
  Scenario: TC-UI-CAT-01 - Verify Add Category page opens successfully for admin
    Given the admin is on the Category List page "/ui/categories"
    When the admin clicks the "Add A Category" button
    Then the Add Category form should open
    And the Category Name field should be displayed
    And the Parent Category dropdown should be displayed

  @ui @Category @TC-UI-CAT-02 @Admin @Validation
  Scenario: TC-UI-CAT-02 - Verify Category Name field is mandatory
    Given the admin is on the Add Category form "/ui/categories/add"
    When the admin leaves the Category Name field empty
    And the admin clicks the Save button
    Then a validation message "Category name is required" should be displayed
    And the category should not be saved

  @ui @Category @TC-UI-CAT-03 @Admin @MainCategory
  Scenario: TC-UI-CAT-03 - Verify admin can add a main category
    Given the admin is on the Add Category form "/ui/categories/add"
    When the admin enters Category Name "Tech"
    And the admin leaves the Parent Category field empty
    And the admin clicks the Save button
    Then the category should be created as a main category
    And the category "Tech" should appear in the category list
    And a success message should be displayed

  @ui @Category @TC-UI-CAT-04 @Admin @SubCategory
  Scenario: TC-UI-CAT-04 - Verify admin can add a sub-category
    Given the admin is on the Add Category form "/ui/categories/add"
    When the admin enters Category Name "Laptops"
    And the admin selects Parent Category "Tech"
    And the admin clicks the Save button
    Then the sub-category should be created successfully
    And the category "Laptops" should appear under parent "Tech"
    And a success message should be displayed

  @ui @Category @TC-UI-CAT-05 @Admin @MinLength @Validation
  Scenario: TC-UI-CAT-05 - Verify minimum length validation for Category Name
    Given the admin is on the Add Category form "/ui/categories/add"
    When the admin enters Category Name "AB"
    And the admin clicks the Save button
    Then a validation message "Category name must be between 3 and 10 characters" should be displayed
    And the category should not be saved

  @ui @Category @TC-UI-CAT-06 @Admin @MaxLength @Validation
  Scenario: TC-UI-CAT-06 - Verify maximum length validation for Category Name
    Given the admin is on the Add Category form "/ui/categories/add"
    When the admin enters Category Name "ThisIsAVeryLongCategoryName"
    And the admin clicks the Save button
    Then a validation message "Category name must be between 3 and 10 characters" should be displayed
    And the category should not be saved
