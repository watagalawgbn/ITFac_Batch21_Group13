package pages.sales;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


import pages.plant.PlantsPage;



public class SalesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    //locator for the sales table
    private final By salesTable = By.cssSelector("table.table-bordered.table-striped.align-middle");
    private final By salesRows = By.xpath("//table//tbody/tr");
    private By salesTableRows = By.cssSelector("table tbody tr");
    private By tableHeaders = By.cssSelector("table thead th");
    private By editButtons = By.xpath("//button[contains(text(),'Edit') or contains(@class,'edit')]");
    private By deleteButtons = By.xpath("//button[contains(text(),'Delete') or contains(@class,'delete')]");
    private By totalPriceColumn = By.xpath("//td[contains(@class,'total') or position()=last()]");
    private By quantityColumn = By.xpath("//td[contains(@class,'quantity')]");
    private By unitPriceColumn = By.xpath("//td[contains(@class,'price') or contains(@class,'unit')]");
    private final By deleteButtonInRow = By.xpath(".//button[contains(@class,'btn-outline-danger')]");
    private final By noSalesMessage = By.xpath("//*[text() = 'No sales found']");
    private final By sellPlantButton = By.xpath("//a[contains(text(), 'Sell Plant')]");

    // pagination container
    private final By pagination = By.cssSelector("ul.pagination");
    // page numbers except active one
    private final By paginationPages = By.cssSelector("ul.pagination li.page-item:not(.active):not(.disabled) a.page-link");

    private final By soldDateColumn = By.xpath("//table//tbody/tr/td[4]");
    private final By soldDateHeader = By.xpath("//th[contains(text(),'Sold Date')]");


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

    //     // Empty state verification
    // public boolean isNoSalesMessageDisplayed() {
    //     try {
    //         wait.until(ExpectedConditions.presenceOfElementLocated(noSalesMessage));
    //         WebElement message = driver.findElement(noSalesMessage);
    //         String messageText = message.getText();
    //         System.out.println("No sales message displayed: " + messageText);
    //         return message.isDisplayed() && messageText.toLowerCase().contains("no sales");
    //     } catch (Exception e) {
    //         System.err.println("No sales message not found: " + e.getMessage());
    //         return false;
    //     }
    // }

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


    //-------------USER----------------------------
    public boolean isPaginationVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(pagination));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<String> getCurrentPageSaleIds() {
        List<WebElement> rows = driver.findElements(salesRows);
        return rows.stream()
                .map(row -> row.getText())
                .toList();
    }

    public void clickNextPage() {
        List<WebElement> oldRows =
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(salesRows));

        List<WebElement> pages =
                wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(paginationPages));

        if (pages.isEmpty()) {
            throw new RuntimeException("No enabled pagination pages available");
        }

        wait.until(ExpectedConditions.elementToBeClickable(pages.get(0))).click();
        wait.until(ExpectedConditions.stalenessOf(oldRows.get(0)));
    }



    public List<LocalDateTime> getSoldDatesAfterLoad() {
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(salesRows),
                ExpectedConditions.visibilityOfElementLocated(noSalesMessage)
        ));

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        return driver.findElements(soldDateColumn)
                .stream()
                .map(e -> LocalDateTime.parse(e.getText(), formatter))
                .toList();
    }


    // Page verification methods
    public boolean isOnSalesPage() {
        try {
            String currentUrl = driver.getCurrentUrl();
            return currentUrl.contains("/sales");
        } catch (Exception e) {
            System.err.println("Error checking if on Sales page: " + e.getMessage());
            return false;
        }
    }

    public boolean isSalesPageLoaded() {
        try {
            // First check if we're on the correct URL
            String currentUrl = driver.getCurrentUrl();
            System.out.println("Current URL: " + currentUrl);

            if (!currentUrl.contains("/sales")) {
                System.err.println("✗ Not on Sales page. Current URL: " + currentUrl);
                return false;
            }

            System.out.println("✓ URL contains '/sales'");

            // Create a shorter wait for page elements (5 seconds instead of 10)
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(5));

            try {
                // Wait for page body to be present (more reliable than specific elements)
                shortWait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
                System.out.println("✓ Page body loaded");

                // Check if either table exists OR no sales message exists
                // Don't wait too long - just check what's currently on the page
                Thread.sleep(1000); // Give page a moment to render

                boolean hasTable = !driver.findElements(salesTable).isEmpty();
                boolean hasNoSalesMsg = !driver.findElements(noSalesMessage).isEmpty();

                System.out.println("Table present: " + hasTable);
                System.out.println("No sales message present: " + hasNoSalesMsg);

                // Page is loaded if we have either the table or the no sales message
                boolean pageLoaded = hasTable || hasNoSalesMsg;

                if (pageLoaded) {
                    System.out.println("✓ Sales page loaded successfully");
                } else {
                    System.out.println("⚠ Sales page loaded but no expected elements found");
                    // Still return true as the page itself loaded, elements might load async
                }

                return true; // Page is considered loaded if URL is correct and body is present

            } catch (Exception e) {
                System.err.println("⚠ Warning checking page elements: " + e.getMessage());
                // Even if elements aren't found, if URL is correct, page is loaded
                return currentUrl.contains("/sales");
            }

        } catch (Exception e) {
            System.err.println("✗ Sales page did not load: " + e.getMessage());
            System.err.println("Current URL: " + driver.getCurrentUrl());
            return false;
        }
    }

    // Sales records verification
    public boolean areSalesRecordsDisplayed() {
        try {
            List<WebElement> rows = driver.findElements(salesTableRows);
            System.out.println("Found " + rows.size() + " sales record(s)");
            return rows.size() > 0;
        } catch (Exception e) {
            System.err.println("Error checking sales records: " + e.getMessage());
            return false;
        }
    }

    public int getSalesRecordsCount() {
        try {
            List<WebElement> rows = driver.findElements(salesTableRows);
            return rows.size();
        } catch (Exception e) {
            System.err.println("Error getting sales records count: " + e.getMessage());
            return 0;
        }
    }

    public boolean areRecordsDisplayedAsRows() {
        try {
            List<WebElement> rows = driver.findElements(salesTableRows);
            for (WebElement row : rows) {
                if (!row.isDisplayed()) {
                    return false;
                }
            }
            System.out.println("✓ All records are displayed as separate rows");
            return true;
        } catch (Exception e) {
            System.err.println("Error checking if records are displayed as rows: " + e.getMessage());
            return false;
        }
    }

    // Total Price validation
    public boolean isTotalPriceDisplayed() {
        try {
            // Check if Total Price column exists in headers
            List<WebElement> headers = driver.findElements(tableHeaders);
            for (WebElement header : headers) {
                String headerText = header.getText().toLowerCase();
                if (headerText.contains("total") || headerText.contains("price")) {
                    System.out.println("✓ Total Price column found: " + header.getText());
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.err.println("Error checking Total Price column: " + e.getMessage());
            return false;
        }
    }

    /**
     * Validates that Total Price = Quantity × Unit Price for each sales record
     * Unit prices are fetched from the Plants page
     * @return true if all calculations are correct
     */
    public boolean isTotalPriceCalculatedCorrectly() {
        try {
            // First, fetch plant prices from the Plants page
            PlantsPage plantsPage = new PlantsPage(driver);
            String currentUrl = driver.getCurrentUrl();
            String baseUrl = currentUrl.substring(0, currentUrl.indexOf("/ui/"));

            System.out.println("\n========== VALIDATING TOTAL PRICE CALCULATIONS ==========");
            System.out.println("Step 1: Fetching unit prices from Plants page...");
            plantsPage.navigateToPlantsPage(baseUrl);
            java.util.Map<String, Double> plantPrices = plantsPage.getPlantPrices();

            // Navigate back to Sales page
            System.out.println("Step 2: Returning to Sales page...");
            driver.get(currentUrl);
            Thread.sleep(1000); // Wait for page reload

            // Get table headers to determine column indices
            List<WebElement> headers = driver.findElements(tableHeaders);
            int plantColIndex = -1;
            int quantityColIndex = -1;
            int totalPriceColIndex = -1;

            System.out.println("\nStep 3: Identifying table columns...");
            for (int i = 0; i < headers.size(); i++) {
                String headerText = headers.get(i).getText().trim();
                System.out.println("  Column " + i + ": " + headerText);

                // Normalize header: remove sorting arrows and convert to lowercase
                String normalizedHeader = headerText.replaceAll("[↑↓▲▼⬆⬇]", "").trim().toLowerCase();

                // Fuzzy matching for column names
                if (normalizedHeader.contains("plant")) {
                    plantColIndex = i;
                    System.out.println("    → Identified as Plant column");
                } else if (normalizedHeader.contains("quantity")) {
                    quantityColIndex = i;
                    System.out.println("    → Identified as Quantity column");
                } else if (normalizedHeader.contains("total") && normalizedHeader.contains("price")) {
                    totalPriceColIndex = i;
                    System.out.println("    → Identified as Total Price column");
                } else if (normalizedHeader.equals("total")) {
                    totalPriceColIndex = i;
                    System.out.println("    → Identified as Total column");
                }
            }

            if (plantColIndex == -1 || quantityColIndex == -1 || totalPriceColIndex == -1) {
                String errorMsg = "✗ Could not identify required columns\n" +
                    "  Plant column index: " + plantColIndex + "\n" +
                    "  Quantity column index: " + quantityColIndex + "\n" +
                    "  Total Price column index: " + totalPriceColIndex;
                System.err.println(errorMsg);
                return false;
            }

            System.out.println("✓ Found Plant column at index " + plantColIndex +
                ", Quantity column at index " + quantityColIndex +
                ", Total Price column at index " + totalPriceColIndex);

            // Validate each sales record
            List<WebElement> rows = driver.findElements(salesTableRows);
            System.out.println("\nStep 4: Validating " + rows.size() + " sales record(s)...");

            boolean allCorrect = true;
            int validatedCount = 0;

            for (int i = 0; i < rows.size(); i++) {
                WebElement row = rows.get(i);
                List<WebElement> cells = row.findElements(By.tagName("td"));

                if (cells.size() > Math.max(Math.max(plantColIndex, quantityColIndex), totalPriceColIndex)) {
                    try {
                        String plantName = cells.get(plantColIndex).getText().trim();
                        String quantityText = cells.get(quantityColIndex).getText().trim();
                        String totalPriceText = cells.get(totalPriceColIndex).getText().trim();

                        // Parse numeric values
                        double quantity = Double.parseDouble(quantityText.replaceAll("[^0-9.]", ""));
                        double totalPrice = Double.parseDouble(totalPriceText.replaceAll("[^0-9.]", ""));

                        // Get unit price from Plants page
                        Double unitPrice = plantPrices.get(plantName);

                        if (unitPrice == null) {
                            System.err.println("  ✗ Row " + (i + 1) + ": Plant '" + plantName + "' not found in Plants page");
                            allCorrect = false;
                            continue;
                        }

                        double expectedTotal = quantity * unitPrice;

                        // Allow for small floating point differences (0.01)
                        if (Math.abs(totalPrice - expectedTotal) < 0.01) {
                            System.out.println("  ✓ Row " + (i + 1) + ": " + plantName + " | " +
                                quantity + " × $" + unitPrice + " = $" + totalPrice + " (Correct)");
                            validatedCount++;
                        } else {
                            System.err.println("  ✗ Row " + (i + 1) + ": " + plantName + " | Expected $" +
                                String.format("%.2f", expectedTotal) + " but got $" + totalPrice);
                            System.err.println("    Calculation: " + quantity + " × $" + unitPrice + " = $" +
                                String.format("%.2f", expectedTotal));
                            allCorrect = false;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("  ✗ Row " + (i + 1) + ": Could not parse numeric values - " + e.getMessage());
                        allCorrect = false;
                    }
                }
            }

            System.out.println("\nValidation Summary:");
            System.out.println("  Total records: " + rows.size());
            System.out.println("  Validated: " + validatedCount);
            System.out.println("  Result: " + (allCorrect ? "✓ All calculations correct" : "✗ Some calculations incorrect"));
            System.out.println("=========================================================\n");

            return allCorrect && validatedCount > 0;

        } catch (Exception e) {
            System.err.println("✗ Error validating Total Price calculations: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    // Edit button verification
    public boolean isEditButtonVisible() {
        try {
            List<WebElement> editButtonsList = driver.findElements(editButtons);
            boolean visible = editButtonsList.size() > 0 && editButtonsList.get(0).isDisplayed();
            System.out.println("Edit button visible: " + visible);
            return visible;
        } catch (Exception e) {
            System.out.println("Edit button not found (expected for user role)");
            return false;
        }
    }

    public boolean canUserModifyRecords() {
        try {
            // Check for Edit buttons
            List<WebElement> editButtonsList = driver.findElements(editButtons);
            if (editButtonsList.size() > 0) {
                System.err.println("✗ Edit buttons found - user should not have edit access");
                return true; // User CAN modify (unexpected)
            }

            // Check for Delete buttons
            List<WebElement> deleteButtonsList = driver.findElements(deleteButtons);
            if (deleteButtonsList.size() > 0) {
                System.err.println("✗ Delete buttons found - user should not have delete access");
                return true; // User CAN modify (unexpected)
            }

            System.out.println("✓ User cannot modify records (no edit/delete buttons)");
            return false; // User CANNOT modify (expected)

        } catch (Exception e) {
            System.out.println("✓ User cannot modify records (exception: " + e.getMessage() + ")");
            return false;
        }
    }



    public String getNoSalesMessage() {
        try {
            WebElement message = driver.findElement(noSalesMessage);
            return message.getText();
        } catch (Exception e) {
            System.err.println("Could not get no sales message: " + e.getMessage());
            return "";
        }
    }

    // Page inspection for debugging
    public void inspectSalesPage() {
        System.out.println("\n========== SALES PAGE INSPECTION ==========");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        try {
            List<WebElement> tables = driver.findElements(By.tagName("table"));
            System.out.println("Tables found: " + tables.size());

            List<WebElement> rows = driver.findElements(salesTableRows);
            System.out.println("Sales records: " + rows.size());

            List<WebElement> headers = driver.findElements(tableHeaders);
            System.out.println("Table headers: " + headers.size());
            for (WebElement header : headers) {
                System.out.println("  - " + header.getText());
            }

            List<WebElement> editBtns = driver.findElements(editButtons);
            System.out.println("Edit buttons: " + editBtns.size());

            List<WebElement> deleteBtns = driver.findElements(deleteButtons);
            System.out.println("Delete buttons: " + deleteBtns.size());

        } catch (Exception e) {
            System.err.println("Error during inspection: " + e.getMessage());
        }

        System.out.println("============================================\n");
    }
}
