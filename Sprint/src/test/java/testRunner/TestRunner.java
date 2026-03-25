package testRunner;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/java/featureFiles",
	    glue = "stepDefinitions",
	    plugin = {"pretty", "html:target/cucumber-report.html"},
	    monochrome = true
	)


public class TestRunner extends AbstractTestNGCucumberTests {
}