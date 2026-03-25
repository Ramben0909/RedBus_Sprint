package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import java.util.List;

public class SeatMapPage {

    WebDriver driver;

    public SeatMapPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class,'seat') and contains(@class,'available')]")
    List<WebElement> availableSeats;

    @FindBy(xpath = "//span[contains(@class,'fare')]")
    WebElement fare;

    @FindBy(xpath = "//div[contains(text(),'maximum')]")
    WebElement maxMsg;

    public void selectSeat(String seatNo) {
        driver.findElement(By.xpath("//div[@data-seat-no='" + seatNo + "']")).click();
    }

    public void deselectSeat(String seatNo) {
        driver.findElement(By.xpath("//div[@data-seat-no='" + seatNo + "']")).click();
    }

    public void selectSeatByIndex(int i) {
        availableSeats.get(i).click();
    }

    public int getAvailableSeatsCount() {
        return availableSeats.size();
    }

    public String getFare() {
        return fare.getText();
    }

    public String getMaxLimitMsg() {
        return maxMsg.getText();
    }

    public boolean isBookedSeatClickable() {
        try {
            driver.findElement(By.xpath("//div[contains(@class,'seat booked')]")).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}