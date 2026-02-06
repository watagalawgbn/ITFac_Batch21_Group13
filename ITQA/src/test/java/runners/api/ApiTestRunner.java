package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/api-cucumber-report.html",
                "json:target/cucumber-reports/api-cucumber.json",
                "junit:target/cucumber-reports/api-cucumber.xml",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm"
        },
        tags = "@API", // Run all API tests, can be overridden with -Dcucumber.filter.tags
        monochrome = true

)
public class ApiTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = false)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
