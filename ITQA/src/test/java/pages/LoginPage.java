package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class LoginPage {
    private WebDriver driver;

    // Multiple locator strategies for username field
    private By[] usernameLocators = {
        By.id("username"),
        By.name("username"),
        By.xpath("//input[@type='text']"),
        By.xpath("//input[@placeholder='Username' or @placeholder='username']"),
        By.cssSelector("input[name='username']"),
        By.cssSelector("input[id*='user']"),
        By.xpath("//input[contains(@id,'user')]")
    };

    // Multiple locator strategies for password field
    private By[] passwordLocators = {
        By.name("password"),  // Primary - matches your app
        By.xpath("//input[@name='password']"),
        By.xpath("//input[@placeholder='Enter your password']"),  // Exact match
        By.xpath("//input[@type='password']"),
        By.cssSelector("input[name='password']"),
        By.xpath("//input[@type='password' and @class='form-control']"),
        By.id("password"),  // Fallback
        By.cssSelector("input[id*='pass']")
    };

    // Multiple locator strategies for login button
    private By[] loginButtonLocators = {
        By.xpath("//button[@type='submit' and text()='Login']"),  // Primary - exact match
        By.xpath("//button[@type='submit']"),
        By.cssSelector("button[type='submit']"),
        By.xpath("//button[contains(text(),'Login')]"),
        By.xpath("//button[contains(@class,'btn-primary')]"),
        By.xpath("//button[contains(@class,'btn') and contains(@class,'btn-primary')]"),
        By.id("loginButton"),  // Fallback
        By.name("loginButton"),
        By.xpath("//input[@type='submit']"),
        By.xpath("//input[@value='Login']")
    };

    // Multiple locator strategies for validation error messages
    private By[] validationMessageLocators = {
        By.id("error-message"),
        By.id("errorMessage"),
        By.className("error-message"),
        By.className("alert-danger"),
        By.className("error"),
        By.xpath("//div[contains(@class,'alert')]"),
        By.xpath("//div[contains(@class,'error')]"),
        By.xpath("//span[contains(@class,'error')]"),
        By.xpath("//p[contains(@class,'error')]"),
        By.xpath("//*[contains(text(),'Username and Password are required')]"),
        By.xpath("//*[contains(text(),'required')]"),
        By.cssSelector(".alert"),
        By.cssSelector(".error-message"),
        By.cssSelector("[role='alert']")
    };

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Helper method to find element using multiple locators
    private WebElement findElement(By[] locators) {
        for (By locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    System.out.println("Found element using locator: " + locator);
                    return elements.get(0);
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }
        return null;
    }

    // Helper method to check if element exists
    private boolean isElementDisplayed(By[] locators) {
        WebElement element = findElement(locators);
        return element != null;
    }

    // Methods to interact with elements
    public void navigateToLoginPage(String baseUrl) {
        driver.get(baseUrl + "/ui/login");
        try {
            Thread.sleep(2000); // Wait for page to load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean isUsernameFieldDisplayed() {
        boolean found = isElementDisplayed(usernameLocators);
        if (!found) {
            System.out.println("Username field not found. Trying to find all input elements...");
            printAllInputElements();
        }
        return found;
    }

    public boolean isPasswordFieldDisplayed() {
        boolean found = isElementDisplayed(passwordLocators);
        if (!found) {
            System.out.println("Password field not found.");
        }
        return found;
    }

    public boolean isLoginButtonDisplayed() {
        boolean found = isElementDisplayed(loginButtonLocators);
        if (!found) {
            System.out.println("Login button not found. Trying to find all buttons...");
            printAllButtons();
        }
        return found;
    }

    public boolean isUsernameFieldEnabled() {
        WebElement element = findElement(usernameLocators);
        return element != null && element.isEnabled();
    }

    public boolean isPasswordFieldEnabled() {
        WebElement element = findElement(passwordLocators);
        return element != null && element.isEnabled();
    }

    public boolean isLoginButtonEnabled() {
        WebElement element = findElement(loginButtonLocators);
        return element != null && element.isEnabled();
    }

    // Debug methods to print all elements
    private void printAllInputElements() {
        try {
            List<WebElement> inputs = driver.findElements(By.tagName("input"));
            System.out.println("Found " + inputs.size() + " input elements:");
            for (int i = 0; i < inputs.size(); i++) {
                WebElement input = inputs.get(i);
                System.out.println("Input " + (i+1) + ":");
                System.out.println("  Type: " + input.getAttribute("type"));
                System.out.println("  ID: " + input.getAttribute("id"));
                System.out.println("  Name: " + input.getAttribute("name"));
                System.out.println("  Placeholder: " + input.getAttribute("placeholder"));
                System.out.println("  Class: " + input.getAttribute("class"));
            }
        } catch (Exception e) {
            System.out.println("Error printing input elements: " + e.getMessage());
        }
    }

    private void printAllButtons() {
        try {
            List<WebElement> buttons = driver.findElements(By.tagName("button"));
            System.out.println("Found " + buttons.size() + " button elements:");
            for (int i = 0; i < buttons.size(); i++) {
                WebElement button = buttons.get(i);
                System.out.println("Button " + (i+1) + ":");
                System.out.println("  Type: " + button.getAttribute("type"));
                System.out.println("  ID: " + button.getAttribute("id"));
                System.out.println("  Name: " + button.getAttribute("name"));
                System.out.println("  Text: " + button.getText());
                System.out.println("  Class: " + button.getAttribute("class"));
            }

            List<WebElement> submits = driver.findElements(By.xpath("//input[@type='submit']"));
            System.out.println("Found " + submits.size() + " submit input elements.");
        } catch (Exception e) {
            System.out.println("Error printing button elements: " + e.getMessage());
        }
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean areAllUIElementsDisplayed() {
        return isUsernameFieldDisplayed() &&
               isPasswordFieldDisplayed() &&
               isLoginButtonDisplayed();
    }

    public void enterUsername(String username) {
        WebElement element = findElement(usernameLocators);
        if (element != null) {
            element.clear();
            element.sendKeys(username);
        } else {
            throw new RuntimeException("Unable to find username field");
        }
    }

    public void enterPassword(String password) {
        WebElement element = findElement(passwordLocators);
        if (element != null) {
            element.clear();
            element.sendKeys(password);
        } else {
            throw new RuntimeException("Unable to find password field");
        }
    }

    public void clickLoginButton() {
        WebElement element = findElement(loginButtonLocators);
        if (element != null) {
            element.click();
            // Wait a bit for validation message to appear
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            throw new RuntimeException("Unable to find login button");
        }
    }

    // Validation message methods for TC-UI-LOGIN-02
    public boolean isValidationMessageDisplayed() {
        boolean found = isElementDisplayed(validationMessageLocators);
        if (!found) {
            System.out.println("Validation message not found. Searching for all alert/error elements...");
            printAllValidationElements();
        }
        return found;
    }

    public String getValidationMessage() {
        WebElement element = findElement(validationMessageLocators);
        if (element != null) {
            String message = element.getText();
            System.out.println("Validation message found: " + message);
            return message;
        }
        System.out.println("No validation message element found");
        return "";
    }

    public boolean isValidationMessageContaining(String expectedText) {
        String actualMessage = getValidationMessage();
        boolean contains = actualMessage.toLowerCase().contains(expectedText.toLowerCase());
        System.out.println("Checking if validation message contains '" + expectedText + "': " + contains);
        return contains;
    }

    public void clearUsernameField() {
        WebElement element = findElement(usernameLocators);
        if (element != null) {
            element.clear();
        }
    }

    public void clearPasswordField() {
        WebElement element = findElement(passwordLocators);
        if (element != null) {
            element.clear();
        }
    }

    public void clearAllFields() {
        clearUsernameField();
        clearPasswordField();
    }

    public boolean isOnLoginPage() {
        String currentUrl = getCurrentUrl();
        return currentUrl != null && currentUrl.contains("/ui/login");
    }

    private void printAllValidationElements() {
        try {
            // Check for common error/alert elements
            List<WebElement> alerts = driver.findElements(By.xpath("//*[contains(@class,'alert') or contains(@class,'error')]"));
            System.out.println("Found " + alerts.size() + " alert/error elements:");
            for (int i = 0; i < alerts.size(); i++) {
                WebElement alert = alerts.get(i);
                if (alert.isDisplayed()) {
                    System.out.println("Alert/Error " + (i + 1) + ":");
                    System.out.println("  Tag: " + alert.getTagName());
                    System.out.println("  ID: " + alert.getAttribute("id"));
                    System.out.println("  Class: " + alert.getAttribute("class"));
                    System.out.println("  Text: " + alert.getText());
                    System.out.println("  Visible: " + alert.isDisplayed());
                }
            }
        } catch (Exception e) {
            System.out.println("Error printing validation elements: " + e.getMessage());
        }
    }

    // Methods for TC-UI-LOGIN-03 - Invalid login credentials
    public boolean isUserLoggedIn() {
        // Check if user is redirected away from login page
        String currentUrl = getCurrentUrl();
        boolean isStillOnLoginPage = currentUrl.contains("/ui/login");

        // User is logged in if they are NOT on the login page
        boolean isLoggedIn = !isStillOnLoginPage;

        System.out.println("Current URL: " + currentUrl);
        System.out.println("Is user logged in: " + isLoggedIn);

        return isLoggedIn;
    }

    public boolean isErrorMessageDisplayed() {
        // Wait a bit for error message to appear
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if error message is displayed using validation message locators
        return isValidationMessageDisplayed();
    }

    public String getErrorMessage() {
        return getValidationMessage();
    }

    public void performLogin(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    // Methods for TC-UI-LOGIN-04 - Successful admin login
    public boolean isRedirectedToDashboard() {
        // Wait for redirection
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String currentUrl = getCurrentUrl();
        System.out.println("Current URL after login: " + currentUrl);

        // Check if redirected away from login page to dashboard
        boolean isOnDashboard = !currentUrl.contains("/ui/login") &&
                                (currentUrl.contains("dashboard") ||
                                 currentUrl.contains("admin") ||
                                 currentUrl.contains("home"));

        System.out.println("Is redirected to dashboard: " + isOnDashboard);
        return isOnDashboard;
    }

    public boolean isLoginSuccessful() {
        // User successfully logged in if:
        // 1. Not on login page anymore
        // 2. No error messages
        // 3. Redirected to dashboard

        String currentUrl = getCurrentUrl();
        boolean notOnLoginPage = !currentUrl.contains("/ui/login") ||
                                  currentUrl.contains("dashboard") ||
                                  currentUrl.contains("home");

        System.out.println("Login successful: " + notOnLoginPage);
        return notOnLoginPage;
    }

    public String getCurrentPageUrl() {
        return getCurrentUrl();
    }
}
