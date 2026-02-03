package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PlantsPage {
    private WebDriver driver;

    // Locators
    private By addPlantButton = By.xpath("//button[contains(text(), 'Add a Plant')] | //a[contains(text(), 'Add a Plant')] | //button[contains(text(), 'Add Plant')]");
    private By pageHeading = By.xpath("//h1[contains(text(), 'Plants')] | //h2[contains(text(), 'Plants')]");
    private By plantsPageTitle = By.tagName("h1");

    public PlantsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToPlantsPage(String url) {
        driver.navigate().to(url);
    }

    public boolean isAddPlantButtonVisible() {
        try {
            WebElement button = driver.findElement(addPlantButton);
            return button.isDisplayed() && button.isEnabled();
        } catch (Exception e) {
            System.out.println("Add Plant button visibility check failed: " + e.getMessage());
            // Try alternative button locators
            try {
                return driver.findElement(By.xpath("//*[contains(text(), 'Add')]")).isDisplayed();
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public boolean isAddPlantButtonPresent() {
        try {
            return driver.findElements(addPlantButton).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddPlantButton() {
        WebElement button = driver.findElement(addPlantButton);
        button.click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isPlantsPageDisplayed() {
        try {
            // Check if we're on plants page by URL first
            if (driver.getCurrentUrl().contains("plants")) {
                return true;
            }
            // Then check for the heading
            return driver.findElement(pageHeading).isDisplayed();
        } catch (Exception e) {
            System.out.println("Plants page check failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddPlantFormDisplayed() {
        try {
            // Check for form elements that would be present on the Add Plant form
            return driver.findElements(By.xpath("//form | //input[@name='plantName'] | //input[@placeholder]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }
}

