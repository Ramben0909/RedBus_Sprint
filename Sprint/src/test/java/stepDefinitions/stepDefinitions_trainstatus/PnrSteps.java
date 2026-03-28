package stepDefinitions.stepDefinitions_trainstatus;

import java.util.List;

import org.jcp.xml.dsig.internal.dom.Utils;
import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import Pages.Pages_trainstatus.PnrPage;
import DriverManager.DriverManager_trainstatus;
import Utils.ExcelUtils_trainstatus;

public class PnrSteps {

    // Page object (same pattern as TrainSteps)
    PnrPage pnrPage = new PnrPage(DriverManager_trainstatus.getDriver());

    // ── GIVEN ─────────────────────────────────────────────

    @Given("the PNR test data is loaded from Excel sheet {string}")
    public void loadPnrData(String sheetName) {

        List<String[]> rows = ExcelUtils_trainstatus.getSheetData(sheetName);

        System.out.println("\n===== PNR Excel Data =====");
        System.out.printf("%-15s %-10s %-30s%n", "PNR", "Type", "Description");
        System.out.println("-------------------------------------------------------");

        for (String[] row : rows) {
            System.out.printf("%-15s %-10s %-30s%n", row[0], row[1], row[2]);
        }

        Assert.assertTrue(rows.size() > 0, "Excel sheet is empty!");
    }
    
    @Given("the user is on the Train Running Status page")
    public void userIsOnTrainPage() {
        // Already handled by Hooks
        System.out.println("User is on Train Running Status page");
    }

    // ── WHEN ──────────────────────────────────────────────

    @When("the user navigates to PNR status section")
    public void openPnrSection() {
        pnrPage.openPnrSection();
    }

    @When("the user enters PNR number {string}")
    public void enterPnr(String pnr) {
        pnrPage.enterPnr(pnr);
    }

    @When("the user clicks on Check Status")
    public void clickCheckStatus() {
        pnrPage.clickCheckStatus();
    }

    // ── THEN ──────────────────────────────────────────────

    @Then("the PNR details page should be displayed")
    public void verifyPnrDetailsPage() {
        Assert.assertTrue(
                pnrPage.isPnrStatusPageOpened(),
                "FAIL: PNR details page did not open"
        );
    }

    @Then("an error message should be displayed for invalid PNR")
    public void verifyInvalidPnrError() {
        Assert.assertTrue(
                pnrPage.isErrorMessageDisplayed(),
                "FAIL: Error message not displayed for invalid PNR"
        );
    }

    @Then("the Check Status button should remain disabled")
    public void verifyButtonDisabled() {
        Assert.assertFalse(
                pnrPage.isCheckButtonEnabled(),
                "FAIL: Button should be disabled for invalid input"
        );
    }

    @Then("the PNR input should accept only 10 digits")
    public void verifyInputRestriction() {
        Assert.assertEquals(
                pnrPage.getEnteredPnrLength(),
                10,
                "FAIL: Input accepted more than 10 digits"
        );
    }
}