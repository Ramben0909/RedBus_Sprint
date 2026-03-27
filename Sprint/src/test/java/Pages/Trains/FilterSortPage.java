package Pages.Trains;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class FilterSortPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public FilterSortPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void selectACFilter() throws InterruptedException {
        WebElement acFilter = wait.until(
            ExpectedConditions.elementToBeClickable(By.xpath("//div[text()='AC']")));
        acFilter.click();
        Thread.sleep(2000);
    }

    public void clearAllFilters() throws InterruptedException {
        WebElement clearBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Clear all filters']")));
        clearBtn.click();
        Thread.sleep(2000);
    }

    public void sortByArrivalTime() throws InterruptedException {
        WebElement arrivalBtn = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//button[.//text()[contains(.,'Arrival')]]")));
        arrivalBtn.click();
        Thread.sleep(2000);
    }
}