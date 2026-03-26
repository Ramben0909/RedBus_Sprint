package Pages.pagesAP;

import org.openqa.selenium.*;

public class BusPage {

    WebDriver driver;

    public BusPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectBus() throws InterruptedException {

        while (true) {
            try {
                driver.findElement(By.xpath("//div[text()='Raj Ratan Tours And Travels']")).click();
                break;
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,500)");
                Thread.sleep(1500);
            }
        }
    }
}