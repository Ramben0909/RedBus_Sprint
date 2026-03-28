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
        // ✅ Use currentInvalidDest, not toInput (toInput is never set in invalid scenario)
        boolean result = ctx.searchPage.getInvalidDestinationSuggestions(ctx.currentInvalidDest);
        Assert.assertTrue(result, "Expected 'No Results Found' but suggestions appeared");
    }
}