package Pages.pagesap;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class PaymentPage {

    WebDriver driver;
    WebDriverWait wait;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void selectGender() {
        driver.findElement(By.xpath("//label[text()='Male']")).click();
    }

    public void enterMobile(String number) {
        driver.findElement(By.xpath("//input[@placeholder='Phone']")).sendKeys(number);
    }

    public void selectState() throws InterruptedException {

        WebElement stateDropdown = driver.findElement(
                By.xpath("//input[@autocomplete='address-level1']")
        );

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", stateDropdown);
        Thread.sleep(2000);

        driver.findElement(
                By.xpath("(//div[@role=\"radiogroup\"]//div[@role='radio'])[35]")
        ).click();
    }

    public void clickContinue() {
        driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
    }

    public void rejectInsurance() {
        driver.findElement(By.xpath("//div[@id='insuranceRejectText']")).click();
    }

    public void selectUPI() throws InterruptedException {
    	Thread.sleep(10000);
        driver.findElement(By.xpath("//label[@class='customRadio___c29974 customRadio___c29974']")).click();
    }
}