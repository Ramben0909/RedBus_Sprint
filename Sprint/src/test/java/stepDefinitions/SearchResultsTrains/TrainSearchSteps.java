package stepDefinitions.SearchResultsTrains;

import java.time.Duration;

import org.testng.Assert;

import Pages.Trains.SearchTrainsPageFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.en.*;

public class TrainSearchSteps {

    // Static so WrongDest can access the same instance
    public static SearchTrainsPageFactory page;

    @Given("User is on redRail homepage")
    public void user_is_on_homepage() {
        page = new SearchTrainsPageFactory(new ChromeDriver());
        page.driver.manage().window().maximize();
        page.driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        page.driver.get("https://www.redbus.in/");
        System.out.println("Opened redbus.in");
    }

    @When("User clicks on Train Tickets")
    public void user_clicks_train_tickets() throws InterruptedException {
        page.driver.findElement(By.xpath("//span[contains(text(),'Train')]")).click();
        Thread.sleep(1500);
        System.out.println("Clicked Train Tickets tab");
    }

    @When("User enters {string} in From field")
    public void user_enters_from(String from) {
        page.fromInput = from;
        page.isFrom = true;
    }

    @When("User enters {string} in To field")
    public void user_enters_to(String to) {
        page.toInput = to;
        page.isFrom = false;
    }

    @When("User selects {string} from suggestions")
    public void user_selects_from_suggestions(String targetName) throws InterruptedException {
        if (page.isFrom) {
            page.selectSource(page.fromInput, targetName);
        } else {
            page.selectDestination(page.toInput, targetName);
        }
    }

    @When("User clicks on Search Trains button")
    public void user_clicks_search() {
        page.clickSearch();
    }

    @Then("Train results should be displayed")
    public void verify_results() throws InterruptedException {
        Thread.sleep(3000);
        Assert.assertTrue(page.areResultsDisplayed(), "No train results were displayed!");
        System.out.println("Total results found: " + page.getResultsCount());
        page.driver.quit();
    }
}