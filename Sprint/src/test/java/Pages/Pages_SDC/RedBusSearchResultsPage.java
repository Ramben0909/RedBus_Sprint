package Pages.Pages_SDC;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RedBusSearchResultsPage {

    private final WebDriver driver ;
    private final WebDriverWait wait;

    @FindBy(xpath = "//div[@class='label___4d2f88 ' and contains(text(), 'AC')]")
    private WebElement acFilter;

    @FindBy(xpath = "//div[@class='label___4d2f88 ' and contains(text(), 'SEATER')]")
    private WebElement seaterFilter;

    public RedBusSearchResultsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        PageFactory.initElements(driver, this);
    }

    public void waitForPageLoad() {
        sleep(5000);
    }

    public void clickDTDropdown() {
        WebElement dt = wait.until(ExpectedConditions.elementToBeClickable(By.id("dt")));
        dt.click();
    }

    public void selectTimeFilter(String time) {
        if ("Afternoon".equals(time)) {
            WebElement afternoon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class=\"listText___33f80b subtitleText___0375d6\" and text()=\"Afternoon\"]")));
            afternoon.click();
        }
    }

    public void clickFilter(String filterName) {
        if ("AC".equals(filterName)) {
            WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(acFilter));
            filter.click();
        } else if ("SEATER".equals(filterName)) {
            WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(seaterFilter));
            filter.click();
        }
        sleep(3000);
    }

    public void clickSortByRatings() {
        WebElement ratings = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'sortType') and contains(text(), 'Ratings')]")));
        ratings.click();
        sleep(3000);
    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}