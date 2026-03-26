package Pages.pagesAP;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @FindBy(id = "srcinput")
    WebElement source;

    @FindBy(id = "destinput")
    WebElement destination;

    @FindBy(xpath = "//button[text()='Tomorrow']")
    WebElement tomorrow;

    public void openSite() {
        driver.get("https://www.redbus.in/");
    }

    public void search(String src, String dest) {

        source.clear();
        source.sendKeys(src);

        By suggestion = By.xpath("(//ul[contains(@class,'autoFill')]//li)[1]");
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion)).click();

        destination.clear();
        destination.sendKeys(dest);
        wait.until(ExpectedConditions.visibilityOfElementLocated(suggestion)).click();

        wait.until(ExpectedConditions.elementToBeClickable(tomorrow));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tomorrow);
    }
}