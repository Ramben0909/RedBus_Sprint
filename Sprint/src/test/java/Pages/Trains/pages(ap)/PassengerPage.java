package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class PassengerPage {

    WebDriver driver;

    public PassengerPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@placeholder='Name']")
    WebElement name;

    @FindBy(xpath = "//input[@placeholder='Age']")
    WebElement age;

    @FindBy(xpath = "//label[text()='Male']")
    WebElement male;

    @FindBy(xpath = "//button[contains(text(),'Continue')]")
    WebElement cont;

    @FindBy(xpath = "//span[contains(@class,'error')]")
    WebElement error;

    public void enterPassenger(String n, String a) {
        name.clear();
        name.sendKeys(n);
        age.clear();
        age.sendKeys(a);
        male.click();
    }

    public void clickContinue() {
        cont.click();
    }

    public String getError() {
        return error.getText();
    }
}