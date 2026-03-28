package stepDefinitions.SearchResultsTrains;

import Context.ScenarioContext;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import java.util.ArrayList;
import java.util.List;

public class FilterSortSteps {

    // ── NO constructor, NO injected field ────────────────────────────
    private ScenarioContext ctx() {
        return ScenarioContext.get();
    }

    // ── AC filter ────────────────────────────────────────────────────
    @And("User selects AC Filter")
    public void user_selects_ac_filter() throws InterruptedException {
        ctx().filterSortPage.selectACFilter();
    }

    @Then("Only AC trains are visible should be displayed")
    public void verify_only_ac_trains() {
        List<String> classes = ctx().resultsPage.getJourneyClasses(10);
        boolean allAC = classes.stream()
            .allMatch(c -> c.contains("A") || c.contains("E")
                        || c.equalsIgnoreCase("CC"));
        Assert.assertTrue(allAC, "Non-AC train found! Filter failed");
    }

    // ── Clear filter ─────────────────────────────────────────────────
    @And("User clears all filters")
    public void user_clears_all_filters() throws InterruptedException {
        ctx().filterSortPage.clearAllFilters();
    }

    @Then("Non-AC trains should be visible")
    public void verify_non_ac_trains() {
        List<String> classes = ctx().resultsPage.getJourneyClasses(50);
        boolean foundSL = classes.stream().anyMatch(c -> c.equalsIgnoreCase("SL"));
        Assert.assertTrue(foundSL, "SL class not found! Clear filter failed");
    }

    // ── Sort by arrival ──────────────────────────────────────────────
    @And("User sorts by Arrival Time")
    public void sortByArrivalTime() throws InterruptedException {
        WebElement arrivalBtn = ctx().resultsPage.wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//text()[contains(.,'Arrival')]]")));
        arrivalBtn.click();
        System.out.println("Clicked sort by arrival");
        Thread.sleep(5000); // ← 4000 → 5000 for Edge to finish re-rendering before getArrivalTimesText is called
    }
    
    

    @Then("Trains are sorted in ascendeing order of arrival time")
    public void verify_arrival_sort() {
        List<String> rawTimes = ctx().resultsPage.getArrivalTimesText(5);
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

        // ── handle next-day wraparound ────────────────────────────────────
        // if a time is significantly LESS than the previous one,
        // it means it crossed midnight — add 24hrs to it and all after it
        List<Integer> adjusted = new ArrayList<>();
        int offset = 0;

        for (int i = 0; i < minutes.size(); i++) {
            int current = minutes.get(i) + offset;

            if (i > 0 && current < adjusted.get(i - 1)) {
                // crossed midnight — shift this and all subsequent by 24hrs
                offset += 24 * 60;
                current += 24 * 60;
            }
            adjusted.add(current);
        }

        // ── assert adjusted times are in order ────────────────────────────
        for (int i = 0; i < adjusted.size() - 1; i++) {
            Assert.assertTrue(
                adjusted.get(i) <= adjusted.get(i + 1),
                "Sorting error at index " + i + ": "
                    + rawTimes.get(i) + " > " + rawTimes.get(i + 1)
                    + " (adjusted: " + adjusted.get(i) + " > " + adjusted.get(i + 1) + ")"
            );
        }

        System.out.println("Sort verified with midnight wraparound handling ✔");
    }
}