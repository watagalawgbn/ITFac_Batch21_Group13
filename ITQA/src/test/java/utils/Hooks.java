package utils;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setUp() {
        DriverManager.initializeDriver("chrome");
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}