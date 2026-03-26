package stepDefinitions.SearchResultsTrains;

import io.cucumber.java.en.*;
import org.testng.Assert;

public class TrainSearchSteps {

    private final Context.ScenarioContext ctx;

    public TrainSearchSteps(Context.ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Given("User is on redRail homepage")
    public void user_is_on_homepage() {
        ctx.searchPage.navigateTo("https://www.redbus.in/");
    }

    @When("User clicks on Train Tickets")
    public void user_clicks_train_tickets() throws InterruptedException {
        ctx.searchPage.clickTrainTab();
    }

    @When("User enters {string} in From field")
    public void user_enters_from(String from) {
        ctx.fromInput = from;
        ctx.isFrom    = true;
    }

    @When("User enters {string} in To field")
    public void user_enters_to(String to) {
        ctx.toInput = to;
        ctx.isFrom  = false;
    }

    @When("User selects {string} from suggestions")
    public void user_selects_from_suggestions(String targetName) throws InterruptedException {
        if (ctx.isFrom) {
            ctx.searchPage.selectSource(ctx.fromInput, targetName);
        } else {
            ctx.searchPage.selectDestination(ctx.toInput, targetName);
        }
    }

    @When("User clicks on Search Trains button")
    public void user_clicks_search() {
        ctx.searchPage.clickSearch();
    }

    @Then("Train results should be displayed")
    public void verify_results() throws InterruptedException {
        Thread.sleep(3000);
        Assert.assertTrue(ctx.resultsPage.areResultsDisplayed(), "No train results were displayed!");
        System.out.println("Total results: " + ctx.resultsPage.getResultsCount());
    }
}