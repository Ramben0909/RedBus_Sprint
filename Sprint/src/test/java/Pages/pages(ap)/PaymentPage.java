package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;

public class PaymentPage {

    WebDriver driver;

    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//input[@type='tel']")
    WebElement phone;

    @FindBy(xpath = "//input[@type='email']")
    WebElement email;

    @FindBy(xpath = "//span[contains(text(),'UPI')]")
    WebElement upi;

    @FindBy(xpath = "//img[contains(@src,'qr')]")
    WebElement qr;

    public void enterContact() {
        phone.sendKeys("1234567890");
        email.sendKeys("test@mail.com");
    }

    public void selectUPI() {
        upi.click();
    }

    public boolean isQRDisplayed() {
        return qr.isDisplayed();
    }
}