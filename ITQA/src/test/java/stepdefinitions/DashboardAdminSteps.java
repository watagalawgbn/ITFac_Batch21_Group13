package stepdefinitions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.dashboard.DashboardPage;
import utils.DriverFactory;

import java.time.Duration;

public class DashboardAdminSteps {

    WebDriver driver = DriverFactory.getDriver();
    DashboardPage dashboardPage;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // ===== LOGIN =====
    @Given("admin is logged into the dashboard")
    public void admin_is_logged_into_the_dashboard() {
        driver.get("http://localhost:8081/ui/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button.btn.btn-primary.w-100.mb-3")).click();
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        dashboardPage = new DashboardPage(driver);
    }

    // ===== DASHBOARD PAGE =====
    @Then("dashboard page should load successfully for admin")
    public void dashboard_page_should_load_successfully_for_admin() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Dashboard URL does not contain /dashboard");
    }

    @Then("all admin navigation menu items should be visible")
    public void all_admin_navigation_menu_items_should_be_visible() {
        Assert.assertTrue(dashboardPage.isCategoriesMenuVisible(), "Categories menu not visible");
        Assert.assertTrue(dashboardPage.isPlantsMenuVisible(), "Plants menu not visible");
        Assert.assertTrue(dashboardPage.isSalesMenuVisible(), "Sales menu not visible");
    }

    @Then("dashboard menu should be highlighted for admin")
    public void dashboard_menu_should_be_highlighted_for_admin() {
        Assert.assertTrue(dashboardPage.isDashboardMenuActive(), "Dashboard menu is not active");
    }

    // ===== CARD CLICKS =====
    @When("admin clicks on Categories dashboard card")
    public void admin_clicks_on_categories_dashboard_card() {
        dashboardPage.clickCategoryCard();
    }

    @When("admin clicks on Plants dashboard card")
    public void admin_clicks_on_plants_dashboard_card() {
        dashboardPage.clickPlantsCard();
    }

    @When("admin clicks on Sales dashboard card")
    public void admin_clicks_on_sales_dashboard_card() {
        dashboardPage.clickSalesCard();
    }

    // ===== NAVIGATION VERIFICATION =====
    @Then("admin should be navigated to Categories page")
    public void admin_should_be_navigated_to_categories_page() {
        wait.until(ExpectedConditions.urlContains("/ui/categories"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/categories"),
                "Admin did not navigate to Categories page");
    }

    @Then("admin should be navigated to Plants page")
    public void admin_should_be_navigated_to_plants_page() {
        wait.until(ExpectedConditions.urlContains("/ui/plants"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/plants"),
                "Admin did not navigate to Plants page");
    }

    @Then("admin should be navigated to Sales page")
    public void admin_should_be_navigated_to_sales_page() {
        wait.until(ExpectedConditions.urlContains("/ui/sales"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/sales"),
                "Admin did not navigate to Sales page");
    }

    // ===== SIDEBAR MENU =====
    @When("admin clicks on Categories menu")
    public void admin_clicks_on_categories_menu() {
        dashboardPage.clickCategoriesMenu();
    }

    @When("admin clicks on Plants menu")
    public void admin_clicks_on_plants_menu() {
        dashboardPage.clickPlantsMenu();
    }

    @Then("Categories menu should be highlighted")
    public void categories_menu_should_be_highlighted() {
        Assert.assertTrue(dashboardPage.isCategoriesMenuActive(), "Categories menu is not highlighted");
    }

    @Then("Plants menu should be highlighted")
    public void plants_menu_should_be_highlighted() {
        Assert.assertTrue(dashboardPage.isPlantsMenuActive(), "Plants menu is not highlighted");
    }
}
