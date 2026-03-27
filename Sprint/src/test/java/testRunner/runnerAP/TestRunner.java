package testRunner.runnerAP;

import DriverManager.DriverManagerAP;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterClass;

@CucumberOptions(
    features = "src/test/resources/features/busBooking.feature",
    glue = "stepdefinitions",
    plugin = {"pretty", "html:target/report.html", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @AfterClass
    public void tearDown() {
        DriverManagerAP.quitDriver();
    }
}


