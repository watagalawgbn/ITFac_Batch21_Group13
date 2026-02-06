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

    public void selectFirstAvailablePlant() {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(plantDropdown));
        dropdown.click();

        List<WebElement> options = getPlantOptions();

        // Start from index 1 to skip the first placeholder option
        for (int i = 1; i < options.size(); i++) {
            WebElement option = options.get(i);
            String text = option.getText();
            if (!text.contains("(0)")) {
                option.click(); // click triggers selection
                return;
            }
        }

        throw new RuntimeException("No plant with available stock found");
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
