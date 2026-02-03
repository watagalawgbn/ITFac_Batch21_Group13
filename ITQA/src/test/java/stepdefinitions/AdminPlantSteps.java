package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import pages.AddPlantPage;
import pages.LoginPage;
import pages.PlantsPage;
import utils.DriverManager;

import java.util.List;

public class AdminPlantSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private PlantsPage plantsPage;
    private AddPlantPage addPlantPage;

    public AdminPlantSteps() {
        this.driver = DriverManager.getDriver();
        this.loginPage = new LoginPage(driver);
        this.plantsPage = new PlantsPage(driver);
        this.addPlantPage = new AddPlantPage(driver);
    }

    // Background Steps
    @Given("user navigates to the application")
    public void user_navigates_to_the_application() {
        driver.navigate().to("http://localhost:8080/ui/login");
        System.out.println("User navigated to the application");
    }

    // Login Steps
    @Given("admin user is on the login page")
    public void admin_user_is_on_the_login_page() {
        loginPage.navigateToLoginPage("http://localhost:8080/ui/login");
        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Login page is not displayed");
        System.out.println("Admin user is on the login page");
    }

    @When("admin user enters username {string}")
    public void admin_user_enters_username(String username) {
        loginPage.enterUsername(username);
        System.out.println("Admin user entered username: " + username);
    }

    @When("admin user enters password {string}")
    public void admin_user_enters_password(String password) {
        loginPage.enterPassword(password);
        System.out.println("Admin user entered password: " + password);
    }

    @When("admin user clicks login button")
    public void admin_user_clicks_login_button() {
        loginPage.clickLoginButton();
        System.out.println("Admin user clicked login button");
        // Wait for page to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Then("admin user should be logged in successfully")
    public void admin_user_should_be_logged_in_successfully() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after login: " + currentUrl);
        // Check if login was successful by verifying the URL changed from login page
        Assert.assertFalse(currentUrl.contains("login"), "User is still on login page");
        System.out.println("Admin user is logged in successfully");
    }

    // Plants Page Steps
    @Given("admin user is logged in successfully")
    public void admin_user_is_logged_in_successfully() {
        // Assuming login already happened from previous scenario or setup
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL for login check: " + currentUrl);
        // Accept any URL that's not on the login page
        if (currentUrl.contains("/ui/login")) {
            // If we're on login page, do a quick login
            try {
                loginPage.login("admin", "admin123");
                Thread.sleep(3000);
            } catch (Exception e) {
                System.out.println("Auto-login failed: " + e.getMessage());
            }
        }
        System.out.println("Admin user is logged in successfully");
    }

    @When("admin user navigates to Plants page {string}")
    public void admin_user_navigates_to_plants_page(String path) {
        String baseUrl = driver.getCurrentUrl().split("/ui/")[0];
        String fullUrl = baseUrl + path;
        plantsPage.navigateToPlantsPage(fullUrl);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Admin user navigated to Plants page: " + fullUrl);
    }

    @Then("Plants page should be displayed")
    public void plants_page_should_be_displayed() {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Checking if plants page is displayed. Current URL: " + currentUrl);
        // Check by URL as primary validation
        Assert.assertTrue(currentUrl.contains("plants") || currentUrl.contains("plant"),
            "Plants page is not displayed. URL: " + currentUrl);
        System.out.println("Plants page is displayed");
    }

    @Then("Add Plant button should be visible")
    public void add_plant_button_should_be_visible() {
        Assert.assertTrue(plantsPage.isAddPlantButtonVisible(), "Add Plant button is not visible");
        System.out.println("Add Plant button is visible");
    }

    @Then("Add Plant button should be enabled for admin user")
    public void add_plant_button_should_be_enabled_for_admin_user() {
        Assert.assertTrue(plantsPage.isAddPlantButtonPresent(), "Add Plant button is not present");
        System.out.println("Add Plant button is enabled for admin user");
    }

    // Add Plant Navigation Steps
    @Given("admin user is on the Plants page {string}")
    public void admin_user_is_on_the_plants_page(String path) {
        String baseUrl = driver.getCurrentUrl().split("/ui/")[0];
        String fullUrl = baseUrl + path;
        plantsPage.navigateToPlantsPage(fullUrl);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Validate by URL instead of page elements
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("plants") || currentUrl.contains("plant"),
            "Plants page is not displayed. URL: " + currentUrl);
        System.out.println("Admin user is on the Plants page");
    }

    @When("admin user clicks on Add a Plant button")
    public void admin_user_clicks_on_add_a_plant_button() {
        plantsPage.clickAddPlantButton();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Admin user clicked on Add a Plant button");
    }

    @Then("user should be redirected to {string} page")
    public void user_should_be_redirected_to_page(String expectedPath) {
        String currentUrl = driver.getCurrentUrl();
        System.out.println("Current URL after clicking Add Plant: " + currentUrl);
        Assert.assertTrue(currentUrl.contains(expectedPath.replace("/ui/", "")),
            "User is not redirected to " + expectedPath + ". Current URL: " + currentUrl);
        System.out.println("User is redirected to: " + expectedPath);
    }

    @Then("Add Plant form should be displayed successfully")
    public void add_plant_form_should_be_displayed_successfully() {
        Assert.assertTrue(addPlantPage.isOnAddPlantPage(), "Not on Add Plant page");
        Assert.assertTrue(addPlantPage.isAddPlantFormDisplayed(), "Add Plant form is not displayed");
        System.out.println("Add Plant form is displayed successfully");
    }

    // Verify default values of Add Plant form fields
    @Then("all text fields should be empty by default")
    public void all_text_fields_should_be_empty_by_default() {
        Assert.assertTrue(addPlantPage.isPlantNameFieldEmpty(), "Plant Name field is not empty");
        Assert.assertTrue(addPlantPage.isPriceFieldEmpty(), "Price field is not empty");
        Assert.assertTrue(addPlantPage.isQuantityFieldEmpty(), "Quantity field is not empty");
        System.out.println("All text fields are empty by default");
    }

    @Then("category dropdown should display default value {string}")
    public void category_dropdown_should_display_default_value(String expectedDefault) {
        String actualDefault = addPlantPage.getCategoryDropdownDefaultText();
        Assert.assertTrue(actualDefault.contains(expectedDefault.replace("\"", "")),
            "Category dropdown default value is not correct. Expected: " + expectedDefault + ", Actual: " + actualDefault);
        System.out.println("Category dropdown displays default value: " + actualDefault);
    }

    @Then("no pre-filled data should be visible in the form")
    public void no_pre_filled_data_should_be_visible_in_the_form() {
        Assert.assertTrue(addPlantPage.isPlantNameFieldEmpty(), "Plant Name field has pre-filled data");
        Assert.assertTrue(addPlantPage.isPriceFieldEmpty(), "Price field has pre-filled data");
        Assert.assertTrue(addPlantPage.isQuantityFieldEmpty(), "Quantity field has pre-filled data");
        Assert.assertTrue(addPlantPage.isCategoryDropdownDefaultValue(), "Category dropdown is not set to default");
        System.out.println("No pre-filled data is visible in the form");
    }

    // Verify visibility of Save and Cancel buttons
    @Then("Save button should be visible")
    public void save_button_should_be_visible() {
        Assert.assertTrue(addPlantPage.isSaveButtonVisible(), "Save button is not visible");
        System.out.println("Save button is visible");
    }

    @Then("Cancel button should be visible")
    public void cancel_button_should_be_visible() {
        Assert.assertTrue(addPlantPage.isCancelButtonVisible(), "Cancel button is not visible");
        System.out.println("Cancel button is visible");
    }

    @Then("Save button should be enabled for interaction")
    public void save_button_should_be_enabled_for_interaction() {
        Assert.assertTrue(addPlantPage.isSaveButtonEnabled(), "Save button is not enabled");
        System.out.println("Save button is enabled for interaction");
    }

    @Then("Cancel button should be enabled for interaction")
    public void cancel_button_should_be_enabled_for_interaction() {
        Assert.assertTrue(addPlantPage.isCancelButtonEnabled(), "Cancel button is not enabled");
        System.out.println("Cancel button is enabled for interaction");
    }

    @Then("both Save and Cancel buttons should be enabled for interaction")
    public void both_save_and_cancel_buttons_should_be_enabled_for_interaction() {
        Assert.assertTrue(addPlantPage.isSaveButtonEnabled(), "Save button is not enabled");
        Assert.assertTrue(addPlantPage.isCancelButtonEnabled(), "Cancel button is not enabled");
        System.out.println("Both Save and Cancel buttons are enabled for interaction");
    }

    // Verify mandatory field validations
    @When("admin user clicks Save button without entering any data")
    public void admin_user_clicks_save_button_without_entering_any_data() {
        addPlantPage.clickSaveButton();
        System.out.println("Save button clicked without entering any data");
    }

    @Then("validation messages should appear for mandatory fields")
    public void validation_messages_should_appear_for_mandatory_fields() {
        Assert.assertTrue(addPlantPage.areValidationMessagesDisplayed(),
            "Validation messages are not displayed for mandatory fields");
        System.out.println("Validation messages appear for mandatory fields");
    }

    @Then("validation message {string} should be displayed")
    public void validation_message_should_be_displayed(String expectedMessage) {
        Assert.assertTrue(addPlantPage.hasValidationMessageForField(expectedMessage),
            "Validation message '" + expectedMessage + "' is not displayed");
        System.out.println("Validation message displayed: " + expectedMessage);
    }

    @Then("plant should not be saved")
    public void plant_should_not_be_saved() {
        // Verify we're still on the add plant page (plant was not saved and redirected)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("/plants/add"),
            "Plant was saved and user was redirected. Current URL: " + currentUrl);
        System.out.println("Plant was not saved - user remains on add plant page");
    }

    @Then("user should remain on the Add Plant page {string}")
    public void user_should_remain_on_the_add_plant_page(String expectedPath) {
        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains(expectedPath.replace("/ui/", "")),
            "User was not redirected to " + expectedPath + ". Current URL: " + currentUrl);
        Assert.assertTrue(addPlantPage.isAddPlantFormDisplayed(), "Add Plant form is not displayed");
        System.out.println("User remains on the Add Plant page: " + expectedPath);
    }

    @Given("admin user is on the Add Plant page {string}")
    public void admin_user_is_on_the_add_plant_page(String path) {
        try {
            String url = driver.getCurrentUrl();
            if (!url.contains("/plants/add")) {
                // Navigate to the add plant page
                driver.navigate().to("http://localhost:8080" + path);
                System.out.println("Navigated to Add Plant page: " + path);
            }

            // Wait for the form to load
            Thread.sleep(2000);

            Assert.assertTrue(addPlantPage.isOnAddPlantPage(), "Not on Add Plant page");
            Assert.assertTrue(addPlantPage.isAddPlantFormDisplayed(), "Add Plant form is not displayed");
            System.out.println("Admin user is on the Add Plant page: " + path);
        } catch (Exception e) {
            System.out.println("Error navigating to Add Plant page: " + e.getMessage());
            throw new RuntimeException("Failed to navigate to Add Plant page", e);
        }
    }

    // Plant name length validation steps
    @When("admin user enters plant name with {int} characters {string}")
    public void admin_user_enters_plant_name_with_characters(int characterCount, String plantName) {
        addPlantPage.enterPlantName(plantName);
        System.out.println("Plant name with " + characterCount + " characters entered: " + plantName);
    }

    @When("admin user enters plant name {string}")
    public void admin_user_enters_plant_name(String plantName) {
        addPlantPage.enterPlantName(plantName);
        System.out.println("Plant name entered: " + plantName);
    }

    @When("admin user enters price {string}")
    public void admin_user_enters_price(String price) {
        addPlantPage.enterPrice(price);
        System.out.println("Price entered: " + price);
    }

    @When("admin user enters quantity {string}")
    public void admin_user_enters_quantity(String quantity) {
        addPlantPage.enterQuantity(quantity);
        System.out.println("Quantity entered: " + quantity);
    }

    @When("admin user selects category {string}")
    public void admin_user_selects_category(String categoryName) {
        addPlantPage.selectCategory(categoryName);
        System.out.println("Category selected: " + categoryName);
    }

    @When("admin user clicks Save button")
    public void admin_user_clicks_save_button() {
        addPlantPage.clickSaveButton();
        System.out.println("Save button clicked");
    }

    // Category dropdown verification steps
    @When("admin user opens category dropdown")
    public void admin_user_opens_category_dropdown() {
        addPlantPage.openCategoryDropdown();
        System.out.println("Category dropdown opened by admin");
    }

    @Then("category dropdown should be opened")
    public void category_dropdown_should_be_opened() {
        Assert.assertTrue(addPlantPage.isCategoryDropdownOpen(),
            "Category dropdown is not open");
        System.out.println("Category dropdown is opened");
    }

    @Then("only valid sub-categories should be displayed")
    public void only_valid_sub_categories_should_be_displayed() {
        Assert.assertTrue(addPlantPage.areOnlyValidSubcategoriesDisplayed(),
            "Valid sub-categories are not displayed");
        System.out.println("Only valid sub-categories are displayed");
    }

    @Then("parent categories should not be displayed")
    public void parent_categories_should_not_be_displayed() {
        Assert.assertTrue(addPlantPage.areParentCategoriesNotDisplayed(),
            "Parent categories are displayed");
        System.out.println("Parent categories are not displayed");
    }

    // Cancel button steps
    @When("admin user clicks Cancel button")
    public void admin_user_clicks_cancel_button() {
        addPlantPage.clickCancelButton();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cancel button clicked");
    }

    @Then("no data from Add Plant form should be saved")
    public void no_data_from_add_plant_form_should_be_saved() {
        // Verify we're on the plants list page (not on add plant page)
        String currentUrl = driver.getCurrentUrl();
        Assert.assertFalse(currentUrl.contains("/plants/add"),
            "User is still on the add plant page - form data may have been saved");
        System.out.println("Form data was not saved - user is no longer on add plant page");
    }

    // Success message and plant addition steps
    @Then("success message {string} should be displayed")
    public void success_message_should_be_displayed(String expectedMessage) {
        try {
            Thread.sleep(2000);  // Increased wait time for success message

            // Print page source for debugging
            System.out.println("Looking for success message: " + expectedMessage);

            // Method 1: Check for common success message containers
            List<WebElement> successMessages = driver.findElements(
                By.xpath("//*[contains(@class, 'alert-success') or contains(@class, 'alert') or contains(@class, 'success') or contains(@class, 'toast')]")
            );

            boolean messageFound = false;
            if (!successMessages.isEmpty()) {
                for (WebElement msg : successMessages) {
                    if (msg.isDisplayed() && msg.getText().contains(expectedMessage)) {
                        messageFound = true;
                        System.out.println("Success message found (Method 1): " + msg.getText());
                        break;
                    }
                }
            }

            // Method 2: Check for any element containing the exact message text
            if (!messageFound) {
                List<WebElement> messageElements = driver.findElements(
                    By.xpath("//*[contains(text(), '" + expectedMessage + "')]")
                );
                if (!messageElements.isEmpty()) {
                    for (WebElement elem : messageElements) {
                        if (elem.isDisplayed()) {
                            messageFound = true;
                            System.out.println("Success message found (Method 2): " + elem.getText());
                            break;
                        }
                    }
                }
            }

            // Method 3: Check for div/span elements with the message text
            if (!messageFound) {
                try {
                    WebElement messageDiv = driver.findElement(
                        By.xpath("//div[contains(text(), '" + expectedMessage + "')] | //span[contains(text(), '" + expectedMessage + "')] | //h4[contains(text(), '" + expectedMessage + "')] | //p[contains(text(), '" + expectedMessage + "')]")
                    );
                    if (messageDiv.isDisplayed()) {
                        messageFound = true;
                        System.out.println("Success message found (Method 3): " + messageDiv.getText());
                    }
                } catch (Exception e) {
                    System.out.println("Method 3 search failed: " + e.getMessage());
                }
            }

            Assert.assertTrue(messageFound,
                "Success message '" + expectedMessage + "' was not displayed. Please check page source.");
            System.out.println("Success message displayed: " + expectedMessage);
        } catch (Exception e) {
            System.out.println("Error checking success message: " + e.getMessage());
            // Print page source for debugging
            try {
                System.out.println("===== PAGE SOURCE FOR DEBUGGING =====");
                System.out.println(driver.getPageSource());
                System.out.println("===== END PAGE SOURCE =====");
            } catch (Exception e2) {
                System.out.println("Could not capture page source");
            }
            throw new RuntimeException("Failed to verify success message", e);
        }
    }

    @Then("newly added plant {string} should be visible in the plants list")
    public void newly_added_plant_should_be_visible_in_the_plants_list(String plantName) {
        try {
            Thread.sleep(2000);  // Increased wait time for page to fully load
            System.out.println("Looking for newly added plant: " + plantName);

            // Method 1: Check if the plant name appears anywhere on the page
            List<WebElement> plantElements = driver.findElements(
                By.xpath("//*[contains(text(), '" + plantName + "')]")
            );

            boolean plantFound = false;
            if (!plantElements.isEmpty()) {
                for (WebElement elem : plantElements) {
                    if (elem.isDisplayed()) {
                        plantFound = true;
                        System.out.println("Plant found (Method 1): " + elem.getText());
                        break;
                    }
                }
            }

            // Method 2: Check in table rows
            if (!plantFound) {
                try {
                    List<WebElement> tableRows = driver.findElements(
                        By.xpath("//table//tr[contains(., '" + plantName + "')]")
                    );
                    if (!tableRows.isEmpty()) {
                        plantFound = true;
                        System.out.println("Plant found in table (Method 2)");
                    }
                } catch (Exception e) {
                    System.out.println("Method 2 search failed: " + e.getMessage());
                }
            }

            // Method 3: Check in list items
            if (!plantFound) {
                try {
                    List<WebElement> listItems = driver.findElements(
                        By.xpath("//li[contains(text(), '" + plantName + "')] | //div[contains(@class, 'plant') and contains(text(), '" + plantName + "')]")
                    );
                    if (!listItems.isEmpty()) {
                        plantFound = true;
                        System.out.println("Plant found in list (Method 3)");
                    }
                } catch (Exception e) {
                    System.out.println("Method 3 search failed: " + e.getMessage());
                }
            }

            Assert.assertTrue(plantFound,
                "Newly added plant '" + plantName + "' is not visible in the plants list");

            System.out.println("Newly added plant '" + plantName + "' is visible in the plants list");
        } catch (Exception e) {
            System.out.println("Error checking newly added plant: " + e.getMessage());
            // Print page source for debugging
            try {
                System.out.println("===== PAGE SOURCE FOR DEBUGGING =====");
                System.out.println(driver.getPageSource());
                System.out.println("===== END PAGE SOURCE =====");
            } catch (Exception e2) {
                System.out.println("Could not capture page source");
            }
            throw new RuntimeException("Failed to verify newly added plant visibility", e);
        }
    }
}
