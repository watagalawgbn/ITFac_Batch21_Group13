package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        // This initializes the driver
        DriverFactory.getDriver();
    }

    @After
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}