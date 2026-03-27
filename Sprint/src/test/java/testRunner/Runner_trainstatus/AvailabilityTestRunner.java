package testRunner.Runner_trainstatus;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions(
    features = "src/test/resources/FeatureFiles/features_trainstatus",
    glue = {"stepDefinitions.stepDefinitions_trainstatus", "Hooks.Hooks_trainstatus"}, 
    tags = "@Availability",
    plugin = {"pretty", "html:target/availability-report.html"}
)
public class AvailabilityTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}