package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import pages.SalesPage;
import pages.SellPlantPage;
import utils.DriverFactory;

import java.util.List;
public class AdminPlantDropdownSteps {
    private SalesPage salesPage;
    private SellPlantPage sellPlantPage;

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
}
