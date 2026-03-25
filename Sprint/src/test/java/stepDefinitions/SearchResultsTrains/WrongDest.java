package stepDefinitions.SearchResultsTrains;

import org.testng.Assert;

import io.cucumber.java.en.Then;

public class WrongDest {

    // Default constructor
    public WrongDest() {}

    @Then("No suggestions should be displayed for invalid destination")
    public void no_suggestions_for_invalid_destination() throws InterruptedException {

        // ✅ Use dynamic input from previous step
        boolean result = TrainSearchSteps.page
                .getInvalidDestinationSuggestions();

        // ✅ Assert based on UI behavior (No Results Found)
        Assert.assertTrue(result, 
            "Expected 'No Results Found' but suggestions appeared");

        System.out.println("PASSED — No Results Found displayed correctly");

        // ✅ Close browser
        TrainSearchSteps.page.driver.quit();
    }
}