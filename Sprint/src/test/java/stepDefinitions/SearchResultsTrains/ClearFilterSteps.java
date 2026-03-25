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

public class ClearFilterSteps {

    public ClearFilterSteps() {}

    @And("User clears all filters")
    public void user_clears_all_filters() throws InterruptedException {

        WebDriverWait wait = new WebDriverWait(
                TrainSearchSteps.page.driver, Duration.ofSeconds(15)
        );

        WebElement clearBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[@aria-label='Clear all filters']")
                )
        );

        ((org.openqa.selenium.JavascriptExecutor) TrainSearchSteps.page.driver)
                .executeScript("arguments[0].click();", clearBtn);

        Thread.sleep(2000);

        System.out.println("Cleared all filters");
    }


    @Then("Non-AC trains should be visible")
    public void verify_non_ac_trains() {

        WebDriverWait wait = new WebDriverWait(
                TrainSearchSteps.page.driver, Duration.ofSeconds(15)
        );

        String cardXpath = "//div[contains(@class,'srpCardWrap')]";

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(cardXpath), 0));

        List<WebElement> cards = TrainSearchSteps.page.driver.findElements(By.xpath(cardXpath));

        boolean foundSL = false;

        for (WebElement card : cards) {

            List<WebElement> classEl = card.findElements(
                    By.xpath(".//span[contains(@class,'journeyClass')]")
            );

            if (!classEl.isEmpty()) {

                String trainClass = classEl.get(0).getText().trim();
                System.out.println("Class: " + trainClass);

                if (trainClass.equalsIgnoreCase("SL")) {
                    foundSL = true;
                    break;
                }
            }
        }

        Assert.assertTrue(foundSL, "SL class not found! Clear filter failed");

        System.out.println("✅ Clear filter working (SL found)");

        TrainSearchSteps.page.driver.quit();
    }
}