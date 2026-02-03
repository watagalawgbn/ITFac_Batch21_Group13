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

public class DashboardUserSteps {

    WebDriver driver = DriverFactory.getDriver();
    DashboardPage dashboardPage;
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // ===== User Login =====
    @Given("regular user is logged into the dashboard")
    public void regular_user_is_logged_into_the_dashboard() {
        driver.get("http://localhost:8081/ui/login");
        driver.findElement(By.name("username")).sendKeys("testuser");
        driver.findElement(By.name("password")).sendKeys("test123");
        driver.findElement(By.cssSelector("button.btn.btn-primary.w-100.mb-3")).click();
        wait.until(ExpectedConditions.urlContains("/dashboard"));
        dashboardPage = new DashboardPage(driver);
    }

    // ===== User Dashboard Verification =====
    @Then("dashboard page should load successfully for user")
    public void dashboard_page_should_load_successfully_for_user() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"),
                "Dashboard URL does not contain /dashboard");
    }

    @Then("user dashboard cards should be visible")
    public void user_dashboard_cards_should_be_visible() {
        Assert.assertTrue(dashboardPage.isCategoryCardVisible(), "Category card not visible");
        Assert.assertTrue(dashboardPage.isPlantsCardVisible(), "Plants card not visible");
        Assert.assertTrue(dashboardPage.isSalesCardVisible(), "Sales card not visible");
    }

    @Then("dashboard menu should be highlighted for user")
    public void dashboard_menu_should_be_highlighted_for_user() {
        Assert.assertTrue(dashboardPage.isDashboardMenuActive(), "Dashboard menu is not active");
    }

    // ===== User Card Click Actions =====
    @When("user clicks on Categories dashboard card")
    public void user_clicks_on_categories_dashboard_card() {
        dashboardPage.clickCategoryCard();
    }

    @When("user clicks on Plants dashboard card")
    public void user_clicks_on_plants_dashboard_card() {
        dashboardPage.clickPlantsCard();
    }

    @When("user clicks on Sales dashboard card")
    public void user_clicks_on_sales_dashboard_card() {
        dashboardPage.clickSalesCard();
    }

    // ===== User Navigation Verification =====
    @Then("user should be navigated to Categories page")
    public void user_should_be_navigated_to_categories_page() {
        wait.until(ExpectedConditions.urlContains("/ui/categories"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/categories"),
                "User did not navigate to Categories page");
    }

    @Then("user should be navigated to Plants page")
    public void user_should_be_navigated_to_plants_page() {
        wait.until(ExpectedConditions.urlContains("/ui/plants"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/plants"),
                "User did not navigate to Plants page");
    }

    @Then("user should be navigated to Sales page")
    public void user_should_be_navigated_to_sales_page() {
        wait.until(ExpectedConditions.urlContains("/ui/sales"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/sales"),
                "User did not navigate to Sales page");
    }
}

