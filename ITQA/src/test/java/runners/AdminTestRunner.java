package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        tags = "@AdminTests",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-admin.html"
        },
        monochrome = true
)
public class AdminTestRunner extends AbstractTestNGCucumberTests {
}
