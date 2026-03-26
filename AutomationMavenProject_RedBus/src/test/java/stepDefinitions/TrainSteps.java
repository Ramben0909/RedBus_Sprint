package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;
import pages.TrainPage;
import utils.DriverManager;

import java.util.List;

public class TrainSteps {

    // Page object is created fresh per scenario because Hooks sets up a new
    // driver in @Before and tears it down in @After for each scenario.
    TrainPage trainPage = new TrainPage(DriverManager.getDriver());

    // ── GIVEN ─────────────────────────────────────────────────────────────────

    @Given("the user is on the RedBus train running status page")
    public void userIsOnTrainPage() {
        // Navigation is handled in Hooks @Before — nothing extra needed here.
        System.out.println("User is on RedBus Train Running Status page.");
    }

    // ── WHEN ──────────────────────────────────────────────────────────────────

    /**
     * Used by the valid-search Scenario Outline (TC_01 – TC_04).
     * Types input, picks the first suggestion, clicks Check Status.
     */
    @When("the user searches for {string}")
    public void searchForTrain(String input) {
        trainPage.searchTrain(input);
    }

    /**
     * Used by the invalid-search Scenario Outline (TC_07, TC_08).
     * Only types — no suggestion appears, button stays disabled.
     */
    @When("the user types {string} in the search box")
    public void typeInSearchBox(String input) {
        trainPage.typeOnly(input);
    }

    /**
     * DataTable step — drives the full availability flow in order:
     *   1. click availability
     *   2. navigate to next month
     *   3. select date
     * Each row in the DataTable is one step keyword.
     */
    @When("the user performs availability steps")
    public void performAvailabilitySteps(DataTable dataTable) {
        List<String> steps = dataTable.asList();

        for (String step : steps) {
            switch (step.trim().toLowerCase()) {
                case "click availability":
                    trainPage.clickAvailabilityButton();
                    break;
                case "navigate to next month":
                    trainPage.navigateToNextMonth();
                    break;
                case "select date":
                    trainPage.selectDate();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown availability step: " + step);
            }
        }
    }

    // ── THEN ──────────────────────────────────────────────────────────────────

    /** TC_01 – TC_04: confirms liveTrainDetails URL opened. */
    @Then("the train details page should be displayed")
    public void verifyTrainDetailsPage() {
        Assert.assertTrue(
            trainPage.isTrainDetailsPageOpened(),
            "FAIL: Did not navigate to liveTrainDetails page."
        );
    }

    /** TC_07, TC_08: confirms Check Status button remains disabled. */
    @Then("the Check Status button should be disabled")
    public void verifyCheckStatusButtonDisabled() {
        Assert.assertTrue(
            trainPage.isCheckStatusButtonDisabled(),
            "FAIL: Check Status button should be disabled for invalid input."
        );
    }

    /** Story-3 full flow: confirms seat availability result page opened. */
    @Then("the seat availability result should be displayed")
    public void verifyAvailabilityResult() {
        Assert.assertTrue(
            trainPage.isAvailabilityResultPageOpened(),
            "FAIL: Seat availability result page did not open."
        );
    }
}