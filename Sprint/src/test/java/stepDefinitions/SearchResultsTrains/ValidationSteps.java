package stepDefinitions.SearchResultsTrains;
import io.cucumber.java.en.*;
import org.testng.Assert;

import Context.ScenarioContext;

//ValidationSteps.java
public class ValidationSteps {

 private ScenarioContext ctx() {
     return ScenarioContext.get();
 }

 @Then("No suggestions should be displayed for invalid destination")
 public void no_suggestions_for_invalid_destination() throws InterruptedException {
     boolean result = ctx().searchPage.isNoResultsFound();
     Assert.assertTrue(result,
         "Expected 'No Results Found' but suggestions appeared");
 }
}