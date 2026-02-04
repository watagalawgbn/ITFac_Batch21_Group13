package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SalesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    //locator for the sales table
    private final By salesTable = By.cssSelector("table.table-bordered.table-striped.align-middle");
    private final By salesRows =
            By.xpath("//table//tbody/tr");

    private final By deleteButtonInRow =
            By.xpath(".//button[contains(@class,'btn-outline-danger')]");
    private final By noSalesMessage = By.xpath("//*[text() = 'No sales found']");
    private final By sellPlantButton = By.xpath("//a[contains(text(), 'Sell Plant')]");
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

    public boolean isSellPlantButtonVisible(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(sellPlantButton));
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public SellPlantPage clickSellPlantButton(){
        wait.until(ExpectedConditions.elementToBeClickable(sellPlantButton)).click();
        return new SellPlantPage(driver);
    }
    public int getSalesCount(){
        List<WebElement> rows = driver.findElements(salesRows);
        return rows.size();
    }

    public void clickFirstDeleteButton() {

        WebElement table =
                wait.until(ExpectedConditions.visibilityOfElementLocated(salesTable));

        List<WebElement> rows = table.findElements(By.xpath(".//tbody/tr"));

        if (rows.isEmpty()) {
            throw new RuntimeException("No sales available to delete");
        }

        WebElement deleteBtn = rows.get(0).findElement(
                By.xpath(".//button[contains(@class,'btn-outline-danger')]")
        );

        deleteBtn.click();
    }

    public void acceptDeleteAlert(){
        wait.until(ExpectedConditions.alertIsPresent());
        driver.switchTo().alert().accept();
    }

    public boolean isSaleDeleted(int beforeCount){
        if (beforeCount == 1) {
            return isNoSalesMessageDisplayed();
        }
        return getSalesCount() == beforeCount - 1;
    }
}
