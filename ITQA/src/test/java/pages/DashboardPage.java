package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pages.sales.SalesPage;
public class DashboardPage {
    private final WebDriver driver;
    private final By salesMenu = By.cssSelector("a[href='/ui/sales']");
    public DashboardPage(WebDriver driver){
        this.driver = driver;
    }
    public SalesPage goToSalesPage(){
        driver.findElement(salesMenu).click();
        return new SalesPage(driver);
    }

}
