package pages;

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

    public SellPlantPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPlantDropdown(){
        wait.until(ExpectedConditions.elementToBeClickable(plantDropdown)).click();
    }

    public List<WebElement> getPlantOptions() {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(plantDropdown));
        Select select = new Select(dropdown);

        // Optional: wait until more than 1 option is available
        wait.until(d -> select.getOptions().size() > 1);

        return select.getOptions();
    }

}
