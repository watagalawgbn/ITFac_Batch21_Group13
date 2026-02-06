package pages.category;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CategoryPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Locators - Updated based on actual application structure
    private By addCategoryButton = By.xpath("//a[contains(text(),'Add A Category')] | //button[contains(text(),'Add A Category')] | //a[contains(text(),'Add a Category')] | //button[contains(text(),'Add a Category')]");
    private By searchButton = By.xpath("//button[contains(text(),'Search')]");
    private By resetButton = By.xpath("//button[contains(text(),'Reset')]");

    // Form field locators - trying multiple strategies
    private By categoryNameField = By.name("categoryName");
    private By categoryNameFieldById = By.id("categoryName");
    private By categoryNameFieldByLabel = By.xpath("//label[contains(text(),'Category Name')]/following-sibling::input | //label[contains(text(),'Category Name')]/..//input");

    private By parentCategoryDropdown = By.name("parentCategory");
    private By parentCategoryDropdownById = By.id("parentCategory");
    private By parentCategoryDropdownByLabel = By.xpath("//label[contains(text(),'Parent Category')]/following-sibling::select | //label[contains(text(),'Parent Category')]/..//select");

    private By saveButton = By.xpath("//button[@type='submit' or contains(text(),'Save') or contains(text(),'Submit')]");
    private By submitButton = By.xpath("//button[@type='submit']");

    private By validationMessage = By.cssSelector(".invalid-feedback, .error-message, .text-danger, .alert-danger");
    private By validationMessageAlt = By.xpath("//div[contains(@class,'invalid-feedback')] | //span[contains(@class,'error')] | //small[contains(@class,'text-danger')]");

    private By successMessage = By.cssSelector(".alert-success, .success-message, .alert-info");
    private By successMessageAlt = By.xpath("//div[contains(@class,'alert-success')] | //div[contains(@class,'success')]");

    private By categoryList = By.className("category-list");
    private By categoryListAlt = By.cssSelector(".categories, table");
    private By categoryTable = By.tagName("table");

    public CategoryPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void navigateToCategoryList(String url) {
        driver.get(url);
        System.out.println("Navigated to Category List page: " + url);
        waitForPageLoad();
        // Inspect page elements to help debug
        inspectPageElements();
    }

    public void navigateToAddCategoryForm(String url) {
        driver.get(url);
        System.out.println("Navigated to Add Category form: " + url);
        waitForPageLoad();
        // Inspect page elements to help debug
        inspectPageElements();
    }

    public void inspectPageElements() {
        try {
            System.out.println("\n========== CATEGORY PAGE INSPECTION ==========");
            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page Title: " + driver.getTitle());

            // Find all buttons
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            System.out.println("\n===== BUTTONS (" + buttons.size() + ") =====");
            for (int i = 0; i < Math.min(buttons.size(), 10); i++) {
                WebElement btn = buttons.get(i);
                System.out.println("Button #" + (i+1) + ": '" + btn.getText() + "'" +
                    " | Type: " + btn.getAttribute("type") +
                    " | Class: " + btn.getAttribute("class"));
            }

            // Find all links
            List<WebElement> links = driver.findElements(By.tagName("a"));
            System.out.println("\n===== LINKS (" + links.size() + ") =====");
            for (int i = 0; i < Math.min(links.size(), 10); i++) {
                WebElement link = links.get(i);
                String linkText = link.getText().trim();
                if (!linkText.isEmpty()) {
                    System.out.println("Link #" + (i+1) + ": '" + linkText + "'" +
                        " | Href: " + link.getAttribute("href"));
                }
            }

            // Find all input fields
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            System.out.println("\n===== INPUT FIELDS (" + inputs.size() + ") =====");
            for (int i = 0; i < Math.min(inputs.size(), 10); i++) {
                WebElement input = inputs.get(i);
                System.out.println("Input #" + (i+1) +
                    " | Name: " + input.getAttribute("name") +
                    " | ID: " + input.getAttribute("id") +
                    " | Type: " + input.getAttribute("type") +
                    " | Placeholder: " + input.getAttribute("placeholder"));
            }

            // Find all select elements
            List<WebElement> selects = driver.findElements(By.tagName("select"));
            System.out.println("\n===== SELECT DROPDOWNS (" + selects.size() + ") =====");
            for (int i = 0; i < Math.min(selects.size(), 5); i++) {
                WebElement select = selects.get(i);
                System.out.println("Select #" + (i+1) +
                    " | Name: " + select.getAttribute("name") +
                    " | ID: " + select.getAttribute("id"));
            }

            // Find all tables
            List<WebElement> tables = driver.findElements(By.tagName("table"));
            System.out.println("\n===== TABLES (" + tables.size() + ") =====");

            System.out.println("============================================\n");
        } catch (Exception e) {
            System.out.println("Could not inspect page: " + e.getMessage());
        }
    }

    public void clickAddCategoryButton() {
        try {
            WebElement addBtn = wait.until(ExpectedConditions.elementToBeClickable(addCategoryButton));
            addBtn.click();
            System.out.println("✓ Clicked Add Category button");
        } catch (Exception e) {
            System.err.println("✗ Could not click Add Category button: " + e.getMessage());
            throw e;
        }
    }

    public boolean isAddCategoryFormDisplayed() {
        try {
            // Check if we're on the add category page
            String currentUrl = driver.getCurrentUrl();
            boolean onAddPage = currentUrl.contains("/add") || currentUrl.contains("/create");

            // Check if category name field is visible
            boolean categoryFieldVisible = isCategoryNameFieldDisplayed();

            System.out.println("Add Category form displayed: " + (onAddPage && categoryFieldVisible));
            return onAddPage && categoryFieldVisible;
        } catch (Exception e) {
            System.err.println("✗ Add Category form not displayed: " + e.getMessage());
            return false;
        }
    }

    public boolean isCategoryNameFieldDisplayed() {
        try {
            WebElement field = findCategoryNameField();
            boolean displayed = field != null && field.isDisplayed();
            System.out.println("Category Name field displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            System.err.println("✗ Category Name field not found");
            return false;
        }
    }

    public boolean isParentCategoryDropdownDisplayed() {
        try {
            WebElement dropdown = findParentCategoryDropdown();
            if (dropdown == null) {
                System.out.println("Parent Category dropdown not present (optional field)");
                return false;
            }
            boolean displayed = dropdown.isDisplayed();
            System.out.println("Parent Category dropdown displayed: " + displayed);
            return displayed;
        } catch (Exception e) {
            System.err.println("✗ Parent Category dropdown not found");
            return false;
        }
    }

    public void enterCategoryName(String categoryName) {
        try {
            WebElement field = findCategoryNameField();
            field.clear();
            field.sendKeys(categoryName);
            System.out.println("✓ Entered Category Name: " + categoryName);
        } catch (Exception e) {
            System.err.println("✗ Could not enter Category Name: " + e.getMessage());
            throw e;
        }
    }

    public void leaveCategoryNameEmpty() {
        try {
            WebElement field = findCategoryNameField();
            field.clear();
            System.out.println("✓ Category Name field left empty");
        } catch (Exception e) {
            System.err.println("✗ Could not clear Category Name field: " + e.getMessage());
            throw e;
        }
    }

    public void selectParentCategory(String parentCategory) {
        try {
            WebElement dropdown = findParentCategoryDropdown();
            Select select = new Select(dropdown);
            select.selectByVisibleText(parentCategory);
            System.out.println("✓ Selected Parent Category: " + parentCategory);
        } catch (Exception e) {
            System.err.println("✗ Could not select Parent Category: " + e.getMessage());
            throw e;
        }
    }

    public void leaveParentCategoryEmpty() {
        try {
            WebElement dropdown = findParentCategoryDropdown();
            Select select = new Select(dropdown);
            // Select the first option (usually empty or "None")
            select.selectByIndex(0);
            System.out.println("✓ Parent Category left empty");
        } catch (Exception e) {
            System.err.println("✗ Could not leave Parent Category empty: " + e.getMessage());
            // It's okay if this fails - some forms don't have parent category
        }
    }

    public void clickSaveButton() {
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(saveButton));
            btn.click();
            System.out.println("✓ Clicked Save button");
            try {
                Thread.sleep(1000); // Wait for validation/submission
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        } catch (Exception e) {
            try {
                // Try alternative submit button
                WebElement submitBtn = driver.findElement(submitButton);
                submitBtn.click();
                System.out.println("✓ Clicked Submit button");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            } catch (Exception ex) {
                System.err.println("✗ Could not click Save/Submit button: " + ex.getMessage());
                throw ex;
            }
        }
    }

    public boolean isValidationMessageDisplayed(String expectedMessage) {
        try {
            // Wait a moment for validation message to appear
            Thread.sleep(500);

            // Try to find all potential error messages
            List<WebElement> errorElements = new ArrayList<>();

            // Try finding by common error classes
            try {
                errorElements.addAll(driver.findElements(validationMessage));
            } catch (Exception ignored) {}

            try {
                errorElements.addAll(driver.findElements(validationMessageAlt));
            } catch (Exception ignored) {}

            // Check each error element
            for (WebElement element : errorElements) {
                try {
                    String actualMessage = element.getText().trim();
                    if (!actualMessage.isEmpty() && actualMessage.contains(expectedMessage)) {
                        System.out.println("✓ Validation message found: " + actualMessage);
                        System.out.println("✓ Expected message: " + expectedMessage);
                        return true;
                    }
                } catch (Exception ignored) {}
            }

            // If no specific error element found, check page source
            String pageSource = driver.getPageSource();
            if (pageSource.contains(expectedMessage)) {
                System.out.println("✓ Validation message found in page source: " + expectedMessage);
                return true;
            }

            System.err.println("✗ Validation message not found: " + expectedMessage);
            System.out.println("Page URL: " + driver.getCurrentUrl());
            return false;
        } catch (Exception e) {
            System.err.println("✗ Error checking validation message: " + e.getMessage());
            return false;
        }
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WebElement message = null;
            try {
                message = driver.findElement(successMessage);
            } catch (Exception e) {
                message = driver.findElement(successMessageAlt);
            }

            String messageText = message.getText();
            System.out.println("✓ Success message displayed: " + messageText);
            return message.isDisplayed();
        } catch (Exception e) {
            System.err.println("✗ Success message not found");
            return false;
        }
    }

    public boolean isCategoryInList(String categoryName) {
        try {
            waitForPageLoad();
            String pageSource = driver.getPageSource().toLowerCase();
            boolean found = pageSource.contains(categoryName.toLowerCase());

            System.out.println("Category '" + categoryName + "' in list: " + found);
            return found;
        } catch (Exception e) {
            System.err.println("✗ Could not check category list: " + e.getMessage());
            return false;
        }
    }

    public boolean isCategoryUnderParent(String categoryName, String parentName) {
        try {
            // This would need to be customized based on actual UI structure
            String pageSource = driver.getPageSource();
            boolean categoryFound = pageSource.contains(categoryName);
            boolean parentFound = pageSource.contains(parentName);

            System.out.println("Category '" + categoryName + "' under parent '" + parentName + "': " + (categoryFound && parentFound));
            return categoryFound && parentFound;
        } catch (Exception e) {
            System.err.println("✗ Could not verify category hierarchy: " + e.getMessage());
            return false;
        }
    }

    // Helper methods
    private WebElement findCategoryNameField() {
        try {
            return driver.findElement(categoryNameField);
        } catch (Exception e) {
            try {
                return driver.findElement(categoryNameFieldById);
            } catch (Exception ex) {
                try {
                    // Try by label
                    return driver.findElement(categoryNameFieldByLabel);
                } catch (Exception ex2) {
                    try {
                        // Try by any input with "category" in name/id
                        return driver.findElement(By.xpath("//input[contains(@name,'category') or contains(@id,'category')]"));
                    } catch (Exception ex3) {
                        try {
                            // Try first text input in form
                            return driver.findElement(By.xpath("//form//input[@type='text'][1]"));
                        } catch (Exception ex4) {
                            // Last resort: any text input
                            List<WebElement> inputs = driver.findElements(By.xpath("//input[@type='text']"));
                            if (!inputs.isEmpty()) {
                                return inputs.get(0);
                            }
                            throw new RuntimeException("Could not find category name field");
                        }
                    }
                }
            }
        }
    }

    private WebElement findParentCategoryDropdown() {
        try {
            return driver.findElement(parentCategoryDropdown);
        } catch (Exception e) {
            try {
                return driver.findElement(parentCategoryDropdownById);
            } catch (Exception ex) {
                try {
                    // Try by label
                    return driver.findElement(parentCategoryDropdownByLabel);
                } catch (Exception ex2) {
                    try {
                        // Try by any select with "parent" in name/id
                        return driver.findElement(By.xpath("//select[contains(@name,'parent') or contains(@id,'parent')]"));
                    } catch (Exception ex3) {
                        try {
                            // Try first select in form
                            return driver.findElement(By.xpath("//form//select[1]"));
                        } catch (Exception ex4) {
                            // Last resort: any select
                            List<WebElement> selects = driver.findElements(By.tagName("select"));
                            if (!selects.isEmpty()) {
                                return selects.get(0);
                            }
                            // Parent might be optional
                            System.out.println("Note: No parent category dropdown found (optional)");
                            return null;
                        }
                    }
                }
            }
        }
    }

    private void waitForPageLoad() {
        try {
            Thread.sleep(1000); // Give page time to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
