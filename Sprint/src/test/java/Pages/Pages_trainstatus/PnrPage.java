package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;


public class PnrPage {

    WebDriver driver;

    // ── LOCATORS ─────────────────────────────────────────

    By pnrTab = By.xpath("(//div[@class='ris-selector'])[2]");
    By pnrInput = By.id("pnrInput");
    By checkStatusBtn = By.xpath("//button[contains(text(),'Check status')]");

    // Result / Validation
    By errorMessage = By.xpath("//span[contains(@class,'errorMsg')]");
    
    // ── CONSTRUCTOR ─────────────────────────────────────

    public PnrPage(WebDriver driver) {
        this.driver = driver;
    }

    // ── ACTION METHODS ──────────────────────────────────

    public void openPnrSection() {
        driver.findElement(pnrTab).click();
    }

    public void enterPnr(String pnr) {
        WebElement input = driver.findElement(pnrInput);
        input.clear();
        input.sendKeys(pnr);
    }

    public void clickCheckStatus() {
        driver.findElement(checkStatusBtn).click();
    }

    // ── VALIDATIONS ─────────────────────────────────────

    public boolean isPnrStatusPageOpened() {
        return driver.getCurrentUrl().contains("pnrStatusDetails");
    }

    public boolean isErrorMessageDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        By errorMessage = By.xpath("//span[contains(@class,'errorMsg')]");

        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).isDisplayed();
    }

    public boolean isCheckButtonEnabled() {
        return driver.findElement(checkStatusBtn).isEnabled();
    }

    public int getEnteredPnrLength() {
        return driver.findElement(pnrInput).getAttribute("value").length();
    }
}