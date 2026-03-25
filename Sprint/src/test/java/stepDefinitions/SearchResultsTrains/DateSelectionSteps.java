package stepDefinitions.SearchResultsTrains;

import org.testng.Assert;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DateSelectionSteps {

    // Default constructor
    public DateSelectionSteps() {}

    @When("User opens Date of Journey calendar")
    public void user_opens_calendar() throws InterruptedException {
        TrainSearchSteps.page.openCalendar();
    }

    @When("User selects today's date")
    public void user_selects_today_date() throws InterruptedException {
        TrainSearchSteps.page.selectTodayDate();
    }

    @Then("Today's date should be selected successfully")
    public void verify_today_date_selected() {

        String selectedDate = TrainSearchSteps.page.driver
                .findElement(org.openqa.selenium.By.xpath("//div[@data-field='date']//div"))
                .getText();

        String today = String.valueOf(java.time.LocalDate.now().getDayOfMonth());

        Assert.assertTrue(selectedDate.contains(today),
                "Today's date not selected properly");

        System.out.println("PASSED — Today's date selected: " + selectedDate);

        TrainSearchSteps.page.driver.quit();
    }
}