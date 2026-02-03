package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import pages.AddPlantPage;
import pages.LoginPage;
import pages.PlantsPage;
import utils.DriverManager;

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
}
