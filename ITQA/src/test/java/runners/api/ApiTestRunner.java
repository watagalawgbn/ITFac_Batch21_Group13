package runners.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "stepdefinitions",
                "utils"
        },
        tags = "@api",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-sales-api.html"
        },
        monochrome = true
)
public class ApiTestRunner extends AbstractTestNGCucumberTests {
}
