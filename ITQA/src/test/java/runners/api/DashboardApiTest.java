package runners.api;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/", 
        glue = "stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-dashboard-api-reports.html"
        },
        monochrome = true
)
public class DashboardApiTest extends AbstractTestNGCucumberTests { 
 
}
