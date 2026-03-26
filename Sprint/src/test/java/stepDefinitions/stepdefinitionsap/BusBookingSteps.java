package stepdefinitions;

import base.BaseTest;
import pages.*;
import io.cucumber.java.en.*;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BusBookingSteps extends BaseTest {

    SeatMapPage seatPage;
    BoardingPage boardingPage;
    PassengerPage passengerPage;
    PaymentPage paymentPage;
    
    public static WebDriverWait wait;
    
    
    @Given("user opens redbus and logs in manually")
    public void open_login() throws InterruptedException {
        setup();
    }
    
    @When("user searches buses from Delhi to Mumbai")
    public void search() throws InterruptedException {

        String src = "Delhi";
        String dest = "Mumbai";
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@id='srcinput']")).sendKeys(src);

        // Destination
        driver.findElement(By.xpath("//input[@id='destinput']")).sendKeys(dest);

        // Date
        driver.findElement(By.xpath("//button[text()='Tomorrow']")).click();

        // Search
        driver.findElement(By.xpath("//button[contains(text(),'Search')]")).click();
    }
    @When("user selects a bus")
    public void select_bus() {
    	wait = new WebDriverWait(driver, Duration.ofSeconds(120));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[contains(@class,'bus-item')]")
        ));

        driver.findElement(By.xpath("(//div[contains(text(),'View Seats')])[1]")).click();

        seatPage = new SeatMapPage(driver);
        boardingPage = new BoardingPage(driver);
        passengerPage = new PassengerPage(driver);
        paymentPage = new PaymentPage(driver);
    }

    @Then("seat map is loaded")
    public void seat_map() {
        assert seatPage.getAvailableSeatsCount() > 0;
    }

    @Then("available seats should be clickable")
    public void available() {
        seatPage.selectSeat("38L");
    }

    @Then("booked seats should not be clickable")
    public void booked() {
        assert !seatPage.isBookedSeatClickable();
    }

    @When("user selects seat {string}")
    public void selectSeat(String s) {
        seatPage.selectSeat(s);
    }

    @Then("total fare should be updated")
    public void fare() {
        assert !seatPage.getFare().isEmpty();
    }

    @When("user deselects seat {string}")
    public void deselect(String s) {
        seatPage.deselectSeat(s);
    }

    @Then("fare should be reduced")
    public void reduced() {}

    @When("user selects 6 seats")
    public void select6() {
        for (int i = 0; i < 6; i++) {
            seatPage.selectSeatByIndex(i);
        }
    }

    @When("tries to select another seat")
    public void selectExtra() {
        seatPage.selectSeatByIndex(6);
    }

    @Then("warning message should appear")
    public void warning() {
        assert seatPage.getMaxLimitMsg().contains("maximum");
    }

    @When("user selects boarding point")
    public void boarding() {
        boardingPage.selectBoarding();
    }

    @When("user selects dropping point")
    public void dropping() {
        boardingPage.selectDropping();
    }

    @Then("both should appear in summary")
    public void summary() {
        assert boardingPage.getSelectedBoarding().length() > 0;
        assert boardingPage.getSelectedDropping().length() > 0;
    }

    @When("user enters valid passenger details")
    public void validPassenger() {
        passengerPage.enterPassenger("Rahul", "28");
        passengerPage.clickContinue();
    }

    @Then("user should proceed to payment")
    public void paymentNav() {}

    @When("user enters invalid data")
    public void invalidPassenger() {
        passengerPage.enterPassenger("123", "abc");
        passengerPage.clickContinue();
    }

    @Then("error message should be displayed")
    public void error() {
        assert !passengerPage.getError().isEmpty();
    }

    @When("user enters contact details")
    public void contact() {
        paymentPage.enterContact();
    }

    @When("selects UPI")
    public void upi() {
        paymentPage.selectUPI();
    }

    @Then("QR code should be displayed")
    public void qr() {
        assert paymentPage.isQRDisplayed();
        tearDown();
    }
}