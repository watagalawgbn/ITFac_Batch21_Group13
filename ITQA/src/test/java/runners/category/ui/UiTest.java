package runners.ui;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features = "src/test/resources/features/category/ui",
    glue = {
        "stepdefinitions",
        "utils"
    },
    tags = "@ui",
    plugin = {
        "pretty",
        "html:target/ui-report.html"
    }
)
public class UiTest extends AbstractTestNGCucumberTests {
}
