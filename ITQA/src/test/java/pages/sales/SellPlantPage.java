package pages.sales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class SellPlantPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    //locators
    private final By plantDropdown = By.id("plantId");
    private final By plantOptions = By.xpath("//select[@id='plantId']/option");
    private final By quantityInput = By.id("quantity");
    private final By sellButton = By.xpath("//button[contains(text(), 'Sell')]");


    public SellPlantPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPlantDropdown(){
        wait.until(ExpectedConditions.elementToBeClickable(plantDropdown)).click();
    }

    public List<WebElement> getPlantOptions() {
        // Wait until the <select> is visible
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(plantDropdown));

        // Get all <option> children
        return dropdown.findElements(By.tagName("option"));
    }

    private String selectedPlantName;

    public void selectFirstAvailablePlant() {
        WebElement dropdown = driver.findElement(plantDropdown);
        Select select = new Select(dropdown);

        WebElement option = select.getOptions().stream()
                .filter(o -> {
                    String text = o.getText(); // e.g., "Lemon (Stock: 92)"
                    if (text == null || !text.contains("Stock")) return false;

                    // Extract number
                    try {
                        String number = text.replaceAll(".*Stock: (\\d+).*", "$1");
                        return Integer.parseInt(number) > 0;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                })
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No plant with stock found"));

        selectedPlantName = option.getText().trim();
        select.selectByVisibleText(selectedPlantName);
    }


    public String getSelectedPlantName() {
        return selectedPlantName;
    }


    public void enterQuantity(int quantity){
        WebElement qty = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        qty.clear();
        qty.sendKeys(String.valueOf(quantity));
    }

    public void clickSellButton(){
        wait.until(ExpectedConditions.elementToBeClickable(sellButton)).click();
    }
}
