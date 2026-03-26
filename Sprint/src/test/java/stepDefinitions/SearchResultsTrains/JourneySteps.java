package stepDefinitions.SearchResultsTrains;

import io.cucumber.java.en.*;
import org.testng.Assert;

public class JourneySteps {

    private final Context.ScenarioContext ctx;

    public JourneySteps(Context.ScenarioContext ctx) {
        this.ctx = ctx;
    }

    @When("User opens Date of Journey calendar")
    public void user_opens_calendar() throws InterruptedException {
        ctx.searchPage.openCalendar();
    }

    @When("User selects today's date")
    public void user_selects_today_date() throws InterruptedException {
        ctx.searchPage.selectTodayDate();
    }

    @Then("Today's date should be selected successfully")
    public void verify_today_date_selected() {
        String selectedDate = ctx.resultsPage.getSelectedDateText();
        String today = String.valueOf(java.time.LocalDate.now().getDayOfMonth());
        Assert.assertTrue(selectedDate.contains(today), "Today's date not selected properly");
    }

    @When("User selects first available train")
    public void user_selects_first_available_train() {
        ctx.resultsPage.clickFirstAvailableTrain();
    }

    @Then("Free cancellation should be displayed")
    public void verify_free_cancellation() {
        Assert.assertTrue(ctx.resultsPage.isFreeCancellationDisplayed(),
            "Free Cancellation not displayed!");
    }
}