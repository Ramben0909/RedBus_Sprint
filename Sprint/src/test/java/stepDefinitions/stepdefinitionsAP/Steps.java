package stepDefinitions.stepdefinitionsAP;

import Driver.DriverAP.*;
import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import Pages.pagesAP.*;

public class Steps {

    WebDriver driver = DriverManager.getDriver();

    HomePage home = new HomePage(driver);
    BusPage bus;
    SeatPage seat;
    PassengerPage passenger;
    PaymentPage payment;

    @Given("user opens redbus and logs in manually")
    public void open() {
        home.openSite();
    }

    @When("user searches buses from {string} to {string}")
    public void search(String src, String dest) {
        home.search(src, dest);
    }

    @And("user selects a bus")
    public void selectBus() throws Exception {
        bus = new BusPage(driver);
        bus.selectBus();

        seat = new SeatPage(driver);
        passenger = new PassengerPage(driver);
        payment = new PaymentPage(driver);
    }

    @When("user selects seat {string}")
    public void selectSeat(String s) {
        seat.selectSeat(s);
    }

    @When("user selects 6 seats")
    public void selectSix() {
        seat.selectSixSeats();
    }

    @And("tries to select another seat")
    public void tryExtra() {
        seat.selectSeat("37L");
    }

    @Then("warning message should appear")
    public void warning() {
        seat.clickOkay();
    }

    @When("user selects boarding point")
    public void board() {
        seat.selectBoardDrop();
    }

    @When("user enters valid passenger details")
    public void passenger() {
        passenger.enterDetails("TestUser","25");
        passenger.clickContinue();
    }

    @When("user enters invalid data")
    public void invalid() {
        passenger.enterDetails("","abc");
    }

    @When("user enters contact details")
    public void payment() throws Exception {
        payment.fillPayment();
    }

    @And("selects UPI")
    public void upi() {
        payment.selectUPI();
    }
}