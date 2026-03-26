package testRunner.Runner_SDC;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = {"src/test/resources/FeatureFiles/Features_SDC"},
        glue = {"stepDefinitions.StepDefinations_SDC", "Hooks.Hooks_SDC"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        tags = ""
)
public class TestRunner extends AbstractTestNGCucumberTests {
    
    // This allows TestNG to run scenarios in parallel
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}