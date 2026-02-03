package stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.DashboardPage;
import pages.LoginPage;
import pages.SalesPage;
import pages.SellPlantPage;
import utils.DriverFactory;

import java.util.List;

public class AdminSalesSteps {

    private LoginPage loginPage;
    private DashboardPage dashboardPage;
    private SalesPage salesPage;
    private SellPlantPage sellPlantPage;


    // LOGIN
    @Given("Admin is logged in")
    public void admin_is_logged_in(){
        loginPage = new LoginPage(DriverFactory.getDriver());
        loginPage.open();
        dashboardPage = loginPage.login("admin", "admin123");
    }

    //SALES LIST
    @When("Admin navigates to the sales list page")
    public void admin_navigates_to_the_sales_list_page(){
        salesPage = dashboardPage.goToSalesPage();
    }

    @Then("Sales list should be displayed")
    public void sales_list_should_be_displayed(){
        Assert.assertTrue(salesPage.isSalesListDisplayed() || salesPage.isNoSalesMessageDisplayed(),
                "Sales list page is not displayed.");
    }

    //SELL PLANT BUTTON
    @Then("Sell plant button should be visible")
    public void Sell_plant_button_should_be_visible(){
        Assert.assertTrue(
                salesPage.isSellPlantButtonVisible(),
                "Sell plant button is not visible for admin"
        );
    }

    //SELL PLANT PAGE
    @And("Admin navigates to sell plant page")
    public void admin_navigates_to_sell_plant_page(){
        salesPage = new SalesPage(DriverFactory.getDriver());
        sellPlantPage = salesPage.clickSellPlantButton();
    }

    @Then("Plant dropdown show only plants with stock greater than zero")
    public void plant_dropdown_should_show_only_available_plants(){
        sellPlantPage.openPlantDropdown();
        List<WebElement> options = sellPlantPage.getPlantOptions();

        for(WebElement option : options){
            String optionText = option.getText();
            Assert.assertFalse(
                    optionText.contains("(0)"),
                    "Plant with zero stock is visible in dropdown: " + optionText
            );
        }
    }

    @And("Admin selects a plant with available stock")
    public void admin_selects_a_plant_with_available_stock(){
        sellPlantPage.selectFirstAvailablePlant();
    }

    @And("Admin enters a valid quantity")
    public void admin_enters_a_valid_quantity(){
        sellPlantPage.enterQuantity(1);
    }

    @And("Admin clicks the sell button")
    public void admin_clicks_the_sell_button(){
        sellPlantPage.clickSellButton();
    }

    @And("Sale should be created successfully")
    public void sale_should_be_created_successfully(){
        SalesPage salesPage = new SalesPage(DriverFactory.getDriver());
        Assert.assertTrue(
                salesPage.isSalesListDisplayed() || salesPage.isNoSalesMessageDisplayed(),
                "Sale creation failed or sales list not shown"
        );
    }

    @And("Admin should be redirected to the sales list page")
    public void admin_should_be_redirected_to_the_sales_list_page(){
//        SalesPage salesPage = new SalesPage(DriverFactory.getDriver());
        Assert.assertTrue(salesPage.isSalesListDisplayed(),
                "Admin was not redirected to sales list page");
    }
}
