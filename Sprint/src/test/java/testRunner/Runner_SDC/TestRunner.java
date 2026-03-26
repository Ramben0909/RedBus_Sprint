package testRunner.Runner_SDC;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/Features_SDC"},
        glue = {"stepDefinitions.StepDefinations_SDC", "Base.Hooks_SDC"},
        plugin = {
                "pretty",
                "html:target/cucumber-reports/cucumber.html",
                "json:target/cucumber-reports/cucumber.json"
        },
        monochrome = true,
        tags = ""
)
public class TestRunner extends AbstractTestNGCucumberTests {
}