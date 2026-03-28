package Pages.pagesAP;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class PassengerPage {

    WebDriver driver;

    public PassengerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterName(String name) {
        WebElement nameField = driver.findElement(By.xpath("//input[@placeholder='Enter your Name']"));
        nameField.clear();
        nameField.sendKeys(name);
    }

    public void enterAge(String age) {
        WebElement ageField = driver.findElement(By.xpath("//input[@placeholder='Enter Age']"));
        ageField.clear();
        ageField.sendKeys(age);
    }

    public void clickContinue() {
        driver.findElement(By.xpath("//button[contains(text(),'Continue')]")).click();
    }
}