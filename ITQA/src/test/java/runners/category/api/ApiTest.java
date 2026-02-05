package runners.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/category/api",
    glue = {
        "stepdefinitions",
        "utils"
    },
    tags = "@api",
    plugin = {
        "pretty",
        "html:target/api-report.html"
    }
)
public class ApiTest extends AbstractTestNGCucumberTests {
}
