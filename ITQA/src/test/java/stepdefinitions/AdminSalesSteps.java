package stepdefinitions;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SalesPage;
import utils.DriverFactory;

public class AdminSalesSteps {

    private final LoginPage loginPage = new LoginPage(DriverFactory.getDriver());
    private DashboardPage dashboardPage;
    private SalesPage salesPage;

    @Given("Admin is logged in")
    public void admin_is_logged_in(){
        loginPage.open();
        dashboardPage = loginPage.login("admin", "admin123");
    }

    @When("Admin navigates to the sales list page")
    public void admin_navigates_to_the_sales_list_page(){
        salesPage = dashboardPage.goToSalesPage();
    }

    @Then("Sales list should be displayed")
    public void sales_list_should_be_displayed(){
        Assert.assertTrue(salesPage.isSalesListDisplayed() || salesPage.isNoSalesMessageDisplayed(),
                "Sales list page is not displayed.");
    }

}
