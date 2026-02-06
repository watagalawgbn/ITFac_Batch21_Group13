package stepdefinitions.category.ui;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

import pages.category.CategoryListPage;
import utils.DriverFactory;
import org.testng.Assert;

import utils.ConfigReader;


public class CategoryUISteps {

    WebDriver driver = DriverFactory.getDriver();
    CategoryListPage categoryListPage;

    /* ================= LOGIN ================= */

    @Given("regular user is logged into the system")
    public void regular_user_is_logged_into_the_system() {

        driver.get(ConfigReader.get("base.url") + ConfigReader.get("login.path"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameInput.sendKeys(ConfigReader.get("user.username"));

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(ConfigReader.get("user.password"));

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.btn.btn-primary.w-100.mb-3")));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    @Given("admin user is logged into the system")
    public void admin_user_is_logged_into_the_system() {

        driver.get(ConfigReader.get("base.url") + ConfigReader.get("login.path"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameInput.sendKeys(ConfigReader.get("admin.username"));

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys(ConfigReader.get("admin.password"));

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.btn.btn-primary.w-100.mb-3")));
        loginButton.click();

        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    /* ================= NAVIGATION ================= */

    @When("user navigates to the Category List page")
    @When("admin navigates to the Category List page")
    public void navigate_to_category_list_page() {
        driver.get(ConfigReader.get("base.url") + ConfigReader.get("categories.path"));
        categoryListPage = new CategoryListPage(driver);
    }

    @And("no categories exist in the system")
    public void no_categories_exist_in_the_system() {
        // Precondition
    }

    @And("at least one category exists in the system")
    public void at_least_one_category_exists_in_the_system() {
        // Precondition 
    }

    /* ================= VISIBILITY CHECKS ================= */

    @Then("the Add Category button should be visible")
    public void add_category_button_should_be_visible() {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        WebElement addCategoryBtn = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//*[self::button or self::a][contains(@class,'btn') and contains(.,'Add Category')]")
            )
        );

        Assert.assertTrue(addCategoryBtn.isDisplayed(),
                "Add Category button is NOT visible");
    }

    @Then("the Add Category button should NOT be visible")
    public void add_category_button_should_not_be_visible() {

        List<WebElement> addCategoryBtn = driver.findElements(
            By.xpath("//*[self::button or self::a][contains(@class,'btn') and contains(.,'Add Category')]")
        );

        Assert.assertEquals(addCategoryBtn.size(), 0,
                "Add Category button should NOT be visible");
    }

    @Then("the Edit Category button should be visible for each category")
    public void edit_category_button_should_be_visible() {

        WebElement table = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        List<WebElement> editButtons = table.findElements(
            By.xpath(".//a[@title='Edit' and contains(@class,'btn')]")
        );

        Assert.assertTrue(editButtons.size() > 0,
                "Edit Category button is NOT visible");
    }

    @Then("the Edit Category button should NOT be visible")
    public void edit_category_button_should_not_be_visible() {

        WebElement table = driver.findElement(By.tagName("table"));

        List<WebElement> editButtons = table.findElements(
            By.xpath(".//a[@title='Edit' and contains(@class,'btn')]")
        );

        Assert.assertEquals(editButtons.size(), 0,
                "Edit Category button should NOT be visible");
    }

    @Then("the Delete Category button should be visible for each category")
    public void delete_category_button_should_be_visible() {

        WebElement table = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        List<WebElement> deleteButtons = table.findElements(
            By.xpath(".//*[self::a or self::button][@title='Delete' and contains(@class,'btn')]")
        );

        Assert.assertTrue(deleteButtons.size() > 0,
                "Delete Category button is NOT visible");
    }

    @Then("the Delete Category button should NOT be visible")
    public void delete_category_button_should_not_be_visible() {

        WebElement table = driver.findElement(By.tagName("table"));

        List<WebElement> deleteButtons = table.findElements(
            By.xpath(".//*[self::a or self::button][@title='Delete' and contains(@class,'btn')]")
        );

        Assert.assertEquals(deleteButtons.size(), 0,
                "Delete Category button should NOT be visible");
    }

    /* ================= SEARCH ================= */

    @And("user enters {string} in the category search field")
    public void user_enters_category_name(String categoryName) {

        WebElement searchBox = driver.findElement(By.name("name"));
        searchBox.clear();
        searchBox.sendKeys(categoryName);
    }

    @And("user clicks the Search button")
    @And("user clicks the Search button for parent category")
    public void user_clicks_search_button() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("only categories matching {string} should be displayed")
    public void only_matching_categories_should_be_displayed(String keyword) {

        WebElement table = driver.findElement(By.tagName("table"));

        List<WebElement> categoryNames = table.findElements(
                By.xpath(".//tbody/tr/td[2]")
        );

        for (WebElement category : categoryNames) {
            Assert.assertTrue(
                category.getText().toLowerCase().contains(keyword.toLowerCase()),
                "Found non-matching category: " + category.getText()
            );
        }
    }

    @And("user selects {string} from the parent category dropdown")
    public void user_selects_parent_category(String parentCategory) {

        WebElement dropdown = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.name("parentId")));

        new Select(dropdown).selectByVisibleText(parentCategory);
    }

    @Then("only categories under parent {string} should be displayed")
    public void only_categories_under_parent_should_be_displayed(String parentCategory) {

        WebElement table = driver.findElement(By.tagName("table"));

        List<WebElement> parentCells = table.findElements(
                By.xpath(".//tbody/tr/td[3]")
        );

        for (WebElement cell : parentCells) {
            if (parentCategory.equalsIgnoreCase("none")) {
                Assert.assertTrue(cell.getText().equals("-") || cell.getText().isEmpty());
            } else {
                Assert.assertEquals(cell.getText(), parentCategory);
            }
        }
    }

    /* ================= EDIT NAVIGATION ================= */

    @Then("admin should be navigated to the edit page when clicking an Edit icon")
    public void admin_navigates_to_edit_category_page() {

        WebElement table = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.tagName("table")));

        table.findElement(By.xpath(".//a[@title='Edit']")).click();

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.urlContains("/ui/categories/edit/"));

        Assert.assertTrue(driver.getCurrentUrl().contains("/ui/categories/edit/"));
    }

    /* ================= EMPTY STATE ================= */

    @Then("the system should display {string} message")
    public void system_should_display_message(String expectedMessage) {

        WebElement message = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[contains(text(),'No category found')]")));

        Assert.assertEquals(
                message.getText().toLowerCase(),
                expectedMessage.toLowerCase()
        );
    }
}
