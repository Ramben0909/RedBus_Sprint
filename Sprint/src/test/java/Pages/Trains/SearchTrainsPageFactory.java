package Pages.Trains;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchTrainsPageFactory {

    public WebDriver driver;
    public WebDriverWait wait;

    // Shared inputs — set by step defs, used across classes
    public String fromInput;
    public String toInput;
    public boolean isFrom = false;

    // 🔹 Source wrapper div
    @FindBy(id = "rails-search-widget-source")
    private WebElement src_wrapper;

    // 🔹 Destination wrapper div
    @FindBy(xpath = "//div[@data-field='dst']")
    private WebElement dst_wrapper;

    // 🔹 Search button
    @FindBy(xpath = "//button[contains(@class,'primary') and @aria-label='Search Trains']")
    private WebElement btn_search;

    // 🔹 Results
    @FindBy(xpath = "//div[contains(@id,'Normal')]")
    private List<WebElement> train_results;

    public SearchTrainsPageFactory(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        AjaxElementLocatorFactory factory = new AjaxElementLocatorFactory(driver, 10);
        PageFactory.initElements(factory, this);
    }

    // ================= PRIVATE UTILITIES =================

    private void waitForSuggestions(String containerXpath) {
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(containerXpath), 0));
    }

    private void clickMatchingSuggestion(String containerXpath, String targetName) {
        List<WebElement> suggestions = driver.findElements(By.xpath(containerXpath));
        System.out.println("Suggestions found: " + suggestions.size());
        for (WebElement el : suggestions) {
            String firstLine = el.getText().trim().split("\n")[0].trim();
            System.out.println("  >> " + firstLine);
            if (firstLine.equalsIgnoreCase(targetName)) {
                el.click();
                System.out.println("Clicked: " + firstLine);
                return;
            }
        }
        throw new RuntimeException("Suggestion not found: '" + targetName + "'");
    }

    // ================= ACTION METHODS =================

    public void selectSource(String input, String targetName) throws InterruptedException {
        src_wrapper.click();
        Thread.sleep(1000);
        WebElement srcInput = driver.findElement(By.id("srcDest"));
        srcInput.sendKeys(input);
        System.out.println("Typed '" + input + "' in FROM field");
        Thread.sleep(2000);
        String srcXpath = "//div[@id='src-suggestions']//div[contains(@class,'listItem')]";
        waitForSuggestions(srcXpath);
        clickMatchingSuggestion(srcXpath, targetName);
        Thread.sleep(1000);
    }

    public void selectDestination(String input, String targetName) throws InterruptedException {
        dst_wrapper.click();
        Thread.sleep(1000);
        WebElement dstInput = driver.findElement(By.id("srcDest"));
        dstInput.sendKeys(input);
        System.out.println("Typed '" + input + "' in TO field");
        Thread.sleep(2000);
        String dstXpath = "//div[@id='dst-suggestions']//div[contains(@class,'listItem')]";
        waitForSuggestions(dstXpath);
        clickMatchingSuggestion(dstXpath, targetName);
        Thread.sleep(1000);
    }

    public void clickSearch() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", btn_search);
        System.out.println("Clicked Search Trains button");
    }

    public boolean areResultsDisplayed() {
        return train_results.size() > 0;
    }

    public int getResultsCount() {
        return train_results.size();
    }

    // ================= INVALID DESTINATION CHECK =================

    public boolean getInvalidDestinationSuggestions() throws InterruptedException {
        dst_wrapper.click();
        Thread.sleep(1000);
        WebElement dstInput = driver.findElement(By.id("srcDest"));
        dstInput.sendKeys(toInput);
        System.out.println("Typed invalid destination: " + toInput);
 
        // Poll for up to 5 seconds — wait to see if any suggestions appear
        // If nothing shows in 5 seconds, it is truly empty
        System.out.println("Typed invalid destination: " + toInput);

        String suggestionXpath = "//div[@id='dst-suggestions']//div[contains(@class,'listItem')]";
        String noResultXpath = "//div[@id='dst-suggestions']//*[contains(text(),'No Results')]";

        long endTime = System.currentTimeMillis() + 5000;

        while (System.currentTimeMillis() < endTime) {

            List<WebElement> suggestions = driver.findElements(By.xpath(suggestionXpath));
            List<WebElement> noResult = driver.findElements(By.xpath(noResultXpath));

            // ✅ PASS condition (your requirement)
            if (noResult.size() > 0 && noResult.get(0).isDisplayed()) {
                System.out.println("No Results Found displayed → PASS");
                return true;
            }

            // ❌ FAIL condition
            if (suggestions.size() > 0) {
                System.out.println("Unexpected suggestions found → FAIL");
                return false;
            }

            Thread.sleep(500);
        }

        System.out.println("No response from dropdown → FAIL");
        return false;
    }
 // ================= DATE SELECTION =================

    public void openCalendar() throws InterruptedException {

        // ✅ Close dropdown overlay
        driver.findElement(By.tagName("body")).click();
        Thread.sleep(500);

        WebElement calendar = driver.findElement(
            By.xpath("//div[@data-field='date']//div[contains(@class,'dateInputWrapper')]")
        );

        // ✅ Use JS click (important fix)
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", calendar);

        Thread.sleep(1000);
    }
    public void selectTodayDate() throws InterruptedException {

        String todayDay = String.valueOf(java.time.LocalDate.now().getDayOfMonth()+2);
        System.out.println(todayDay);

        String dateXpath = "//li[contains(@class,'dateItem')]//div//div//span";
        Thread.sleep(2000);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(dateXpath)));

        List<WebElement> dates = driver.findElements(By.xpath(dateXpath));

        for (WebElement date : dates) {
            String day = date.getText().trim();

            if (day.equals(todayDay) && date.isDisplayed()) {
                date.click();
                System.out.println("Clicked today's date: " + day);
                return;
            }
        }

        throw new RuntimeException("Today's date not found in calendar");
    }
    
 // ================= TRAIN SELECTION =================

    public void clickFirstAvailableTrain() {

        String cardXpath = "//div[contains(@class,'srpCardWrap')]";

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(cardXpath), 0));

        List<WebElement> cards = driver.findElements(By.xpath(cardXpath));

        for (WebElement card : cards) {

            // ❌ Skip NOT AVAILABLE trains
            List<WebElement> notAvailable = card.findElements(
                By.xpath(".//span[contains(@class,'availability') and contains(text(),'NOT AVAILABLE')]")
            );

            if (notAvailable.isEmpty()) {

                // ✅ Click actual clickable element
                WebElement clickable = card.findElement(By.xpath(".//div[@role='button']"));

                ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", clickable);

                System.out.println("Clicked first AVAILABLE train");
                return;
            }
        }

        throw new RuntimeException("No available train found");
    }


    // ================= FREE CANCELLATION VERIFY =================

    public boolean isFreeCancellationDisplayed() {

        String fcXpath = "//h3[contains(text(),'Free cancellation')]";

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fcXpath)));

        return driver.findElement(By.xpath(fcXpath)).isDisplayed();
    }
}