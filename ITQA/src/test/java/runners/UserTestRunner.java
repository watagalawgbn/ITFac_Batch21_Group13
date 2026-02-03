package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "stepdefinitions",
        tags = "@NormalUserTests",
        plugin = {
                "pretty",
                "html:target/cucumber-reports-user.html"
        },
        monochrome = true
)
public class UserTestRunner extends AbstractTestNGCucumberTests {
}
