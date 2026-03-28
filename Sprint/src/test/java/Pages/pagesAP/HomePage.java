package Pages.pagesap;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;
    Robot robot;

    public HomePage(WebDriver driver) throws AWTException {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        robot = new Robot();
    }
    //Source
    @FindBy(xpath = "//input[@id='srcinput']")
    WebElement source;
    //Destination
    @FindBy(xpath = "//input[@id='destinput']")
    WebElement destination;
    //Date
    @FindBy(xpath = "//button[text()='Tomorrow']")
    WebElement tomorrowBtn;

    public void openSite() {
        driver.get("https://www.redbus.in/");
    }

    public void searchBus(String src, String dest) throws InterruptedException {
    	//Select first option
        source.sendKeys(src);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        //select first option
        destination.sendKeys(dest);
        Thread.sleep(2000);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);

        tomorrowBtn.click();
    }
}