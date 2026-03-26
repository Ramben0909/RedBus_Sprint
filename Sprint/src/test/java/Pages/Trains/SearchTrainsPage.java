package Pages.Trains;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SearchTrainsPage {

    public WebDriver driver;
    public WebDriverWait wait;

    @FindBy(id = "rails-search-widget-source")
    private WebElement src_wrapper;

    @FindBy(xpath = "//div[@data-field='dst']")
    private WebElement dst_wrapper;

    @FindBy(xpath = "//button[contains(@class,'primary') and @aria-label='Search Trains']")
    private WebElement btn_search;

    public SearchTrainsPage(WebDriver driver) {
        this.driver = driver;
        this.wait   = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
    }

    public void navigateTo(String url) {
        driver.get(url);
    }

    public void clickTrainTab() throws InterruptedException {
        driver.findElement(By.xpath("//span[contains(text(),'Train')]")).click();
        Thread.sleep(1500);
    }

    public void selectSource(String input, String targetName) throws InterruptedException {
        src_wrapper.click();
        Thread.sleep(1000);
        driver.findElement(By.id("srcDest")).sendKeys(input);
        Thread.sleep(2000);
        clickSuggestion("//div[@id='src-suggestions']//div[contains(@class,'listItem')]", targetName);
        Thread.sleep(1000);
    }

    public void selectDestination(String input, String targetName) throws InterruptedException {
        dst_wrapper.click();
        Thread.sleep(1000);
        driver.findElement(By.id("srcDest")).sendKeys(input);
        Thread.sleep(2000);
        clickSuggestion("//div[@id='dst-suggestions']//div[contains(@class,'listItem')]", targetName);
        Thread.sleep(1000);
    }

    public void clickSearch() {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn_search);
    }

    public boolean getInvalidDestinationSuggestions(String toInput) throws InterruptedException {
        dst_wrapper.click();
        Thread.sleep(1000);
        driver.findElement(By.id("srcDest")).sendKeys(toInput);

        String suggestionXpath = "//div[@id='dst-suggestions']//div[contains(@class,'listItem')]";
        String noResultXpath   = "//div[@id='dst-suggestions']//*[contains(text(),'No Results')]";
        long endTime = System.currentTimeMillis() + 5000;

        while (System.currentTimeMillis() < endTime) {
            if (!driver.findElements(By.xpath(noResultXpath)).isEmpty()
                    && driver.findElement(By.xpath(noResultXpath)).isDisplayed()) {
                return true;
            }
            if (!driver.findElements(By.xpath(suggestionXpath)).isEmpty()) {
                return false;
            }
            Thread.sleep(500);
        }
        return false;
    }

    public void openCalendar() throws InterruptedException {
        driver.findElement(By.tagName("body")).click();
        Thread.sleep(500);
        WebElement calendar = driver.findElement(
            By.xpath("//div[@data-field='date']//div[contains(@class,'dateInputWrapper')]"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calendar);
        Thread.sleep(1000);
    }

    public void selectTodayDate() throws InterruptedException {
        String todayDay = String.valueOf(java.time.LocalDate.now().getDayOfMonth() + 2);
        String dateXpath = "//li[contains(@class,'dateItem')]//div//div//span";
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(dateXpath)));

        for (WebElement date : driver.findElements(By.xpath(dateXpath))) {
            if (date.getText().trim().equals(todayDay) && date.isDisplayed()) {
                date.click();
                return;
            }
        }
        throw new RuntimeException("Today's date not found in calendar");
    }

    // ── private helpers ────────────────────────────────────────────────────────

    private void clickSuggestion(String containerXpath, String targetName) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(containerXpath), 0));
        for (WebElement el : driver.findElements(By.xpath(containerXpath))) {
            if (el.getText().trim().split("\n")[0].trim().equalsIgnoreCase(targetName)) {
                el.click();
                return;
            }
        }
        throw new RuntimeException("Suggestion not found: " + targetName);
    }
}