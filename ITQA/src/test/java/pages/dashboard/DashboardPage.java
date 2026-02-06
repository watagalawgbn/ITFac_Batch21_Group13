package pages.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardPage {

    WebDriver driver;
    WebDriverWait wait;

    // ========== CARDS ==========
    private By categoryCard = By.cssSelector("a[href='/ui/categories']");
    private By plantsCard = By.cssSelector("a[href='/ui/plants']");
    private By salesCard = By.cssSelector("a[href='/ui/sales']");

    // ========== SIDEBAR MENU ==========
    private By dashboardMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/dashboard')]");
    private By categoriesMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/categories')]");
    private By plantsMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/plants')]");
    private By salesMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/sales')]");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // ===== CARDS =====
    public boolean isCategoryCardVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(categoryCard)).isDisplayed();
    }

    public boolean isPlantsCardVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(plantsCard)).isDisplayed();
    }

    public boolean isSalesCardVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(salesCard)).isDisplayed();
    }

    public void clickCategoryCard() {
        wait.until(ExpectedConditions.elementToBeClickable(categoryCard)).click();
    }

    public void clickPlantsCard() {
        wait.until(ExpectedConditions.elementToBeClickable(plantsCard)).click();
    }

    public void clickSalesCard() {
        wait.until(ExpectedConditions.elementToBeClickable(salesCard)).click();
    }

    // ===== SIDEBAR MENU =====
    public boolean isDashboardMenuActive() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(dashboardMenu))
                .getAttribute("class").contains("active");
    }

    public boolean isCategoriesMenuActive() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(categoriesMenu))
                .getAttribute("class").contains("active");
    }

    public boolean isPlantsMenuActive() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(plantsMenu))
                .getAttribute("class").contains("active");
    }

    public boolean isSalesMenuActive() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(salesMenu))
                .getAttribute("class").contains("active");
    }

    public void clickCategoriesMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(categoriesMenu)).click();
    }

    public void clickPlantsMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(plantsMenu)).click();
    }

    public void clickSalesMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(salesMenu)).click();
    }

    public boolean isCategoriesMenuVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(categoriesMenu)).isDisplayed();
    }

    public boolean isPlantsMenuVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(plantsMenu)).isDisplayed();
    }

    public boolean isSalesMenuVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(salesMenu)).isDisplayed();
    }
}

