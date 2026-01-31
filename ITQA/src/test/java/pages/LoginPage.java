package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.ConfigReader;

public class LoginPage {

    private final WebDriver driver;
    private final By loginButton = By.xpath("//button[@type='submit']");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        String url = ConfigReader.get("base.url") + ConfigReader.get("login.path");
        driver.get(url);
    }

    public void loginAsAdmin(String username, String password) {
        driver.findElement(By.name("username")).sendKeys(username);
        driver.findElement(By.name("password")).sendKeys(password);
        driver.findElement(loginButton).click();
    }
}