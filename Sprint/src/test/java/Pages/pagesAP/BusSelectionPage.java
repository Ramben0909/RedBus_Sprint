package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.JavascriptExecutor;

public class BusSelectionPage {

    WebDriver driver;

    public BusSelectionPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
//User is on the page that lists buses:user selects bus
    public void selectBus() throws InterruptedException {

        while (true) {
            try {
                driver.findElement(By.xpath("//div[text()='Raj Ratan Tours And Travels']")).click();
                break;
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
                Thread.sleep(2000);
            }
        }
    }
}