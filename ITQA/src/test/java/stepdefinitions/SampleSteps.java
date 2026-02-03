package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import utils.DriverManager;

public class SampleSteps {
    private WebDriver driver;

    @Before
    public void setUp() {
        DriverManager.initializeDriver("chrome");
        driver = DriverManager.getDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }

    // Framework setup test steps (from original sample.feature)
    @Given("framework is configured correctly")
    public void framework_is_configured_correctly() {
        System.out.println("Framework configured");
    }

    @When("I run the test")
    public void i_run_the_test() {
        System.out.println("Test is running");
    }

    @Then("execution should be successful")
    public void execution_should_be_successful() {
        Assert.assertTrue(true);
    }
}

