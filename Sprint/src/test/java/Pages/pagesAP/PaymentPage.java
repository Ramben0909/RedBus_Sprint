package Pages.pagesAP;

import org.openqa.selenium.*;

public class PaymentPage {

    WebDriver driver;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
    }

    public void fillPayment() throws InterruptedException {

        driver.findElement(By.xpath("//label[text()='Male']")).click();

        driver.findElement(By.xpath("//input[@placeholder='Phone']"))
                .sendKeys("9999999999");

        WebElement state = driver.findElement(By.xpath("//input[@autocomplete='address-level1']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", state);

        Thread.sleep(2000);

        driver.findElement(By.xpath("(//div[@role='radio'])[35]")).click();

        driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();

        driver.findElement(By.id("insuranceRejectText")).click();
        driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
    }

    public void selectUPI() {
        driver.findElement(By.xpath("//label[contains(@class,'customRadio')]")).click();
    }
}