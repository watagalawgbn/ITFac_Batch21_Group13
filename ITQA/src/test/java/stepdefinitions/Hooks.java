package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import pages.login.LoginPage;
import pages.dashboard.DashboardPage;
import utils.DriverFactory;

public class Hooks {

    private static boolean isAdminLoggedIn = false;
    private static boolean isUserLoggedIn = false;
    private static String baseUrl = "http://localhost:8081";

    // ========== UI TEST HOOKS ==========

    @Before("@UI and @Admin")
    public void beforeUIAdminScenario(Scenario scenario) {
        System.out.println("\n========== STARTING UI ADMIN SCENARIO ==========");

        // Always initialize a fresh driver for each scenario
        DriverFactory.initializeDriver("chrome");
        loginAsAdmin();
        isAdminLoggedIn = true;
        isUserLoggedIn = false;
    }

    @Before("@UI and @User")
    public void beforeUIUserScenario(Scenario scenario) {
        System.out.println("\n========== STARTING UI USER SCENARIO ==========");

        // Always initialize a fresh driver for each scenario
        DriverFactory.initializeDriver("chrome");
        loginAsUser();
        isUserLoggedIn = true;
        isAdminLoggedIn = false;
    }

    @Before("@UI and not @Admin and not @User")
    public void beforeUIScenario(Scenario scenario) {
        System.out.println("\n========== STARTING UI SCENARIO ==========");
        // Always initialize a fresh driver for each scenario
        DriverFactory.initializeDriver("chrome");
    }

    // ========== COMMON HOOKS ==========

    @After(value = "@UI", order = 0)
    public void tearDownUI(Scenario scenario) {
        if (DriverFactory.getDriver() != null) {
            if (scenario.isFailed()) {
                System.out.println("Scenario failed - cleaning up browser...");
            } else {
                System.out.println("Scenario completed - cleaning up browser...");
            }
            try {
                DriverFactory.quitDriver();
            } catch (Exception e) {
                System.err.println("Error during quit: " + e.getMessage());
            } finally {
                isAdminLoggedIn = false;
                isUserLoggedIn = false;
            }
        }
    }

    @After("@Logout")
    public void afterLogoutScenario() {
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            driver.get(baseUrl + "/ui/logout");
            isAdminLoggedIn = false;
            isUserLoggedIn = false;
        }
    }

    // ========== HELPERS & ACCESSORS ==========

    /**
     * This method provides the base URL to CategorySteps and SalesSteps.
     * Re-adding this fixes the compilation errors.
     */
    public static String getBaseUrl() {
        return baseUrl;
    }

    public static WebDriver getDriver() {
        return DriverFactory.getDriver();
    }

    private void loginAsAdmin() {
        WebDriver driver = DriverFactory.getDriver();
        LoginPage loginPage = new LoginPage(driver);
        driver.get(baseUrl + "/ui/login");
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin123");
        loginPage.clickLoginButton();
    }

    private void loginAsUser() {
        WebDriver driver = DriverFactory.getDriver();
        LoginPage loginPage = new LoginPage(driver);
        driver.get(baseUrl + "/ui/login");
        loginPage.enterUsername("testuser");
        loginPage.enterPassword("test123");
        loginPage.clickLoginButton();
    }

    private boolean isDriverSessionValid() {
        WebDriver currentDriver = DriverFactory.getDriver();
        if (currentDriver == null) return false;
        try {
            currentDriver.getCurrentUrl();
            return true;
        } catch (Exception e) {
            DriverFactory.quitDriver();
            return false;
        }
    }
}