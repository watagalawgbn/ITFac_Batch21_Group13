package pages.plant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.ElementClickInterceptedException;

import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.TimeoutException;

import java.util.HashMap;
import java.util.Map;



public class PlantsPage {
    private WebDriver driver;
    

    // Locators
    private By addPlantButton = By.xpath("//button[contains(text(), 'Add a Plant')] | //a[contains(text(), 'Add a Plant')] | //button[contains(text(), 'Add Plant')]");
    private By pageHeading = By.xpath("//h1[contains(text(), 'Plants')] | //h2[contains(text(), 'Plants')]");
    // private By plantsPageTitle = By.tagName("h1");
    private By actionsColumn = By.xpath("//th[contains(text(), 'Actions')] | //th[contains(., 'Action')]");
    private By editButton = By.xpath("//a[contains(@href, 'edit')] | //button[contains(@id, 'edit')] | //i[@class[contains(., 'edit')]] | //*[contains(text(), 'Edit')]");
    private By deleteButton = By.xpath("//a[contains(@href, 'delete')] | //button[contains(@id, 'delete')] | //i[@class[contains(., 'delete')]] | //*[contains(text(), 'Delete')]");
    private By tableRows = By.xpath("//table//tbody//tr");
    private By quantityColumnCells = By.xpath("//table//tbody//tr//td[contains(@class,'quantity') or position()=4]");
    private By lowBadge = By.xpath(".//*[contains(@class,'badge') and contains(text(),'Low')]");
    private By categoryDropdown = By.xpath("//select[contains(@id,'category') or contains(@name,'category')]");
    private By searchButton = By.xpath("//button[contains(text(),'Search') or contains(text(),'Filter')]");
    private By categoryColumnCells = By.xpath("//table//tbody//tr//td[position()=3 or contains(@class,'category')]");

    // Locators
    private By plantsTable = By.tagName("table");
    private By plantsTableRows = By.cssSelector("table tbody tr");
    private By tableHeaders = By.cssSelector("table thead th");

    // // Constructor
    // public PlantsPage(WebDriver driver) {
    //     this.driver = driver;
    //     this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    // }

    // Navigation
    // public void navigateToPlantsPage(String baseUrl) {
    //     String fullUrl = baseUrl + "/ui/plants";
    //     driver.get(fullUrl);
    //     System.out.println("Navigated to Plants page: " + fullUrl);
    //     try {
    //         Thread.sleep(1000); // Wait for page load
    //     } catch (InterruptedException e) {
    //         e.printStackTrace();
    //     }
    // }
    
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

    public void selectCategoryFromDropdown(String categoryName) {
        WebElement dropdown = driver.findElement(categoryDropdown);
        org.openqa.selenium.support.ui.Select select =
                new org.openqa.selenium.support.ui.Select(dropdown);
        select.selectByVisibleText(categoryName);
    }

    public void clickSearchButton() {

        WebElement button = driver.findElement(searchButton);
        button.click();

        new org.openqa.selenium.support.ui.WebDriverWait(driver,
                java.time.Duration.ofSeconds(10))
                .until(driver ->
                        driver.findElements(By.xpath("//table//tbody//tr")).size() > 0
                );
    }

    private int getCategoryColumnIndex() {
        java.util.List<WebElement> headers = driver.findElements(By.xpath("//table//th"));
        for (int i = 0; i < headers.size(); i++) {
            if (headers.get(i).getText().trim().equalsIgnoreCase("Category")) {
                return i + 1; // XPath index starts at 1
            }
        }
        throw new RuntimeException("Category column not found");
    }

    public boolean areOnlyPlantsFromCategoryDisplayed(String expectedCategory) {

        java.util.List<WebElement> rows = driver.findElements(tableRows);

        if (rows.isEmpty()) {
            throw new AssertionError("No rows found after filtering");
        }

        for (WebElement row : rows) {

            // Find the Category cell by header text
            WebElement categoryCell =
                    row.findElement(By.xpath(".//td[count(//th[normalize-space()='Category']/preceding-sibling::th)+1]"));

            String categoryText = categoryCell.getText().trim();

            System.out.println("Category cell value: " + categoryText);

            if (!categoryText.equalsIgnoreCase(expectedCategory)) {
                return false;
            }
        }
        return true;
    }



   
    // Page verification methods
    public boolean isOnPlantsPage() {
        try {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("/plants");
        } catch (Exception e) {
            System.err.println("Error checking if on Plants page: " + e.getMessage());
            return false;
        }
    }

    /**
     * Extracts plant name to unit price mapping from the plants table
     * Expected columns: Name, Category, Price, Stock, Actions
     * @return Map of plant name to unit price
     */
    public Map<String, Double> getPlantPrices() {
        Map<String, Double> plantPrices = new HashMap<>();

        try {
            List<WebElement> rows = driver.findElements(plantsTableRows);
            System.out.println("\n========== EXTRACTING PLANT PRICES ==========");
            System.out.println("Found " + rows.size() + " plant(s) in the table");

            // Get table headers to determine column indices
            List<WebElement> headers = driver.findElements(tableHeaders);
            int nameColIndex = -1;
            int priceColIndex = -1;

            System.out.println("\nTable Headers:");
            for (int i = 0; i < headers.size(); i++) {
                String headerText = headers.get(i).getText().trim();
                System.out.println("  Column " + i + ": " + headerText);

                // Normalize header: remove sorting arrows and special characters, convert to lowercase
                String normalizedHeader = headerText.replaceAll("[↑↓▲▼⬆⬇]", "").trim().toLowerCase();

                // Fuzzy matching: look for headers containing the word "name"
                if (normalizedHeader.contains("name") && !normalizedHeader.contains("category")) {
                    nameColIndex = i;
                    System.out.println("    → Identified as Name column");
                }
                // Fuzzy matching: look for "price" but exclude "total" to avoid confusion with "Total Price"
                else if (normalizedHeader.contains("price") && !normalizedHeader.contains("total")) {
                    priceColIndex = i;
                    System.out.println("    → Identified as Price column");
                }
            }

            if (nameColIndex == -1 || priceColIndex == -1) {
                String errorMsg = "✗ Could not find Name or Price columns\n" +
                    "  Name column index: " + nameColIndex + "\n" +
                    "  Price column index: " + priceColIndex + "\n" +
                    "  Headers found: ";
                for (int i = 0; i < headers.size(); i++) {
                    errorMsg += "\n    [" + i + "] " + headers.get(i).getText();
                }
                System.err.println(errorMsg);
                throw new RuntimeException(errorMsg);
            }

            System.out.println("✓ Found Name column at index " + nameColIndex + ", Price column at index " + priceColIndex);

            System.out.println("\nExtracting prices:");
            // Extract plant names and prices from each row
            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() > Math.max(nameColIndex, priceColIndex)) {
                    try {
                        String plantName = cells.get(nameColIndex).getText().trim();
                        String priceText = cells.get(priceColIndex).getText().trim();

                        // Remove currency symbols and parse price
                        String numericPrice = priceText.replaceAll("[^0-9.]", "");
                        if (!numericPrice.isEmpty()) {
                            double price = Double.parseDouble(numericPrice);
                            plantPrices.put(plantName, price);
                            System.out.println("  ✓ " + plantName + ": $" + price);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("  ✗ Could not parse price for row " + (i + 1));
                    }
                }
            }

            System.out.println("\nTotal plant prices extracted: " + plantPrices.size());
            System.out.println("=============================================\n");

        } catch (Exception e) {
            System.err.println("Error extracting plant prices: " + e.getMessage());
            e.printStackTrace();
        }

        return plantPrices;
    }

    /**
     * Get the price of a specific plant by name
     * @param plantName The name of the plant
     * @return The unit price of the plant, or null if not found
     */
    public Double getPlantPrice(String plantName) {
        Map<String, Double> allPrices = getPlantPrices();
        return allPrices.get(plantName);
    }

    // Page inspection for debugging
    public void inspectPlantsPage() {
        System.out.println("\n========== PLANTS PAGE INSPECTION ==========");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        try {
            List<WebElement> tables = driver.findElements(By.tagName("table"));
            System.out.println("Tables found: " + tables.size());

            List<WebElement> rows = driver.findElements(plantsTableRows);
            System.out.println("Plant records: " + rows.size());

            List<WebElement> headers = driver.findElements(tableHeaders);
            System.out.println("Table headers: " + headers.size());
            for (WebElement header : headers) {
                System.out.println("  - " + header.getText());
            }

        } catch (Exception e) {
            System.err.println("Error during inspection: " + e.getMessage());
        }

        System.out.println("============================================\n");
    }
}
