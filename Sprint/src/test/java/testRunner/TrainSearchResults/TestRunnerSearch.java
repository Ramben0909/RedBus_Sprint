package testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
    features   = "src/test/resources/FeatureFiles/SearchandResults/TrainSearch.feature",
    glue       = {"stepDefinitions.SearchResultsTrains", "Hooks"},
    plugin     = {"pretty", "html:target/cucumber-search-report.html"},
    monochrome = true
)
public class TestRunnerSearch extends AbstractTestNGCucumberTests {
}