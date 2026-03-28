package Pages.pagesAP;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;

public class SeatPage {

    WebDriver driver;
    WebDriverWait wait;

    public SeatPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void switchToSeatFrame() {
        driver.switchTo().defaultContent();
        try {
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(0));
        } catch (Exception e) {
            // seats are on main page
        }
    }

    private void dismissOverlayIfPresent() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                By.cssSelector("div.bottomSheetOverlay___2c4ff9")
            ));
        } catch (Exception e) {
            // no overlay, continue
        }
    }

    public void selectSeat(String seatId) {
        switchToSeatFrame();
        dismissOverlayIfPresent();
        WebElement seat = wait.until(
            ExpectedConditions.presenceOfElementLocated(
                By.xpath("//span[@id='" + seatId + "']")
            )
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", seat);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", seat);
    }

    public void clickOkay() {
        //stay inside iframe:popup is inside iframe too
        switchToSeatFrame();
        try {
            WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(
                    By.xpath("//button[@aria-label='Okay']")
                )
            );
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
        } catch (Exception e) {
            // popup may not have appeared, continue
            System.out.println("Okay button not found inside iframe: " + e.getMessage());
        }
        dismissOverlayIfPresent();
    }

    public void selectBoarding() throws InterruptedException {
        //boarding point is also inside iframe
        switchToSeatFrame();
        dismissOverlayIfPresent();
        WebElement boarding = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Board/')]")
            )
        );
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", boarding);
        
        Thread.sleep(2000);
        //boarding point
          
          WebElement firstBoarding = driver.findElement(
                  By.xpath("(//div[contains(@class,'bpdp')]//div[@role='radio'])[1]")
          );

          ((JavascriptExecutor) driver)
                  .executeScript("arguments[0].click();", firstBoarding);
          
          //dropping point
          
          WebElement dropping = wait.until(ExpectedConditions.presenceOfElementLocated(
                  By.xpath("(//div[contains(@class,'bpdp')]//div[@role='radiogroup'])[2]//div[@role='radio'][1]")
          ));

          ((JavascriptExecutor) driver)
                  .executeScript("arguments[0].click();", dropping);
          
          
          Thread.sleep(3000);
          driver.findElement(By.xpath("//span[contains(text(),'Passenger')]")).click();
          Thread.sleep(3000);
    }
}