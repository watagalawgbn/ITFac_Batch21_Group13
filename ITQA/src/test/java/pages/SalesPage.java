package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
public class SalesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    //locator for the sales table
    private final By salesTable = By.cssSelector("table.table-bordered.table-striped.align-middle");
    private final By noSalesMessage = By.xpath("//*[text() = 'No sales found']");
    public SalesPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    public boolean isSalesListDisplayed(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(salesTable));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean isNoSalesMessageDisplayed(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(noSalesMessage));
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
