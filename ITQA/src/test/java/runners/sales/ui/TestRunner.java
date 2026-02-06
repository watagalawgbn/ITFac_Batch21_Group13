package runners.sales.ui;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/sales/ui",
        glue = {
                "stepdefinitions.sales.ui",
                "utils"
        },
        tags = "@user",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-sales-ui.html"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
