package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class SampleSteps {

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
