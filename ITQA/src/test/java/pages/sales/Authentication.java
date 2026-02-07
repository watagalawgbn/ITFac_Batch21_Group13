package pages.sales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.ConfigReader;

public class Authentication {
    private final WebDriver driver;
    private final By usernameField = By.name("username");
    private final By passwordField = By.name("password");
    private final By loginButton = By.xpath("//button[@type='submit']");

    public Authentication(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        String url = ConfigReader.get("base.url") + ConfigReader.get("login.path");
        driver.get(url);
    }

    public DashboardPage login(String username, String password) {
        driver.findElement(usernameField).sendKeys(username);
        driver.findElement(passwordField).sendKeys(password);
        driver.findElement(loginButton).click();

        return new DashboardPage(driver);
    }
}
