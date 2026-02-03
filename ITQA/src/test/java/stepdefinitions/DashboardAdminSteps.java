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

    @Given("admin is logged into the dashboard")
    public void admin_is_logged_into_the_dashboard() {
        driver.get("http://localhost:8081/ui/login");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin123");
        driver.findElement(By.cssSelector("button.btn.btn-primary.w-100.mb-3")).click();
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/dashboard"));
        dashboardPage = new DashboardPage(driver);
    }

    @Then("dashboard page should load successfully for admin")
    public void dashboard_page_should_load_successfully_for_admin() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"));
    }

    @Then("all admin navigation menu items should be visible")
    public void all_admin_navigation_menu_items_should_be_visible() {
        Assert.assertTrue(dashboardPage.isCategoriesMenuVisible());
        Assert.assertTrue(dashboardPage.isPlantsMenuVisible());
        Assert.assertTrue(dashboardPage.isSalesMenuVisible());
    }

    @Then("category plant and sales summaries should be displayed")
    public void category_plant_and_sales_summaries_should_be_displayed() {
        Assert.assertTrue(dashboardPage.isCategoryCardVisible());
        Assert.assertTrue(dashboardPage.isPlantsCardVisible());
        Assert.assertTrue(dashboardPage.isSalesCardVisible());
    }

    @Then("dashboard menu should be highlighted for admin")
    public void dashboard_menu_should_be_highlighted_for_admin() {
        Assert.assertTrue(dashboardPage.isDashboardMenuActive());
    }

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

    @Then("admin should be navigated to Categories page")
    public void admin_should_be_navigated_to_categories_page() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/ui/categories"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/categories"));
    }

    @Then("admin should be navigated to Plants page")
    public void admin_should_be_navigated_to_plants_page() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/ui/plants"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/plants"));
    }

    @Then("admin should be navigated to Sales page")
    public void admin_should_be_navigated_to_sales_page() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
            .until(ExpectedConditions.urlContains("/ui/sales"));
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/sales"));
    }
}
