package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import java.util.List;

public class BoardingPage {

    WebDriver driver;

    public BoardingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class,'boarding-point')]")
    List<WebElement> boarding;

    @FindBy(xpath = "//div[contains(@class,'dropping-point')]")
    List<WebElement> dropping;

    public void selectBoarding() {
        boarding.get(0).click();
    }

    public void selectDropping() {
        dropping.get(0).click();
    }

    public String getSelectedBoarding() {
        return driver.findElement(By.xpath("(//div[contains(@class,'selected')])[1]")).getText();
    }

    public String getSelectedDropping() {
        return driver.findElement(By.xpath("(//div[contains(@class,'selected')])[2]")).getText();
    }
}