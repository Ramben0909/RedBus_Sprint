package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.DriverManager;

import java.time.Duration;

public class Hooks {

    /**
     * Runs before EVERY scenario.
     * Opens the browser, lands on the Train Running Status page,
     * and confirms the search input is ready — then hands control to the test.
     */
    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        DriverManager.setDriver(driver);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        // Step 1 — RedBus home
        driver.get("https://www.redbus.in/");

        // Step 2 — Railways tab
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//a[@href='https://www.redbus.in/railways'])[1]")))
            .click();

        // Step 3 — Train Running Status chip
        wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("(//a[@class='risChips___04086b'])[2]")))
            .click();

        // Step 4 — Wait until search input is visible; test can now begin
        wait.until(ExpectedConditions.visibilityOfElementLocated(
            By.xpath("//input[@placeholder='Enter train name or number']")));
    }

    /** Runs after EVERY scenario — closes the browser cleanly. */
    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();
        if (driver != null) {
            driver.quit();
        }
    }
}
