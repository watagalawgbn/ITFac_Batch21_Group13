package pages.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class DashboardPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // ===== Cards locators =====
    private By categoryCard = By.cssSelector("a[href='/ui/categories']");
    private By plantsCard = By.cssSelector("a[href='/ui/plants']");
    private By salesCard = By.cssSelector("a[href='/ui/sales']");

    // ===== Sidebar/Menu locators =====
    private By dashboardMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/dashboard')]");
    private By categoriesMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/categories')]");
    private By plantsMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/plants')]");
    private By salesMenu = By.xpath("//div[contains(@class,'sidebar')]//a[contains(@href,'/sales')]");

    // ===== Dynamic dashboard locators =====
    private By[] dashboardLocators = {
        By.xpath("//h1[contains(text(),'Dashboard')]"),
        By.xpath("//h2[contains(text(),'Dashboard')]"),
        By.xpath("//*[contains(@class,'dashboard')]"),
        By.id("dashboard"),
        By.className("dashboard")
    };

    // ===== Constructor =====
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    // ===== Card methods =====
    public boolean isCategoryCardVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(categoryCard)).isDisplayed();
    }

    public void clickCategoryCard() {
        wait.until(ExpectedConditions.elementToBeClickable(categoryCard)).click();
    }

    public boolean isPlantsCardVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(plantsCard)).isDisplayed();
    }

    public void clickPlantsCard() {
        wait.until(ExpectedConditions.elementToBeClickable(plantsCard)).click();
    }

    public boolean isSalesCardVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(salesCard)).isDisplayed();
    }

    public void clickSalesCard() {
        wait.until(ExpectedConditions.elementToBeClickable(salesCard)).click();
    }

    // ===== Sidebar/Menu methods =====
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

    // ===== Dynamic helpers =====
    private WebElement findElement(By[] locators) {
        for (By locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    return elements.get(0);
                }
            } catch (Exception ignored) {}
        }
        return null;
    }

    private boolean isElementDisplayed(By[] locators) {
        return findElement(locators) != null;
    }

    public boolean isOnDashboard() {
        String currentUrl = driver.getCurrentUrl();
        return currentUrl.contains("dashboard") || currentUrl.contains("admin") || isElementDisplayed(dashboardLocators);
    }

    public List<String> getVisibleMenuItems() {
        List<String> menuItems = new ArrayList<>();
        List<WebElement> menuElements = driver.findElements(
            By.xpath("//nav//a | //ul//a | //*[contains(@class,'nav')]//a")
        );
        for (WebElement element : menuElements) {
            if (element.isDisplayed() && !element.getText().isEmpty()) {
                menuItems.add(element.getText().trim());
            }
        }
        return menuItems;
    }

    public boolean isMenuVisible(String menuName) {
        List<WebElement> elements = driver.findElements(
            By.xpath("//*[contains(text(),'" + menuName + "')] | //*[contains(@href,'" + menuName.toLowerCase() + "')]")
        );
        for (WebElement element : elements) {
            if (element.isDisplayed()) return true;
        }
        return false;
    }

    public void clickMenu(String menuName) {
        List<WebElement> elements = driver.findElements(
            By.xpath("//a[contains(text(),'" + menuName + "')] | //button[contains(text(),'" + menuName + "')] | //*[contains(@href,'" + menuName.toLowerCase() + "')]")
        );
        for (WebElement element : elements) {
            if (element.isDisplayed()) {
                element.click();
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
                return;
            }
        }
    }

    public boolean isFeatureAccessible(String featureName) {
        String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource().toLowerCase();
        return currentUrl.toLowerCase().contains(featureName.toLowerCase()) ||
               pageSource.contains(featureName.toLowerCase());
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    // ===== Feature-specific methods =====

    // Dashboard
    public boolean canViewDashboard() {
        return isOnDashboard() || isElementDisplayed(dashboardLocators);
    }

    // Categories
    public boolean canViewCategories() {
        return isMenuVisible("Categories") || isMenuVisible("Category");
    }

    public boolean canAccessCategories() {
        clickMenu("Categories");
        return isFeatureAccessible("categories") || isFeatureAccessible("category");
    }

    public boolean canAddEditDeleteCategory() {
        try {
            List<WebElement> actionButtons = driver.findElements(
                By.xpath("//button[contains(text(),'Add')] | //button[contains(text(),'Edit')] | //button[contains(text(),'Delete')] | //a[contains(text(),'Add')] | //a[contains(text(),'Create')]")
            );
            return !actionButtons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Plants
    public boolean canViewPlants() {
        return isMenuVisible("Plants") || isMenuVisible("Plant");
    }

    public boolean canAccessPlants() {
        clickMenu("Plants");
        return isFeatureAccessible("plants") || isFeatureAccessible("plant");
    }

    public boolean canAddEditDeletePlant() {
        try {
            List<WebElement> actionButtons = driver.findElements(
                By.xpath("//button[contains(text(),'Add')] | //button[contains(text(),'Edit')] | //button[contains(text(),'Delete')]")
            );
            return !actionButtons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // Sales
    public boolean canViewSales() {
        return isMenuVisible("Sales") || isMenuVisible("Sale");
    }

    public boolean canAccessSales() {
        clickMenu("Sales");
        return isFeatureAccessible("sales") || isFeatureAccessible("sale");
    }

    public boolean canCreateSale() {
        try {
            List<WebElement> createButtons = driver.findElements(
                By.xpath("//button[contains(text(),'Create')] | //button[contains(text(),'Add')] | //a[contains(text(),'Create')] | //a[contains(text(),'New Sale')]")
            );
            return !createButtons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canDeleteSale() {
        try {
            List<WebElement> deleteButtons = driver.findElements(
                By.xpath("//button[contains(text(),'Delete')] | //a[contains(text(),'Delete')]")
            );
            return !deleteButtons.isEmpty();
        } catch (Exception e) {
            return false;
        }
    }

    // ===== Debug & Navigation =====
    public void printAllMenus() {
        System.out.println("========== DASHBOARD MENU INSPECTION ==========");
        List<String> menus = getVisibleMenuItems();
        System.out.println("Total menus found: " + menus.size());
        for (String menu : menus) {
            System.out.println("  - " + menu);
        }
        System.out.println("============================================");
    }

    public List<String> navigateThroughAllMenus() {
        List<String> accessibleMenus = new ArrayList<>();
        List<String> menus = getVisibleMenuItems();
        for (String menu : menus) {
            try {
                clickMenu(menu);
                Thread.sleep(1000);
                if (isFeatureAccessible(menu)) {
                    accessibleMenus.add(menu);
                }
            } catch (Exception ignored) {}
        }
        return accessibleMenus;
    }
}
