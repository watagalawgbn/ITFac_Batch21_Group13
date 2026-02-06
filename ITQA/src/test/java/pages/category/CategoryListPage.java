package pages.category;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CategoryListPage {

    WebDriver driver;

    private By noCategoryMessage =
            By.xpath("//*[contains(text(),'No Category found')]");

    public CategoryListPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isNoCategoryMessageDisplayed() {
        return driver.findElement(noCategoryMessage).isDisplayed();
    }
}
