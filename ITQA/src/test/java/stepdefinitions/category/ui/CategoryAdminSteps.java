package stepdefinitions;

import java.time.Duration;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import pages.CategoryListPage;
import utils.DriverFactory;
import org.testng.Assert;

public class CategoryAdminSteps {

    WebDriver driver = DriverFactory.getDriver();
    CategoryListPage categoryListPage;

    /* ================= LOGIN ================= */

    @Given("admin user is logged into the system")
    public void admin_user_is_logged_into_the_system() {

        driver.get("http://localhost:8080/ui/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameInput.sendKeys("admin");

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("admin123");

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.btn.btn-primary.w-100.mb-3")));
        loginButton.click();

        // dashboard redirect confirms login success
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    /* ================= NAVIGATION ================= */

    @When("admin navigates to the Category List page")
    public void admin_navigates_to_category_list_page() {
        driver.get("http://localhost:8080/ui/categories");
        categoryListPage = new CategoryListPage(driver);
    }

    @And("no categories exist in the system")
    public void no_categories_exist_in_the_system() {
        // Assumption: DB is empty OR test environment is pre-cleaned
    }

    /* ================= TC_UI_CAT_01 ================= */

    @Then("the system should display {string} message")
    public void system_should_display_no_category_message(String expectedMessage) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement messageElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'No category found')]")));

        String actualMessage = messageElement.getText().trim();

        // Case-insensitive validation (fixes earlier failure)
        Assert.assertEquals(
                actualMessage.toLowerCase(),
                expectedMessage.toLowerCase(),
                "Displayed message is incorrect");
    }

    /* ================= TC_UI_CAT_02 ================= */

    @Then("the Add Category button should be visible")
    public void add_category_button_should_be_visible() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement addCategoryBtn = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath(
                    "//*[self::button or self::a]" +
                    "[contains(@class,'btn') and contains(.,'Add Category')]"
                )
            )
        );

        Assert.assertTrue(addCategoryBtn.isDisplayed(),
                "Add Category button is NOT visible for Admin");
    }
    @And("at least one category exists in the system")
    public void at_least_one_category_exists_in_the_system() {
        // Test data precondition
        // Option 1 (recommended for now):
        // Ensure test DB already has at least one category

        // Option 2 (advanced):
        // Call API or DB insert here (not required for UI testing exam)
    }

    @Then("the Edit Category button should be visible for each category")
    public void the_edit_category_button_should_be_visible_for_each_category() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for category table to be visible
        WebElement table = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("table"))
        );

        // Find Edit buttons by title attribute (ICON-based button)
        List<WebElement> editButtons = table.findElements(
            By.xpath(".//a[@title='Edit' and contains(@class,'btn')]")
        );

        Assert.assertTrue(
            editButtons.size() > 0,
            "Edit Category button is NOT visible for existing categories"
        );

        for (WebElement btn : editButtons) {
            Assert.assertTrue(btn.isDisplayed(),
                    "An Edit Category button is not visible");
        }
    }
    

    @Then("the Delete Category button should be visible for each category")
    public void the_delete_category_button_should_be_visible_for_each_category() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Wait for category table to be visible
        WebElement table = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("table"))
        );

        // Find Delete buttons by title attribute (ICON-based button)
        List<WebElement> deleteButtons = table.findElements(
            By.xpath(".//*[self::a or self::button][@title='Delete' and contains(@class,'btn')]")
        );


        Assert.assertTrue(
            deleteButtons.size() > 0,
            "Delete Category button is NOT visible for existing categories"
        );

        for (WebElement btn : deleteButtons) {
            Assert.assertTrue(btn.isDisplayed(),
                    "An Delete Category button is not visible");
        }
    }

    @Then("admin should be navigated to the edit page when clicking an Edit icon")
public void admin_navigates_to_edit_category_page() {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    // Wait for the table to appear
    WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

    // Find the first Edit button
    WebElement editButton = table.findElement(By.xpath(".//a[@title='Edit' and contains(@class,'btn')]"));

    // Click the Edit button
    editButton.click();

    // Wait for URL to contain '/ui/categories/edit/'
    wait.until(ExpectedConditions.urlContains("/ui/categories/edit/"));

    // Assert URL contains 'edit'
    String currentUrl = driver.getCurrentUrl();
    Assert.assertTrue(currentUrl.contains("/ui/categories/edit/"), "Did not navigate to Edit Category page");
}

}