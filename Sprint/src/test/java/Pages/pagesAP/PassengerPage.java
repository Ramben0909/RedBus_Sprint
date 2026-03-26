package Pages.pagesAP;

import org.openqa.selenium.*;

public class PassengerPage {

    WebDriver driver;

    public PassengerPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterDetails(String name, String age) {

        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Enter your Name']"));
        WebElement ageField = driver.findElement(By.xpath("//input[@placeholder='Enter Age']"));

        nameField.clear();
        ageField.clear();

        nameField.sendKeys(name);
        ageField.sendKeys(age);
    }

    public void clickContinue() {
        driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
    }
}