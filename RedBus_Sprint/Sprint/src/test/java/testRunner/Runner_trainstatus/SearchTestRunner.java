package testRunner.Runner_trainstatus;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions(
    features = "src/test/resources/FeatureFiles/features_trainstatus",
    glue = {"stepDefinitions.stepDefinitions_trainstatus", "Hooks.Hooks_trainstatus"}, 
    tags = "@Search",
    plugin = {"pretty", "html:target/search-report.html"}
)
public class SearchTestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}