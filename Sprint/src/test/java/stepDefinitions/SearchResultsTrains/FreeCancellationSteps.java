package stepDefinitions.SearchResultsTrains;

import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FreeCancellationSteps {

    @When("User selects first available train")
    public void user_selects_first_available_train() {

        TrainSearchSteps.page.clickFirstAvailableTrain();
    }

    @Then("Free cancellation should be displayed")
    public void verify_free_cancellation() {

        boolean result = TrainSearchSteps.page.isFreeCancellationDisplayed();

        Assert.assertTrue(result, "Free Cancellation not displayed!");

        System.out.println("✅ Free Cancellation verified");

        TrainSearchSteps.page.driver.quit();
    }
}