package testRunner.runnerAP;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions","hooks"},
        plugin = {"pretty","html:target/report.html"}
)
public class TestRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)   // ✅ PARALLEL EXECUTION
    public Object[][] scenarios() {
        return super.scenarios();
    }
}