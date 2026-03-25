package stepDefinitions.SearchResultsTrains;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;

public class ACfilter {

    public ACfilter() {}

    @And("User selects AC Filter")
    public void user_selects_ac_filter() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(
                TrainSearchSteps.page.driver, Duration.ofSeconds(15)
        );

        WebElement acFilter = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[text()='AC']")
                )
        );

        ((org.openqa.selenium.JavascriptExecutor) TrainSearchSteps.page.driver)
                .executeScript("arguments[0].click();", acFilter);

        Thread.sleep(2000);

        System.out.println("AC filter selected");
    }


    @Then("Only AC trains are visible should be displayed")
    public void verify_only_ac_trains() {

        WebDriverWait wait = new WebDriverWait(
                TrainSearchSteps.page.driver, Duration.ofSeconds(15)
        );

        String cardXpath = "//div[contains(@class,'srpCardWrap')]";

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(cardXpath), 0));

        List<WebElement> cards = TrainSearchSteps.page.driver.findElements(By.xpath(cardXpath));

        boolean allAC = true;

        for (int i = 0; i < Math.min(cards.size(), 20); i++) {

            WebElement card = cards.get(i);

            List<WebElement> notAvailable = card.findElements(
                    By.xpath(".//span[contains(text(),'NOT AVAILABLE')]")
            );

            if (!notAvailable.isEmpty()) continue;

            String trainClass = card.findElement(
                    By.xpath(".//span[contains(@class,'journeyClass')]")
            ).getText().trim();

            System.out.println("Class: " + trainClass);

            if (!(trainClass.contains("A") ||trainClass.contains("E") || trainClass.equalsIgnoreCase("CC"))) {
                allAC = false;
                break;
            }
        }

        Assert.assertTrue(allAC, "Non-AC train found! Filter failed");

        System.out.println("✅ AC Filter working correctly");

        TrainSearchSteps.page.driver.quit();
    }
}