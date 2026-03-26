package stepDefinitions.SearchResultsTrains;  // ← change this

import Pages.Trains.SearchTrainsPage;
import Pages.Trains.ResultsPage;
import Pages.Trains.FilterSortPage;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import java.time.Duration;

public class Hooks {

    private final Context.ScenarioContext ctx;

    public Hooks(Context.ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Before
    public void setUp() {
        ChromeDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        ctx.searchPage     = new SearchTrainsPage(driver);
        ctx.resultsPage    = new ResultsPage(driver);
        ctx.filterSortPage = new FilterSortPage(driver);
    }

    @After
    public void tearDown() {
        if (ctx.searchPage != null && ctx.searchPage.driver != null) {
            ctx.searchPage.driver.quit();
        }
    }
}