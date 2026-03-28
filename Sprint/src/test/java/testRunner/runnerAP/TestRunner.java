package testRunner.runnerAP;

import DriverManager.DriverManagerAP;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;

@CucumberOptions(
    features = "src/test/resources/FeatureFiles/featureAP/busBooking.feature",
    glue = "stepDefinitions.stepdefinitionsAP",
    plugin = {"pretty", "html:target/report.h"
    		+ "tml", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @AfterClass
    public void tearDown() {
        DriverManagerAP.quitDriver();
    }
}


