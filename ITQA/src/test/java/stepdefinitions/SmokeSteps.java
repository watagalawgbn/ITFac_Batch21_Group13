package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;

public class SmokeSteps {

    WebDriver driver;
    LoginPage loginPage;

    @Given("admin is logged in")
    public void admin_is_logged_in() {
        driver = DriverFactory.getDriver();
        loginPage = new LoginPage(driver);
        loginPage.open();
        loginPage.loginAsAdmin("admin", "admin123");
    }

    @When("admin navigates to sales page")
    public void admin_navigates_to_sales_page() {
        String url = ConfigReader.get("base.url") + ConfigReader.get("sales.path");
        driver.get(url);
    }

    @Then("sales list should be displayed")
    public void sales_list_should_be_displayed() {
        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/sales"));
        DriverFactory.quitDriver();
    }
}
