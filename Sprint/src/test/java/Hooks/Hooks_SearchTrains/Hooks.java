package Hooks.Hooks_SearchTrains;

import Context.ScenarioContext;
import DriverManager.DriverManagerRB;
import Pages.Trains.SearchTrainsPage;
import Pages.Trains.ResultsPage;
import Pages.Trains.FilterSortPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;

public class Hooks {

    @Before
    public void setUp() {
        DriverManagerRB.initDriver();            
        WebDriver driver = DriverManagerRB.getDriver();

        ScenarioContext ctx = ScenarioContext.get();
        ctx.searchPage     = new SearchTrainsPage(driver);
        ctx.resultsPage    = new ResultsPage(driver);
        ctx.filterSortPage = new FilterSortPage(driver);
    }

    @After
    public void tearDown() {
        DriverManagerRB.quitDriver();
        ScenarioContext.reset();
    }
}