package pages.dashboard;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class DashboardPage {
    private WebDriver driver;

    // Multiple locator strategies for dashboard elements
    private By[] dashboardLocators = {
        By.xpath("//h1[contains(text(),'Dashboard')]"),
        By.xpath("//h2[contains(text(),'Dashboard')]"),
        By.xpath("//*[contains(@class,'dashboard')]"),
        By.id("dashboard"),
        By.className("dashboard")
    };

    // Menu/Navigation locators
    private By[] menuLocators = {
        By.xpath("//nav"),
        By.xpath("//ul[contains(@class,'nav')]"),
        By.xpath("//div[contains(@class,'menu')]"),
        By.xpath("//div[contains(@class,'sidebar')]"),
        By.className("navbar")
    };

    // Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Helper method to find element using multiple locators
    private WebElement findElement(By[] locators) {
        for (By locator : locators) {
            try {
                List<WebElement> elements = driver.findElements(locator);
                if (!elements.isEmpty() && elements.get(0).isDisplayed()) {
                    System.out.println("Found element using locator: " + locator);
                    return elements.get(0);
                }
            } catch (Exception e) {
                // Continue to next locator
            }
        }
        return null;
    }

    // Helper method to check if element exists
    private boolean isElementDisplayed(By[] locators) {
        WebElement element = findElement(locators);
        return element != null;
    }

    // Check if on dashboard page
    public boolean isOnDashboard() {
        String currentUrl = driver.getCurrentUrl();
        boolean urlContainsDashboard = currentUrl.contains("dashboard") ||
                                        currentUrl.contains("admin") ||
                                        currentUrl.contains("home");

        System.out.println("Current URL: " + currentUrl);
        System.out.println("Is on dashboard: " + urlContainsDashboard);

        return urlContainsDashboard;
    }

    // Get all visible menu items
    public List<String> getVisibleMenuItems() {
        List<String> menuItems = new ArrayList<>();

        try {
            // Try multiple strategies to find menu items
            List<WebElement> menuElements = driver.findElements(By.xpath("//nav//a | //ul//a | //*[contains(@class,'nav')]//a"));

            System.out.println("Found " + menuElements.size() + " menu items");

            for (WebElement element : menuElements) {
                if (element.isDisplayed()) {
                    String text = element.getText();
                    if (text != null && !text.trim().isEmpty()) {
                        menuItems.add(text.trim());
                        System.out.println("Menu item: " + text);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error getting menu items: " + e.getMessage());
        }

        return menuItems;
    }

    // Check if specific menu is visible
    public boolean isMenuVisible(String menuName) {
        try {
            // Search for menu by text
            List<WebElement> elements = driver.findElements(
                By.xpath("//*[contains(text(),'" + menuName + "')] | " +
                         "//*[contains(@href,'" + menuName.toLowerCase() + "')]")
            );

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Menu '" + menuName + "' is visible");
                    return true;
                }
            }
        } catch (Exception e) {
            System.out.println("Error checking menu visibility: " + e.getMessage());
        }

        System.out.println("Menu '" + menuName + "' is NOT visible");
        return false;
    }

    // Click on a menu item
    public void clickMenu(String menuName) {
        try {
            List<WebElement> elements = driver.findElements(
                By.xpath("//a[contains(text(),'" + menuName + "')] | " +
                         "//button[contains(text(),'" + menuName + "')] | " +
                         "//*[contains(@href,'" + menuName.toLowerCase() + "')]")
            );

            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    System.out.println("Clicking menu: " + menuName);
                    element.click();

                    // Wait for page to load
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
            }

            System.out.println("Could not find menu to click: " + menuName);
        } catch (Exception e) {
            System.out.println("Error clicking menu: " + e.getMessage());
        }
    }

    // Check if feature is accessible
    public boolean isFeatureAccessible(String featureName) {
        String currentUrl = driver.getCurrentUrl();
        String pageSource = driver.getPageSource().toLowerCase();

        boolean isAccessible = currentUrl.toLowerCase().contains(featureName.toLowerCase()) ||
                               pageSource.contains(featureName.toLowerCase());

        System.out.println("Feature '" + featureName + "' accessible: " + isAccessible);
        return isAccessible;
    }

    // Admin-specific feature checks based on access control matrix

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
        // Check if add/edit/delete buttons are present
        try {
            List<WebElement> actionButtons = driver.findElements(
                By.xpath("//button[contains(text(),'Add')] | " +
                         "//button[contains(text(),'Edit')] | " +
                         "//button[contains(text(),'Delete')] | " +
                         "//a[contains(text(),'Add')] | " +
                         "//a[contains(text(),'Create')]")
            );

            boolean hasActions = !actionButtons.isEmpty();
            System.out.println("Can add/edit/delete categories: " + hasActions);
            return hasActions;
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
                By.xpath("//button[contains(text(),'Add')] | " +
                         "//button[contains(text(),'Edit')] | " +
                         "//button[contains(text(),'Delete')]")
            );

            boolean hasActions = !actionButtons.isEmpty();
            System.out.println("Can add/edit/delete plants: " + hasActions);
            return hasActions;
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
                By.xpath("//button[contains(text(),'Create')] | " +
                         "//button[contains(text(),'Add')] | " +
                         "//a[contains(text(),'Create')] | " +
                         "//a[contains(text(),'New Sale')]")
            );

            boolean canCreate = !createButtons.isEmpty();
            System.out.println("Can create sale: " + canCreate);
            return canCreate;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean canDeleteSale() {
        try {
            List<WebElement> deleteButtons = driver.findElements(
                By.xpath("//button[contains(text(),'Delete')] | " +
                         "//a[contains(text(),'Delete')]")
            );

            boolean canDelete = !deleteButtons.isEmpty();
            System.out.println("Can delete sale: " + canDelete);
            return canDelete;
        } catch (Exception e) {
            return false;
        }
    }

    // Print all available menus for debugging
    public void printAllMenus() {
        System.out.println("========== DASHBOARD MENU INSPECTION ==========");
        List<String> menus = getVisibleMenuItems();
        System.out.println("Total menus found: " + menus.size());
        for (String menu : menus) {
            System.out.println("  - " + menu);
        }
        System.out.println("============================================");
    }

    // Navigate through all menus
    public List<String> navigateThroughAllMenus() {
        List<String> accessibleMenus = new ArrayList<>();
        List<String> menus = getVisibleMenuItems();

        for (String menu : menus) {
            try {
                clickMenu(menu);
                Thread.sleep(1000);

                if (isFeatureAccessible(menu)) {
                    accessibleMenus.add(menu);
                    System.out.println("Successfully accessed: " + menu);
                }
            } catch (Exception e) {
                System.out.println("Could not access: " + menu);
            }
        }

        return accessibleMenus;
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
