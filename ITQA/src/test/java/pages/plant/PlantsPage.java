package pages.plant;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantsPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Locators
    private By plantsTable = By.tagName("table");
    private By plantsTableRows = By.cssSelector("table tbody tr");
    private By tableHeaders = By.cssSelector("table thead th");

    // Constructor
    public PlantsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Navigation
    public void navigateToPlantsPage(String baseUrl) {
        String fullUrl = baseUrl + "/ui/plants";
        driver.get(fullUrl);
        System.out.println("Navigated to Plants page: " + fullUrl);
        try {
            Thread.sleep(1000); // Wait for page load
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
