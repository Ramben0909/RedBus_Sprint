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

        WebElement inputBox = driver.findElement(By.id("srcDest"));
        inputBox.sendKeys(input);
        Thread.sleep(2000);

        // wait for suggestions to appear
        String xpath = "//div[@id='src-suggestions']//div[contains(@class,'listItem')]";
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(xpath), 0));

        clickSuggestion(xpath, targetName);

        Thread.sleep(1000);
    }

    public void selectDestination(String input, String targetName) throws InterruptedException {

        dst_wrapper.click();

        WebElement inputBox = driver.findElement(By.id("srcDest"));
        inputBox.sendKeys(input);
        Thread.sleep(2000);

        String xpath = "//div[@id='dst-suggestions']//div[contains(@class,'listItem')]";

        // wait until suggestions appear
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(xpath), 0));

        clickSuggestion(xpath, targetName);

        Thread.sleep(1000); // you can keep this for now if needed
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

    public void selectDate(String dateStr) throws InterruptedException {
        // dateStr format: "today", "tomorrow", or "DD" like "28"
        int dayNumber;

        if (dateStr.equalsIgnoreCase("today")) {
            dayNumber = java.time.LocalDate.now().getDayOfMonth() + 2;
        } else if (dateStr.equalsIgnoreCase("tomorrow")) {
            dayNumber = java.time.LocalDate.now().getDayOfMonth() + 3;
        } else {
            dayNumber = Integer.parseInt(dateStr) + 2;
        }

        String day = String.valueOf(dayNumber);
        String dateXpath = "//li[contains(@class,'dateItem')]//div//div//span";
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(dateXpath)));

        for (WebElement date : driver.findElements(By.xpath(dateXpath))) {
            if (date.getText().trim().equals(day) && date.isDisplayed()) {
                date.click();
                System.out.println("Clicked date: " + day);
                return;
            }
        }
        throw new RuntimeException("Date not found in calendar: " + day);
    }

    // ── private helpers ────────────────────────────────────────────────────────

    private void clickSuggestion(String containerXpath, String targetName) {

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(containerXpath)));

        List<WebElement> suggestions = driver.findElements(By.xpath(containerXpath));

        for (WebElement el : suggestions) {
            String text = el.getText().toLowerCase();

            if (text.contains(targetName.toLowerCase())) {
                el.click();
                return;
            }
        }

        // 🔥 DEBUG PRINT (VERY IMPORTANT)
        System.out.println("Available suggestions:");
        for (WebElement el : suggestions) {
            System.out.println("→ " + el.getText());
        }

        throw new RuntimeException("Suggestion not found: " + targetName);
    }
}