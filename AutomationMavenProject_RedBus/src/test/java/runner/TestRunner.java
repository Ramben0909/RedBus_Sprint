package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features = "src/test/resources/features",   // where .feature files are
    glue = {"stepDefinitions", "hooks"},         // where Step Defs and Hooks are
    plugin = {
        "pretty",                                // readable console output
        "html:target/cucumber-report.html"       // HTML report generated here
    },
    monochrome = true                            // clean black/white console output
)
public class TestRunner extends AbstractTestNGCucumberTests {
    // body stays empty — Cucumber + TestNG handle everything
	 @Override
	    @DataProvider(parallel = true)
	    public Object[][] scenarios() {
	        return super.scenarios();
	    }
}