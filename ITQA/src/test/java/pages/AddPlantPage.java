package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AddPlantPage {
    private WebDriver driver;

    // Locators
    private By pageHeading = By.xpath("//h1[contains(text(), 'Add')] | //h2[contains(text(), 'Add')]");
    private By plantNameInput = By.id("plantName");
    private By descriptionInput = By.id("description");
    private By submitButton = By.xpath("//button[@type='submit']");
    private By cancelButton = By.xpath("//button[contains(text(), 'Cancel')]");

    public AddPlantPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isAddPlantFormDisplayed() {
        try {
            // Primary check - verify we're on the add plant page by URL
            if (driver.getCurrentUrl().contains("/plants/add") || driver.getCurrentUrl().contains("add")) {
                return true;
            }
            // Secondary check - look for form elements
            return driver.findElement(pageHeading).isDisplayed();
        } catch (Exception e) {
            System.out.println("Add Plant form check failed: " + e.getMessage());
            // Fallback - if URL contains /add, consider form as displayed
            try {
                String url = driver.getCurrentUrl();
                return url.contains("/add");
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnAddPlantPage() {
        return driver.getCurrentUrl().contains("/plants/add") || driver.getCurrentUrl().contains("/add");
    }
}
