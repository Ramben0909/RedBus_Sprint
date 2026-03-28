package stepDefinitions.SearchResultsTrains;

import io.cucumber.java.en.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class FilterSortSteps {

    private final Context.ScenarioContext ctx;

    public FilterSortSteps(Context.ScenarioContext ctx) {
        this.ctx = ctx;
    }

    // ── AC filter ─────────────────────────────────────────────────────────────

    @And("User selects AC Filter")
    public void user_selects_ac_filter() throws InterruptedException {
        ctx.filterSortPage.selectACFilter();
    }

    @Then("Only AC trains are visible should be displayed")
    public void verify_only_ac_trains() {
        List<String> classes = ctx.resultsPage.getJourneyClasses(10);
        boolean allAC = classes.stream()
            .allMatch(c -> c.contains("A") || c.contains("E") || c.equalsIgnoreCase("CC"));
        Assert.assertTrue(allAC, "Non-AC train found! Filter failed");
    }

    // ── Clear filter ───────────────────────────────────────────────────────────

    @And("User clears all filters")
    public void user_clears_all_filters() throws InterruptedException {
        ctx.filterSortPage.clearAllFilters();
    }

    @Then("Non-AC trains should be visible")
    public void verify_non_ac_trains() {
        List<String> classes = ctx.resultsPage.getJourneyClasses(50);
        boolean foundSL = classes.stream().anyMatch(c -> c.equalsIgnoreCase("SL"));
        Assert.assertTrue(foundSL, "SL class not found! Clear filter failed");
    }

    // ── Sort by arrival ────────────────────────────────────────────────────────
    
    @And("User sorts by Arrival Time")
    public void sortByArrivalTime() throws InterruptedException {
        
		WebElement arrivalBtn = ctx.resultsPage.wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//text()[contains(.,'Arrival')]]")));
        arrivalBtn.click();
        System.out.println("Clicked sort by arrival");
        Thread.sleep(4000); // results reload after sort — give it time
    }

    @Then("Trains are sorted in ascendeing order of arrival time")
    public void verify_arrival_sort() {
        List<String> rawTimes = ctx.resultsPage.getArrivalTimesText(5);
        List<Integer> minutes = new ArrayList<>();

        for (String t : rawTimes) {
            String[] parts = t.split(" ");
            String[] hm    = parts[0].split(":");
            int hour   = Integer.parseInt(hm[0]);
            int minute = Integer.parseInt(hm[1]);
            String ampm = parts[1];
            if (ampm.equalsIgnoreCase("PM") && hour != 12) hour += 12;
            else if (ampm.equalsIgnoreCase("AM") && hour == 12) hour = 0;
            minutes.add(hour * 60 + minute);
        }

        for (int i = 0; i < minutes.size() - 1; i++) {
            Assert.assertTrue(minutes.get(i) <= minutes.get(i + 1),
                "Sorting error at index " + i + ": " + rawTimes.get(i) + " > " + rawTimes.get(i + 1));
        }
    }
}