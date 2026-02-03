
package stepdefinitions;

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


import pages.CategoryListPage;
import utils.DriverFactory;
import org.testng.Assert;

public class CategoryUserSteps {

    WebDriver driver = DriverFactory.getDriver();
    CategoryListPage categoryListPage;


    /* ================= LOGIN ================= */

    @Given("regular user is logged into the system")
    public void regular_user_is_logged_into_the_system() {
        driver.get("http://localhost:8080/ui/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement usernameInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        usernameInput.sendKeys("testuser");

        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("test123");

        WebElement loginButton = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.cssSelector("button.btn.btn-primary.w-100.mb-3")));
        loginButton.click();

        // dashboard redirect confirms login success
        wait.until(ExpectedConditions.urlContains("/dashboard"));
    }

    @When("user navigates to the Category List page")
    public void user_navigates_to_category_list_page() {
        driver.get("http://localhost:8080/ui/categories");
        categoryListPage = new CategoryListPage(driver);
    }
    
    @Then("the Add Category button should NOT be visible")
    public void add_category_button_should_not_be_visible() {
        List<WebElement> addCategoryBtn = driver.findElements(
            By.xpath("//*[self::button or self::a][contains(@class,'btn') and contains(.,'Add Category')]")
        );

        Assert.assertTrue(addCategoryBtn.size() == 0, "Add Category button should NOT be visible for regular user");
    }

    @Then("the Edit Category button should NOT be visible")
    public void edit_category_button_should_not_be_visible() {
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> editButtons = table.findElements(
            By.xpath(".//a[@title='Edit' and contains(@class,'btn')]")
        );
        Assert.assertTrue(editButtons.size() == 0, "Edit Category button should NOT be visible for regular user");
    }

    @Then("the Delete Category button should NOT be visible")
    public void delete_category_button_should_not_be_visible() {
        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> deleteButtons = table.findElements(
            By.xpath(".//*[self::a or self::button][@title='Delete' and contains(@class,'btn')]")
        );
        Assert.assertTrue(deleteButtons.size() == 0, "Delete Category button should NOT be visible for regular user");
    }

    @And("user enters {string} in the category search field")
    public void user_enters_category_name(String categoryName) {
        WebElement searchBox = driver.findElement(By.name("name")); 
        // 🔁 change ID if your UI uses a different one
        searchBox.clear();
        searchBox.sendKeys(categoryName);
    }

    @And("user clicks the Search button")
    public void user_clicks_the_search_button() {
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        // 🔁 update locator if needed
        searchButton.click();
    }

    @Then("only categories matching {string} should be displayed")
    public void only_matching_categories_should_be_displayed(String keyword) {

        WebElement table = driver.findElement(By.tagName("table"));
        List<WebElement> categoryNames = table.findElements(
                By.xpath(".//tbody/tr/td[2]") 
                // assumes category name is in second column
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement parentDropdown = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.name("parentId"))
        );

        Select select = new Select(parentDropdown);
        select.selectByVisibleText(parentCategory); // select by visible option text
    }

    @And("user clicks the Search button for parent category")
    public void user_clicks_search_for_parent_category() {
        WebElement searchButton = driver.findElement(By.cssSelector("button[type='submit']"));
        searchButton.click();
    }

    @Then("only categories under parent {string} should be displayed")
    public void only_categories_under_parent_should_be_displayed(String parentCategory) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement table = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.tagName("table"))
        );

        List<WebElement> parentCells = table.findElements(By.xpath(".//tbody/tr/td[3]"));
        // td[3] = Parent column

        for (WebElement cell : parentCells) {
            String parentText = cell.getText().trim();
            // If parent category is "-", treat as empty
            if (parentCategory.equalsIgnoreCase("none")) {
                Assert.assertTrue(parentText.equals("-") || parentText.isEmpty(),
                    "Found category with a parent when expecting none: " + parentText);
            } else {
                Assert.assertEquals(parentText, parentCategory,
                    "Found category with a different parent: " + parentText);
            }
        }
    }





}
