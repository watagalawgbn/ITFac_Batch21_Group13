package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PlantsPage {
    private WebDriver driver;

    // Locators
    private By addPlantButton = By.xpath("//button[contains(text(), 'Add a Plant')] | //a[contains(text(), 'Add a Plant')] | //button[contains(text(), 'Add Plant')]");
    private By pageHeading = By.xpath("//h1[contains(text(), 'Plants')] | //h2[contains(text(), 'Plants')]");
    private By plantsPageTitle = By.tagName("h1");
    private By actionsColumn = By.xpath("//th[contains(text(), 'Actions')] | //th[contains(., 'Action')]");
    private By editButton = By.xpath("//a[contains(@href, 'edit')] | //button[contains(@id, 'edit')] | //i[@class[contains(., 'edit')]] | //*[contains(text(), 'Edit')]");
    private By deleteButton = By.xpath("//a[contains(@href, 'delete')] | //button[contains(@id, 'delete')] | //i[@class[contains(., 'delete')]] | //*[contains(text(), 'Delete')]");
    private By tableRows = By.xpath("//table//tbody//tr");
    private By quantityColumnCells = By.xpath("//table//tbody//tr//td[contains(@class,'quantity') or position()=4]");
    private By lowBadge = By.xpath(".//*[contains(@class,'badge') and contains(text(),'Low')]");

    public PlantsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void navigateToPlantsPage(String url) {
        driver.navigate().to(url);
    }

    public boolean isAddPlantButtonVisible() {
        try {
            WebElement button = driver.findElement(addPlantButton);
            return button.isDisplayed() && button.isEnabled();
        } catch (Exception e) {
            System.out.println("Add Plant button visibility check failed: " + e.getMessage());
            // Try alternative button locators
            try {
                return driver.findElement(By.xpath("//*[contains(text(), 'Add')]")).isDisplayed();
            } catch (Exception e2) {
                return false;
            }
        }
    }

    public boolean isAddPlantButtonPresent() {
        try {
            return driver.findElements(addPlantButton).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickAddPlantButton() {
        WebElement button = driver.findElement(addPlantButton);
        button.click();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isPlantsPageDisplayed() {
        try {
            // Check if we're on plants page by URL first
            if (driver.getCurrentUrl().contains("plants")) {
                return true;
            }
            // Then check for the heading
            return driver.findElement(pageHeading).isDisplayed();
        } catch (Exception e) {
            System.out.println("Plants page check failed: " + e.getMessage());
            return false;
        }
    }

    public boolean isAddPlantFormDisplayed() {
        try {
            // Check for form elements that would be present on the Add Plant form
            return driver.findElements(By.xpath("//form | //input[@name='plantName'] | //input[@placeholder]")).size() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isActionsColumnVisible() {
        try {
            WebElement column = driver.findElement(actionsColumn);
            return column.isDisplayed();
        } catch (Exception e) {
            System.out.println("Actions column not found: " + e.getMessage());
            return false;
        }
    }

    public boolean areEditButtonsVisibleForAllPlants() {
        try {
            // First check if there are table rows with plants
            java.util.List<WebElement> rows = driver.findElements(tableRows);
            if (rows.isEmpty()) {
                System.out.println("No plant records found in the table");
                return false;
            }

            // Try multiple approaches to find edit buttons
            // Approach 1: Look for any element in actions cell that might be an edit button
            java.util.List<WebElement> editButtons = driver.findElements(
                By.xpath("//table//tbody//tr//td[last()]//a | //table//tbody//tr//td[last()]//button | //table//tbody//tr//td[last()]//i | //table//tbody//tr//*[contains(@class, 'edit')] | //table//tbody//tr//*[contains(@class, 'fa-pencil')] | //table//tbody//tr//*[contains(@href, 'edit')]")
            );

            if (editButtons.isEmpty()) {
                // Approach 2: Look for any clickable element in the last column of each row
                editButtons = driver.findElements(
                    By.xpath("//table//tbody//tr//td//a[@href] | //table//tbody//tr//td//button")
                );

                // Filter to find edit-related buttons
                int editCount = 0;
                for (WebElement button : editButtons) {
                    String text = button.getText().toLowerCase();
                    String href = button.getAttribute("href");
                    String id = button.getAttribute("id");
                    String classes = button.getAttribute("class");

                    if ((href != null && href.contains("edit")) ||
                        (id != null && id.contains("edit")) ||
                        (classes != null && classes.contains("edit")) ||
                        text.contains("edit")) {
                        editCount++;
                    }
                }

                if (editCount > 0) {
                    System.out.println("Found " + editCount + " edit buttons for " + rows.size() + " plants");
                    return true;
                }

                System.out.println("No edit buttons found in the plants table");
                return false;
            }

            System.out.println("Found " + editButtons.size() + " edit buttons for " + rows.size() + " plants");
            return true;
        } catch (Exception e) {
            System.out.println("Error checking edit buttons: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean areDeleteButtonsVisibleForAllPlants() {
        try {
            // First check if there are table rows with plants
            java.util.List<WebElement> rows = driver.findElements(tableRows);
            if (rows.isEmpty()) {
                System.out.println("No plant records found in the table");
                return false;
            }

            // Try multiple approaches to find delete buttons
            // Approach 1: Look for any element in actions cell that might be a delete button
            java.util.List<WebElement> deleteButtons = driver.findElements(
                By.xpath("//table//tbody//tr//td[last()]//a | //table//tbody//tr//td[last()]//button | //table//tbody//tr//td[last()]//i | //table//tbody//tr//*[contains(@class, 'delete')] | //table//tbody//tr//*[contains(@class, 'fa-trash')] | //table//tbody//tr//*[contains(@href, 'delete')]")
            );

            if (deleteButtons.isEmpty()) {
                // Approach 2: Look for any clickable element in the last column of each row
                deleteButtons = driver.findElements(
                    By.xpath("//table//tbody//tr//td//a[@href] | //table//tbody//tr//td//button")
                );

                // Filter to find delete-related buttons
                int deleteCount = 0;
                for (WebElement button : deleteButtons) {
                    String text = button.getText().toLowerCase();
                    String href = button.getAttribute("href");
                    String id = button.getAttribute("id");
                    String classes = button.getAttribute("class");

                    if ((href != null && href.contains("delete")) ||
                        (id != null && id.contains("delete")) ||
                        (classes != null && classes.contains("delete")) ||
                        text.contains("delete")) {
                        deleteCount++;
                    }
                }

                if (deleteCount > 0) {
                    System.out.println("Found " + deleteCount + " delete buttons for " + rows.size() + " plants");
                    return true;
                }

                System.out.println("No delete buttons found in the plants table");
                return false;
            }

            System.out.println("Found " + deleteButtons.size() + " delete buttons for " + rows.size() + " plants");
            return true;
        } catch (Exception e) {
            System.out.println("Error checking delete buttons: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPlantsTableDisplayed() {
        try {
            // Check if the plants table is visible on the page
            java.util.List<WebElement> tables = driver.findElements(By.xpath("//table | //div[contains(@class, 'table')] | //div[contains(@class, 'grid')]"));
            if (tables.isEmpty()) {
                System.out.println("No table found on the plants page");
                return false;
            }
            System.out.println("Plants table is displayed");
            return true;
        } catch (Exception e) {
            System.out.println("Error checking plants table display: " + e.getMessage());
            return false;
        }
    }

    public boolean isPlantListNotEmpty() {
        try {
            // Check if there are any plant records in the table
            java.util.List<WebElement> rows = driver.findElements(tableRows);
            if (rows.isEmpty()) {
                System.out.println("No plants found in the list");
                return false;
            }
            System.out.println("Found " + rows.size() + " plants in the list");
            return true;
        } catch (Exception e) {
            System.out.println("Error checking plant list: " + e.getMessage());
            return false;
        }
    }

    public boolean hasAtLeastOneLowStockPlant(int threshold) {
        java.util.List<WebElement> rows = driver.findElements(tableRows);

        for (WebElement row : rows) {
            WebElement quantityCell = row.findElement(
                    By.xpath(".//td[contains(@class,'quantity') or position()=4]")
            );

            String quantityText = quantityCell.getText().trim();
            int quantity = Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));

            if (quantity < threshold) {
                return true;
            }
        }
        return false;
    }

    public boolean isLowBadgeDisplayedForLowStockPlants(int threshold) {
        java.util.List<WebElement> rows = driver.findElements(tableRows);

        for (WebElement row : rows) {
            WebElement quantityCell = row.findElement(
                    By.xpath(".//td[contains(@class,'quantity') or position()=4]")
            );

            String quantityText = quantityCell.getText().trim();
            int quantity = Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));

            if (quantity < threshold) {
                if (row.findElements(lowBadge).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isLowBadgeHiddenForNonLowStockPlants(int threshold) {
        java.util.List<WebElement> rows = driver.findElements(tableRows);

        for (WebElement row : rows) {
            WebElement quantityCell = row.findElement(
                    By.xpath(".//td[contains(@class,'quantity') or position()=4]")
            );

            String quantityText = quantityCell.getText().trim();
            int quantity = Integer.parseInt(quantityText.replaceAll("[^0-9]", ""));

            if (quantity >= threshold) {
                if (!row.findElements(lowBadge).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }
}

