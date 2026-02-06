package runners.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/Dashboard",
        glue = "stepdefinitions",
        tags = "@apiuser",   // Change this to @user to run only user tests, or @api for all API tests
        plugin = {
                "pretty",
                "html:target/cucumber-api-report.html"
        },
        monochrome = true
)
public class ApiTestRunner extends AbstractTestNGCucumberTests {
}
