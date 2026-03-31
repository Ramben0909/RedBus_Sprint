package testRunner.TrainSearchResults;

import DriverManager.DriverManagerRB;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;

@CucumberOptions(
    features   = "src/test/resources/FeatureFiles/SearchandResults/TrainSearch.feature",
    glue       = {"stepDefinitions.SearchResultsTrains", "Hooks.Hooks_SearchTrains"},
    plugin     = {"pretty", "html:target/cucumber-search-report.html",
                  "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"},
    monochrome = true
)
public class TestRunnerSearch extends AbstractTestNGCucumberTests {

    @BeforeClass
    @Parameters("browser")
    public void setBrowser(String browser) {
        DriverManagerRB.setBrowserName(browser);
    }

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}