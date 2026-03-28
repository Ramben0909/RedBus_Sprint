package stepDefinitions.SearchResultsTrains;

import io.cucumber.java.en.*;
import org.testng.Assert;
import Utils.ExcelUtilsRB;

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

    // ── loader — gets data_id and sheet from feature, loads into ctx ───────────

    @When("User loads Excel data id {string} from {string}")
    public void user_loads_excel_data(String dataId,
                                       String sheetName) throws Exception {
        String[] row = ExcelUtilsRB.getRowById(sheetName, dataId);

        // col 0 = data_id, data starts col 1
        ctx.currentFromInput  = row[1];
        ctx.currentFromSelect = row[2];

        if (sheetName.equals("InvalidSearch")) {
            ctx.currentInvalidDest = row[3];
        } else {
            ctx.currentToInput  = row[3];
            ctx.currentToSelect = row[4];
            if (sheetName.equals("DateSearch")) {
                ctx.currentDate = row[5];
            }
        }
    }

    // ── individual steps — each uses only its own ctx field ───────────────────

    @When("User enters loaded From field")
    public void user_enters_loaded_from() {
        ctx.fromInput = ctx.currentFromInput;
        ctx.isFrom    = true;
    }

    @When("User selects loaded From suggestion")
    public void user_selects_loaded_from() throws InterruptedException {
        ctx.searchPage.selectSource(
            ctx.currentFromInput, ctx.currentFromSelect);
    }

    @When("User enters loaded To field")
    public void user_enters_loaded_to() {
        ctx.toInput = ctx.currentToInput;
        ctx.isFrom  = false;
    }

    @When("User selects loaded To suggestion")
    public void user_selects_loaded_to() throws InterruptedException {
        ctx.searchPage.selectDestination(
            ctx.currentToInput, ctx.currentToSelect);
    }

    @When("User selects loaded date")
    public void user_selects_loaded_date() throws InterruptedException {
        ctx.searchPage.selectDate(ctx.currentDate);
    }

    @When("User enters loaded invalid destination")
    public void user_enters_loaded_invalid() throws InterruptedException {
        ctx.searchPage.getInvalidDestinationSuggestions(
            ctx.currentInvalidDest);
    }

    // ── existing steps for TrainResults ───────────────────────────────────────

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
    public void user_selects_from_suggestions(String targetName)
            throws InterruptedException {
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
        Assert.assertTrue(
            ctx.resultsPage.areResultsDisplayed(),
            "No train results were displayed!"
        );
        System.out.println("Total results: "
            + ctx.resultsPage.getResultsCount());
    }
}