package Pages.Pages_SDC;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RedBusSearchResultsPage {

    @SuppressWarnings("unused")
	private final WebDriver driver ;
    private final WebDriverWait wait;


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
        
            WebElement afternoon = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//div[@class=\"listText___33f80b subtitleText___0375d6\" and text()='"+time+"']")));
            afternoon.click();
        
    }

    public void clickFilter(String filterName) {
        
            WebElement filter = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='label___4d2f88 ' and contains(text(), '"+filterName+"')]")));
            filter.click();
        
        sleep(3000);
    }

    public void clickSortBy(String sort) {
        WebElement sortBy = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//div[contains(@class, 'sortType') and contains(text(), '"+sort+"')]")));
        sortBy.click();
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