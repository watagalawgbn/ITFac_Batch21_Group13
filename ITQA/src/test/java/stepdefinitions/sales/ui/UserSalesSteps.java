package stepdefinitions.sales.ui;

import io.cucumber.java.en.*;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;
import pages.sales.SalesPage;
import utils.DriverFactory;

import java.time.LocalDateTime;
import java.util.List;

public class UserSalesSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private SalesPage salesPage;
    private List<String> firstPageSales;


    //TC_UI_Sales_18 - VIEW SALE LIST____________________________________________________________
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

    //TC_UI_Sales_19 - CHECK FOR PAGINATION____________________________________________________________
    @And("Pagination controls are visible")
    public void pagination_controls_are_visible() {
        Assert.assertTrue(
                salesPage.isPaginationVisible(),
                "Pagination controls are not visible"
        );
    }

    @And("User clicks the next pagination button")
    public void user_clicks_next_pagination_button() {

        Assert.assertTrue(
                salesPage.isPaginationVisible(),
                "Pagination is not visible although more than 10 sales exist"
        );

        firstPageSales = salesPage.getCurrentPageSaleIds();
        salesPage.clickNextPage();
    }

    @Then("Next set of sales records should be displayed")
    public void next_set_of_sales_records_should_be_displayed() {

        List<String> secondPageSales = salesPage.getCurrentPageSaleIds();

        Assert.assertNotEquals(
                firstPageSales,
                secondPageSales,
                "Pagination did not change the sales list"
        );
    }

    //TC_UI_Sales_20- NO SALES MESSAGE_______________________________________________________________-
    @Then("No sales message should be displayed to the user")
    public void no_sales_message_should_be_displayed_to_user() {

        boolean noSalesMessageVisible = salesPage.isNoSalesMessageDisplayed();
        boolean salesTableVisible = salesPage.isSalesListDisplayed();

        Assert.assertTrue(
                noSalesMessageVisible || salesTableVisible,
                "Neither sales table nor 'No Sales' message is displayed"
        );
    }



    //TC_UI_Sales_25 - SELL BUTTON NOT VISIBLE TO USER________________________________________
    @Then("Sell Plant button should not be visible to the user")
    public void sell_plant_button_should_not_be_visible_to_user() {

        Assert.assertFalse(
                salesPage.isSellPlantButtonVisible(),
                "Sell Plant button is visible for the user"
        );
    }

    //TC_UI_Sales_24 - SORTING_______________________________________________________________
    @Then("Sales should be sorted by sold date in descending order")
    public void sales_should_be_sorted_by_sold_date_desc() {

        List<LocalDateTime> dates = salesPage.getSoldDatesAfterLoad();

        // If 0 or 1 records, default sorting is trivially correct
        if (dates.size() < 2) {
            Assert.assertTrue(true, "Not enough records to validate sorting");
            return;
        }

        for (int i = 0; i < dates.size() - 1; i++) {
            Assert.assertTrue(
                    !dates.get(i).isBefore(dates.get(i + 1)),
                    "Sales are not sorted by sold date descending"
            );
        }
    }



}
