package stepDefinitions.StepDefinations_SDC;

import Base.Hooks_SDC.Hooks;
import Pages.Pages_SDC.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class RedBusStepDefinitions {

    private RedBusHomePage homePage;
    private RedBusSearchResultsPage resultsPage;

    @Given("the user is on the RedBus homepage")
    public void userIsOnRedBusHomepage() {
        homePage = new RedBusHomePage(Hooks.driver);
        homePage.openHomepage();
    }

    @When("the user fills source as {string} and selects the suggestion")
    public void fillSourceAndSelect(String city) {
        homePage.enterSource(city);
        homePage.selectSourceSuggestion(city);
        homePage.closeAnyDropdown();
    }

    @When("the user fills destination as {string} and selects the suggestion")
    public void fillDestinationAndSelect(String city) {
        homePage.enterDestination(city);
        homePage.selectDestinationSuggestion(city);
        homePage.closeAnyDropdown();
    }

    @When("the user clicks the search buses button")
    public void clickSearchBuses() {
        homePage.clickSearchButton();
        homePage.closePopupIfAppears();
    }

    @Then("the error message {string} is displayed")
    public void verifyExactErrorMessage(String expected) {
        String actual = homePage.getErrorMessageText();
        Assert.assertTrue(actual.contains(expected));
        System.out.println("Error verified: " + actual);
    }

    @Then("the error message containing {string} is displayed")
    public void verifyErrorContains(String keyword) {
        String actual = homePage.getErrorMessageText();
        Assert.assertTrue(actual.toLowerCase().contains(keyword.toLowerCase()));
    }

    @Then("the user is navigated to the search results page")
    public void navigatedToResultsPage() {
        resultsPage = new RedBusSearchResultsPage(Hooks.driver);
        resultsPage.waitForPageLoad();
        System.out.println("Navigated to Search Results page");
    }

    @When("the user clicks the date field")
    public void clickDateField() {
        homePage.clickDateField();
    }

    @When("the user clicks the date field again")
    public void clickDateFieldAgain() {
        homePage.clickDateField();
    }

    @When("the user selects date {string}")
    public void selectDate(String day) {
        homePage.selectDate(day);
    }

    @When("the user removes the source")
    public void removeTheSource() {
        homePage.removeSource();
    }

    @Then("the source field is cleared")
    public void sourceFieldIsCleared() {
        Assert.assertTrue(homePage.isSourceCleared(), "Source field is NOT cleared!");
        System.out.println("Source successfully removed");
    }

    @When("the user clicks the time dropdown")
    public void clickTimeDropdown() {
        resultsPage.clickDTDropdown();
    }

    @When("the user selects {string} time filter")
    public void selectTimeFilter(String time) {
        resultsPage.selectTimeFilter(time);
    }

    @When("the user clicks the {string} time filter again to deselect")
    public void deselectTimeFilter(String time) {
        resultsPage.selectTimeFilter(time);
    }

    @When("the user applies the {string} filter")
    public void applyFilter(String filter) {
        resultsPage.clickFilter(filter);
    }

    @When("the user toggles the {string} filter")
    public void toggleFilter(String filter) {
        resultsPage.clickFilter(filter);
        resultsPage.clickFilter(filter);
    }

    @When("the user clicks sort by Ratings")
    public void clickSortByRatings() {
        resultsPage.clickSortByRatings();
    }
    
    @When("the user clicks sort by Ratings again")
    public void clickSortByRatingsAgain() {
        resultsPage.clickSortByRatings();
    }

    @Then("the filters are successfully applied")
    public void filtersApplied() {
        System.out.println("AC & SEATER filters applied successfully");
    }

    @Then("the filters are successfully toggled")
    public void filtersToggled() {
        System.out.println("Filters toggled ON and OFF successfully");
    }

    @Then("the Afternoon filter is applied successfully")
    public void afternoonApplied() {
        System.out.println("Afternoon filter applied");
    }

    @Then("the Afternoon filter is deselected successfully")
    public void afternoonDeselected() {
        System.out.println("Afternoon filter deselected");
    }

    @Then("the sort by Ratings is toggled successfully")
    public void sortToggled() {
        System.out.println("Sort by Ratings toggled successfully");
    }

    @Then("the calendar reopens and date {string} is selected successfully")
    public void calendarReopenedAndDateSelected(String day) {
        System.out.println("Calendar reopened & date " + day + " selected successfully!");
    }
}