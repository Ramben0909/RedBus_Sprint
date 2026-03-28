package testRunner.TrainSearchResults;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
    features   = "src/test/resources/FeatureFiles/SearchandResults/TrainSearch.feature",
    glue       = {"stepDefinitions.SearchResultsTrains", "Hooks.Hooks_SearchTrains"},
    plugin     = {"pretty", "html:target/cucumber-search-report.html",
                  "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
    monochrome = true
)
public class TestRunnerSearch extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}