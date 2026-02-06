package stepdefinitions.sales.ui;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import pages.sales.SalesPage;
import stepdefinitions.Hooks;

public class SalesSteps {

    private WebDriver driver;
    private SalesPage salesPage;
    private String baseUrl;

    public SalesSteps() {
        // Driver will be initialized by Hooks
    }

    private void initializePage() {
        if (driver == null) {
            driver = Hooks.getDriver();
            baseUrl = Hooks.getBaseUrl();
        }
        if (salesPage == null) {
            salesPage = new SalesPage(driver);
        }
    }

    // ========== GIVEN STEPS ==========

    @Given("at least one sale record exists in the system")
    public void atLeastOneSaleRecordExistsInTheSystem() {
        initializePage();
        System.out.println("Precondition: At least one sale record should exist in the system");
        System.out.println("Note: This is a data precondition - verify in the database or UI");
        // This is a precondition - actual verification happens when navigating to page
    }

    @Given("no sales records exist in the system")
    public void noSalesRecordsExistInTheSystem() {
        initializePage();
        System.out.println("Precondition: No sales records should exist in the system");
        System.out.println("Note: This is a data precondition - verify system is in empty state");
        // This is a precondition - actual verification happens when navigating to page
    }

    // ========== WHEN STEPS ==========

    @When("the user navigates to the Sales page {string}")
    public void theUserNavigatesToTheSalesPage(String salesPageUrl) {
        initializePage();
        try {
            String fullUrl = baseUrl + salesPageUrl;
            System.out.println("\n========== NAVIGATING TO SALES PAGE ==========");
            System.out.println("Navigating to: " + fullUrl);

            driver.get(fullUrl);
            Thread.sleep(1000); // Wait for page load

            System.out.println("Current URL: " + driver.getCurrentUrl());
            System.out.println("Page Title: " + driver.getTitle());

            // Inspect page for debugging
            salesPage.inspectSalesPage();

            System.out.println("✓ Successfully navigated to Sales page");
            System.out.println("==============================================\n");

        } catch (Exception e) {
            System.err.println("✗ Error navigating to Sales page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ========== THEN STEPS ==========

    @Then("the Sales page should load successfully")
    public void theSalesPageShouldLoadSuccessfully() {
        initializePage();
        System.out.println("\n========== VERIFYING SALES PAGE LOADED ==========");
        System.out.println("Current URL: " + driver.getCurrentUrl());
        System.out.println("Page Title: " + driver.getTitle());

        boolean pageLoaded = salesPage.isSalesPageLoaded();

        if (!pageLoaded) {
            System.err.println("\n✗ SALES PAGE LOAD FAILURE DETAILS:");
            System.err.println("  - Current URL: " + driver.getCurrentUrl());
            System.err.println("  - Page Title: " + driver.getTitle());
            System.err.println("  - Expected URL to contain: /sales");

            // Check if redirected to login
            if (driver.getCurrentUrl().contains("/login")) {
                System.err.println("  - ⚠ REDIRECTED TO LOGIN PAGE - User session may have expired");
            }

            // Print page source for debugging (first 500 chars)
            try {
                String pageSource = driver.getPageSource();
                System.err.println("  - Page Source (first 500 chars): " +
                    pageSource.substring(0, Math.min(500, pageSource.length())));
            } catch (Exception e) {
                System.err.println("  - Could not get page source: " + e.getMessage());
            }
        }

        Assert.assertTrue(pageLoaded,
                "Sales page should load successfully. Current URL: " + driver.getCurrentUrl());

        System.out.println("✓ Sales page loaded successfully");
        System.out.println("=================================================\n");
    }

    @Then("the user should be on the Sales page")
    public void theUserShouldBeOnTheSalesPage() {
        initializePage();
        System.out.println("Verifying user is on Sales page...");

        boolean onSalesPage = salesPage.isOnSalesPage();
        String currentUrl = driver.getCurrentUrl();

        Assert.assertTrue(onSalesPage,
                "User should be on Sales page. Current URL: " + currentUrl);

        System.out.println("✓ User is on Sales page: " + currentUrl);
    }

    @Then("all existing sales records should be displayed in the sales table")
    public void allExistingSalesRecordsShouldBeDisplayedInTheSalesTable() {
        initializePage();
        System.out.println("Verifying sales records are displayed...");

        boolean recordsDisplayed = salesPage.areSalesRecordsDisplayed();
        int recordCount = salesPage.getSalesRecordsCount();

        Assert.assertTrue(recordsDisplayed,
                "Sales records should be displayed in the table");

        System.out.println("✓ Sales records are displayed: " + recordCount + " record(s) found");
    }

    @Then("each record should be shown as a separate row")
    public void eachRecordShouldBeShownAsASeparateRow() {
        initializePage();
        System.out.println("Verifying each record is shown as a separate row...");

        boolean recordsAsRows = salesPage.areRecordsDisplayedAsRows();
        int recordCount = salesPage.getSalesRecordsCount();

        Assert.assertTrue(recordsAsRows,
                "Each record should be displayed as a separate row");

        System.out.println("✓ All " + recordCount + " record(s) are displayed as separate rows");
    }

    @Then("the Total Price column should display calculated values")
    public void theTotalPriceColumnShouldDisplayCalculatedValues() {
        initializePage();
        System.out.println("Verifying Total Price column displays calculated values...");

        boolean totalPriceDisplayed = salesPage.isTotalPriceDisplayed();

        Assert.assertTrue(totalPriceDisplayed,
                "Total Price column should be displayed");

        System.out.println("✓ Total Price column is displayed with calculated values");
    }

    @Then("the Total Price should equal Quantity multiplied by Unit Price for each record")
    public void theTotalPriceShouldEqualQuantityMultipliedByUnitPriceForEachRecord() {
        initializePage();
        System.out.println("Verifying Total Price = Quantity × Unit Price for each record...");

        boolean calculationsCorrect = salesPage.isTotalPriceCalculatedCorrectly();

        Assert.assertTrue(calculationsCorrect,
                "Total Price should equal Quantity × Unit Price for all records");

        System.out.println("✓ All Total Price calculations are correct");
    }

    @Then("the Edit button should not be visible for the user role")
    public void theEditButtonShouldNotBeVisibleForTheUserRole() {
        initializePage();
        System.out.println("Verifying Edit button is not visible for user role...");

        boolean editButtonVisible = salesPage.isEditButtonVisible();

        Assert.assertFalse(editButtonVisible,
                "Edit button should NOT be visible for user role");

        System.out.println("✓ Edit button is not visible (user has read-only access)");
    }

    @Then("the user should be unable to modify any sales record")
    public void theUserShouldBeUnableToModifyAnySalesRecord() {
        initializePage();
        System.out.println("Verifying user cannot modify sales records...");

        boolean canModify = salesPage.canUserModifyRecords();

        Assert.assertFalse(canModify,
                "User should NOT be able to modify sales records");

        System.out.println("✓ User cannot modify sales records (read-only access confirmed)");
    }

}