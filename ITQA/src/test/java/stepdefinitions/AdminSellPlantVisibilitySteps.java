package stepdefinitions;

import io.cucumber.java.en.Then;
import org.testng.Assert;
import pages.SalesPage;

public class AdminSellPlantVisibilitySteps {
    private SalesPage salesPage;
    @Then("Sell plant button should be visible")
    public void Sell_plant_button_should_be_visible(){
        salesPage = new SalesPage(utils.DriverFactory.getDriver());
        Assert.assertTrue(
                salesPage.isSellPlantButtonVisible(),
                "Sell plant button is not visible for admin"
        );
    }
}
