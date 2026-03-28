package Hooks.HooksAP;

import Driver.DriverAP.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {

    @Before
    public void setup() {
        DriverManager.initDriver();
    }

    @After
    public void tearDown() {
        DriverManager.quitDriver();
    }
}