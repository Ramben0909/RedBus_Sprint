package Pages.pagesAP;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class SeatPage {

    WebDriver driver;
    WebDriverWait wait;

    public SeatPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void selectSeat(String id) {
        By seat = By.xpath("//span[@id='" + id + "']");
        wait.until(ExpectedConditions.elementToBeClickable(seat)).click();
    }

    public void selectSixSeats() {
        String[] seats = {"30U","35U","36U","41U","42U","38L"};
        for(String s: seats) selectSeat(s);
    }

    public void clickOkay() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[@aria-label='Okay']")
        )).click();
    }

    public void selectBoardDrop() {

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[contains(text(),'Board/')]")
        )).click();

        driver.findElement(By.xpath("(//div[@role='radio'])[1]")).click();
        driver.findElement(By.xpath("(//div[@role='radiogroup'])[2]//div[@role='radio'][1]")).click();
    }
}