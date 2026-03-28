package Pages.Trains;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ResultsPage {

    private final WebDriver driver;
    public final WebDriverWait wait;

    private static final String CARD_XPATH = "//div[contains(@class,'srpCardWrap')]";
    private static final String RESULT_XPATH = "//div[contains(@id,'Normal')]";

    public ResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public boolean areResultsDisplayed() {
        return !driver.findElements(By.xpath(RESULT_XPATH)).isEmpty();
    }

    public int getResultsCount() {
        return driver.findElements(By.xpath(RESULT_XPATH)).size();
    }

    public void clickFirstAvailableTrain() {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(CARD_XPATH), 0));

        for (WebElement card : driver.findElements(By.xpath(CARD_XPATH))) {
            List<WebElement> notAvailable = card.findElements(
                By.xpath(".//span[contains(@class,'availability') and contains(text(),'NOT AVAILABLE')]"));

            if (notAvailable.isEmpty()) {
                WebElement clickable = card.findElement(By.xpath(".//div[@role='button']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", clickable);
                return;
            }
        }
        throw new RuntimeException("No available train found");
    }

    public boolean isFreeCancellationDisplayed() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//h3[contains(text(),'Free cancellation')]")));
        return driver.findElement(By.xpath("//h3[contains(text(),'Free cancellation')]")).isDisplayed();
    }

    public String getSelectedDateText() {
        return driver.findElement(By.xpath("//div[@data-field='date']//div")).getText();
    }

    public List<String> getArrivalTimesText(int limit) {
        // wait for page to settle after sort click
        try { Thread.sleep(6000); } catch (InterruptedException e) { e.printStackTrace(); }

        // ← use longer wait specifically for post-sort rendering on Edge
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
        longWait.until(ExpectedConditions.numberOfElementsToBeMoreThan(
            By.xpath("//div[contains(@class,'srpCardWrap')]"), 0));

        // now try to find the result divs — try both possible xpaths
        List<WebElement> trains = driver.findElements(By.xpath("//div[contains(@id,'Normal')]"));

        if (trains.isEmpty()) {
            trains = driver.findElements(
                By.xpath("//div[contains(@class,'srpCardWrap')]"));
            System.out.println("Using fallback xpath, found: " + trains.size());
        }

        List<String> times = new java.util.ArrayList<>();

        for (int i = 0; i < Math.min(limit, trains.size()); i++) {
            try {
                WebElement el = trains.get(i).findElement(
                    By.xpath(".//div[contains(@class,'arrival')]//span[contains(@class,'time')]"));
                String time = el.getText().trim();
                if (!time.isEmpty()) {
                    times.add(time);
                    System.out.println("Arrival time " + (i+1) + ": " + time);
                }
            } catch (Exception e) {
                System.out.println("Skipping card " + (i+1) + " — no arrival time found");
            }
        }

        if (times.isEmpty()) {
            throw new RuntimeException(
                "No arrival times found after sort — check arrival time xpath");
        }

        return times;
    }

    public List<String> getJourneyClasses(int limit) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(CARD_XPATH), 0));
        List<WebElement> cards = driver.findElements(By.xpath(CARD_XPATH));
        List<String> classes = new java.util.ArrayList<>();

        for (int i = 0; i < Math.min(limit, cards.size()); i++) {
            WebElement card = cards.get(i);
            List<WebElement> notAvail = card.findElements(
                By.xpath(".//span[contains(text(),'NOT AVAILABLE')]"));
            if (!notAvail.isEmpty()) continue;

            List<WebElement> classEl = card.findElements(
                By.xpath(".//span[contains(@class,'journeyClass')]"));
            if (!classEl.isEmpty()) {
                classes.add(classEl.get(0).getText().trim());
            }
        }
        return classes;
    }
}