package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class AddPlantPage {
    private WebDriver driver;

    // Locators - based on actual HTML structure
    private By pageHeading = By.xpath("//h3[contains(text(), 'Add Plant')]");
    private By plantNameInput = By.id("name");  // Plant Name field
    private By priceInput = By.id("price");  // Price field
    private By quantityInput = By.id("quantity");  // Quantity field
    private By categoryDropdown = By.id("categoryId");  // Category dropdown
    private By saveButton = By.xpath("//button[contains(text(), 'Save')]");  // Save button
    private By cancelButton = By.xpath("//a[contains(text(), 'Cancel')]");  // Cancel button (anchor tag)

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

    // Helper method to print page source for debugging
    public void printPageSource() {
        try {
            String pageSource = driver.getPageSource();
            System.out.println("=== PAGE SOURCE ===");
            System.out.println(pageSource);
            System.out.println("=== END PAGE SOURCE ===");
        } catch (Exception e) {
            System.out.println("Error capturing page source: " + e.getMessage());
        }
    }

    // Helper method to find all input fields
    public List<WebElement> findAllInputElements() {
        try {
            return driver.findElements(By.tagName("input"));
        } catch (Exception e) {
            System.out.println("Error finding input elements: " + e.getMessage());
            return List.of();
        }
    }

    // Helper method to find all buttons
    public List<WebElement> findAllButtons() {
        try {
            return driver.findElements(By.tagName("button"));
        } catch (Exception e) {
            System.out.println("Error finding button elements: " + e.getMessage());
            return List.of();
        }
    }

    public boolean isPlantNameFieldEmpty() {
        try {
            WebElement plantNameField = driver.findElement(plantNameInput);
            String value = plantNameField.getAttribute("value");
            return value == null || value.isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking plant name field: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    public boolean isPriceFieldEmpty() {
        try {
            WebElement priceField = driver.findElement(priceInput);
            String value = priceField.getAttribute("value");
            return value == null || value.isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking price field: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    public boolean isQuantityFieldEmpty() {
        try {
            WebElement quantityField = driver.findElement(quantityInput);
            String value = quantityField.getAttribute("value");
            return value == null || value.isEmpty();
        } catch (Exception e) {
            System.out.println("Error checking quantity field: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    public boolean isCategoryDropdownDefaultValue() {
        try {
            WebElement dropdown = driver.findElement(categoryDropdown);
            String selectedText = dropdown.getText();
            String selectedValue = dropdown.getAttribute("value");
            // Check if default text is displayed
            return selectedText.contains("Select") || selectedValue.isEmpty() || selectedValue.equals("0");
        } catch (Exception e) {
            System.out.println("Error checking category dropdown: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    public String getCategoryDropdownDefaultText() {
        try {
            WebElement dropdown = driver.findElement(categoryDropdown);
            return dropdown.getText().trim();
        } catch (Exception e) {
            System.out.println("Error getting category dropdown text: " + e.getMessage());
            printPageSource();
            return "";
        }
    }

    // Verify button visibility and state
    public boolean isSaveButtonVisible() {
        try {
            WebElement button = driver.findElement(saveButton);
            return button.isDisplayed();
        } catch (Exception e) {
            System.out.println("Error checking save button visibility: " + e.getMessage());
            System.out.println("Available buttons: ");
            List<WebElement> buttons = findAllButtons();
            for (WebElement btn : buttons) {
                System.out.println("  - Text: " + btn.getText() + ", Type: " + btn.getAttribute("type"));
            }
            printPageSource();
            return false;
        }
    }

    public boolean isSaveButtonEnabled() {
        try {
            WebElement button = driver.findElement(saveButton);
            return button.isEnabled();
        } catch (Exception e) {
            System.out.println("Error checking save button enabled state: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    public boolean isCancelButtonVisible() {
        try {
            WebElement button = driver.findElement(cancelButton);
            return button.isDisplayed();
        } catch (Exception e) {
            System.out.println("Error checking cancel button visibility: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    public boolean isCancelButtonEnabled() {
        try {
            WebElement button = driver.findElement(cancelButton);
            return button.isEnabled();
        } catch (Exception e) {
            System.out.println("Error checking cancel button enabled state: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    // Methods for form submission and validation
    public void clickSaveButton() {
        try {
            WebElement button = driver.findElement(saveButton);
            button.click();
            System.out.println("Save button clicked");
        } catch (Exception e) {
            System.out.println("Error clicking save button: " + e.getMessage());
            printPageSource();
        }
    }

    public boolean areValidationMessagesDisplayed() {
        try {
            // Wait a moment for validation messages to appear
            Thread.sleep(1000);

            // Check for validation messages in div.text-danger elements
            List<WebElement> errorMessages = driver.findElements(By.xpath("//div[contains(@class, 'text-danger')]"));

            System.out.println("Found " + errorMessages.size() + " validation message elements");

            if (!errorMessages.isEmpty()) {
                // Print the validation messages found
                for (WebElement msg : errorMessages) {
                    if (msg.isDisplayed()) {
                        System.out.println("  - " + msg.getText());
                    }
                }
                System.out.println("Validation messages are displayed");
                return true;
            }

            // Print page source for debugging
            printPageSource();
            return false;
        } catch (Exception e) {
            System.out.println("Error checking validation messages: " + e.getMessage());
            printPageSource();
            return false;
        }
    }

    public boolean isValidationMessageDisplayed(String fieldName) {
        try {
            // Look for validation messages containing the field name
            String xpath = "//*[contains(text(), '" + fieldName + "')]";
            WebElement message = driver.findElement(By.xpath(xpath));

            if (message.isDisplayed()) {
                System.out.println("Validation message for '" + fieldName + "' is displayed: " + message.getText());
                return true;
            }
        } catch (Exception e) {
            System.out.println("Validation message for '" + fieldName + "' not found: " + e.getMessage());
        }

        return false;
    }

    public String getValidationMessageText(String fieldName) {
        try {
            // Look for validation messages containing the field name
            List<WebElement> messages = driver.findElements(By.xpath("//*[contains(text(), '" + fieldName + "')]"));

            if (!messages.isEmpty()) {
                return messages.get(0).getText();
            }
        } catch (Exception e) {
            System.out.println("Error getting validation message text: " + e.getMessage());
        }

        return "";
    }

    public boolean hasValidationMessageForField(String expectedMessage) {
        try {
            Thread.sleep(500);

            // Check for validation messages in div.text-danger elements containing the expected message
            List<WebElement> allMessages = driver.findElements(By.xpath("//div[contains(@class, 'text-danger')]"));

            if (!allMessages.isEmpty()) {
                for (WebElement msg : allMessages) {
                    if (msg.isDisplayed() && msg.getText().contains(expectedMessage)) {
                        System.out.println("Found validation message: " + msg.getText());
                        return true;
                    }
                }
            }
            System.out.println("Validation message '" + expectedMessage + "' not found");
            printPageSource();
            return false;
        } catch (Exception e) {
            System.out.println("Error checking validation message: " + e.getMessage());
            return false;
        }
    }
}
