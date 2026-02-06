package runners.sales.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/sales/api",
        glue = {
                "stepdefinitions.sales.api",
                "utils"
        },
        tags = "@admin",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-sales-api.html"
        },
        monochrome = true
)
public class ApiTestRunner extends AbstractTestNGCucumberTests {
}
