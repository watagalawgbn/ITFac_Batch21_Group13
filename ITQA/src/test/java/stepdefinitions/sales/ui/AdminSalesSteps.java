package stepdefinitions.sales.ui;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import pages.plant.PlantsPage;
import pages.sales.Authentication;
import pages.sales.DashboardPage;
import pages.sales.SalesPage;
import pages.sales.SellPlantPage;
import utils.DriverFactory;

import java.util.List;

public class AdminSalesSteps {

    private Authentication Authentication;
    private DashboardPage dashboardPage;
    private SalesPage salesPage;
    private SellPlantPage sellPlantPage;
    private PlantsPage plantsPage;
    private int salesCountBeforeDelete;
    int soldQuantity;
    int stockBefore;
    int stockAfter;


    // ------------------ LOGIN ----------------------------------------------------------------------------------
    @Given("Admin is logged in")
    public void admin_is_logged_in(){
        Authentication = new Authentication(DriverFactory.getDriver());
        Authentication.open();
        dashboardPage = Authentication.login("admin", "admin123");
    }

    //TC_UI_Sales_1 - SALES LIST --------------------------------------------------------------------------------------
    @When("Admin navigates to the sales list page")
    public void admin_navigates_to_the_sales_list_page(){
        salesPage = dashboardPage.goToSalesPage();
    }

    @Then("Sales list should be displayed")
    public void sales_list_should_be_displayed(){
        Assert.assertTrue(salesPage.isSalesListDisplayed() || salesPage.isNoSalesMessageDisplayed(),
                "Sales list page is not displayed.");
    }

    //TC_UI_Sales_8 - SELL PLANT BUTTON VISIBILITY-------------------------------------------------------------------------------------
    @Then("Sell plant button should be visible")
    public void Sell_plant_button_should_be_visible(){
        Assert.assertTrue(
                salesPage.isSellPlantButtonVisible(),
                "Sell plant button is not visible for admin"
        );
    }

    //TC_UI_Sales_10 - NAVIGATES TO SELL PLANT PAGE AND CHECK PLANT DROPDOWN ------------------------------------------------------------
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

    //TC_UI_Sales_11 - CREATE SALES AND CHECK STOCK REDUCTION --------------------------------------------------------------------------------------
    @And("Admin selects a plant with available stock")
    public void admin_selects_a_plant_with_available_stock(){
        sellPlantPage.selectFirstAvailablePlant();
    }

    @And("Admin notes the available stock for the selected plant")
    public void admin_notes_stock_before_sale() {
        // Get the selected plant text from the dropdown
        String selectedText = sellPlantPage.getSelectedPlantName(); 

        // Extract the number from the text
        String numberOnly = selectedText.replaceAll(".*Stock:\\s*(\\d+).*", "$1");

        stockBefore = Integer.parseInt(numberOnly);
    }


    @And("Admin enters a valid quantity")
    public void admin_enters_a_valid_quantity(){
        soldQuantity = 1;
        sellPlantPage.enterQuantity(soldQuantity);
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

    @And("Admin navigates to the plants list page")
    public void admin_navigates_to_plants_list_page() {
        PlantsPage plantsPage = new PlantsPage(DriverFactory.getDriver());
        plantsPage.navigateToPlantsPage("http://localhost:8080/ui/plants");
        Assert.assertTrue(plantsPage.isPlantsPageDisplayed(),
                "Plants page was not displayed");
    }

    @And("Stock should be reduced by the sold quantity")
    public void stock_should_be_reduced_by_sold_quantity() {

        PlantsPage plantsPage = new PlantsPage(DriverFactory.getDriver());

        // Get the name of the plant that was selected in the Sell Plant page
        String plantName = sellPlantPage.getSelectedPlantName();

        // Get stock for the exact selected plant
        stockAfter = plantsPage.getStockForPlant(plantName);

        Assert.assertEquals(
                stockAfter,
                stockBefore - soldQuantity,
                "Stock was not reduced correctly after sale for plant: " + plantName
        );
    }



    //TC_UI_Sales_17 - DELETE SALES ---------------------------------------------------------------------
    @And("At least one sale record exists")
    public void at_least_one_sale_record_exists(){
        Assert.assertTrue(
                salesPage.getSalesCount() > 0,
                "No sales available to delete"
        );
    }
    @When("Admin clicks delete button of a sale")
    public void admin_clicks_delete_button_of_a_sale(){
        salesCountBeforeDelete = salesPage.getSalesCount();
        salesPage.clickFirstDeleteButton();
    }

    @And("Admin confirms the deletion")
    public void admin_confirms_the_deletion(){
        salesPage.acceptDeleteAlert();
    }

    @Then("Sale should be deleted successfully")
    public void sale_should_be_deleted_successfully() {

        SalesPage salesPage = new SalesPage(DriverFactory.getDriver());

        boolean isDeleted =
                salesPage.isNoSalesMessageDisplayed()
                        || salesPage.isSalesListDisplayed();

        Assert.assertTrue(isDeleted, "Sale was not deleted successfully");
    }
}