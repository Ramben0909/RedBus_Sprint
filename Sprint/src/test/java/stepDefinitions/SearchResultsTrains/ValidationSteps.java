package stepDefinitions.SearchResultsTrains;

import io.cucumber.java.en.*;
import org.testng.Assert;

public class ValidationSteps {

    private final Context.ScenarioContext ctx;

    public ValidationSteps(Context.ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @Then("No suggestions should be displayed for invalid destination")
    public void no_suggestions_for_invalid_destination() throws InterruptedException {
        boolean result = ctx.searchPage.getInvalidDestinationSuggestions(ctx.toInput);
        Assert.assertTrue(result, "Expected 'No Results Found' but suggestions appeared");
    }
}