package Pages.Trains;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ResultsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

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
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(RESULT_XPATH), 0));
        List<WebElement> trains = driver.findElements(By.xpath(RESULT_XPATH));
        List<String> times = new java.util.ArrayList<>();
        for (int i = 0; i < Math.min(limit, trains.size()); i++) {
            WebElement el = trains.get(i).findElement(
                By.xpath(".//div[contains(@class,'arrival')]//span[contains(@class,'time')]"));
            times.add(el.getText().trim());
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