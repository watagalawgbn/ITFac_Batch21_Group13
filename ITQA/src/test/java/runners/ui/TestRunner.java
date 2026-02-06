package runners.ui;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {
                "stepdefinitions",
                "utils"
        },
        tags = "@ui",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-sales-ui.html"
        },
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
