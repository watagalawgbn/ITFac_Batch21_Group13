package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SalesPage;
import utils.DriverFactory;

public class UserSalesSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private SalesPage salesPage;

    @Given("User is logged in")
    public void user_is_logged_in() {
        loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.open();
        dashboardPage = loginPage.login("testuser", "test123");
    }

    @When("User navigates to the sales list page")
    public void user_navigates_to_sales_page() {
        salesPage = dashboardPage.goToSalesPage();
    }

    @Then("Sales list should be visible to the user")
    public void sales_list_should_be_visible_to_user() {

        boolean isVisible =
                salesPage.isSalesListDisplayed()
                        || salesPage.isNoSalesMessageDisplayed();

        Assert.assertTrue(isVisible,
                "Sales list or No Sales message is not visible to the user");
    }
}
