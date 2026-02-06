package pages.plant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.DriverFactory;

public class LoginPage {
    private WebDriver driver;

    // Locators with fallbacks
    private By usernameField = By.xpath("//input[@id='username'] | //input[@name='username'] | //input[@type='text']");
    private By passwordField = By.xpath("//input[@id='password'] | //input[@name='password'] | //input[@type='password']");
    private By loginButton = By.xpath("//button[@id='login'] | //button[contains(text(), 'Login')] | //button[contains(text(), 'Sign In')] | //input[@type='submit']");
    private By errorMessage = By.className("error-message");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToLoginPage(String url) {
        driver.navigate().to(url);
        // Wait for page to load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void enterUsername(String username) {
        try {
            WebElement usernameInput = driver.findElement(usernameField);
            usernameInput.clear();
            usernameInput.sendKeys(username);
        } catch (Exception e) {
            System.out.println("Error entering username: " + e.getMessage());
        }
    }

    public void enterPassword(String password) {
        try {
            WebElement passwordInput = driver.findElement(passwordField);
            passwordInput.clear();
            passwordInput.sendKeys(password);
        } catch (Exception e) {
            System.out.println("Error entering password: " + e.getMessage());
        }
    }

    public void clickLoginButton() {
        try {
            WebElement button = driver.findElement(loginButton);
            button.click();
        } catch (Exception e) {
            System.out.println("Error clicking login button: " + e.getMessage());
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            // Check if any of the login elements are visible
            return !driver.findElements(usernameField).isEmpty() ||
                   !driver.findElements(passwordField).isEmpty() ||
                   driver.getTitle().toLowerCase().contains("login");
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        try {
            return driver.findElement(errorMessage).getText();
        } catch (Exception e) {
            return "";
        }
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }
}
