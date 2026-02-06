package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions", "utils"},
        tags = "@ApiTests",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-api.html"
        },
        monochrome = true
)
public class ApiTestRunner extends AbstractTestNGCucumberTests {
}