package Hooks.Hooks_trainstatus;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import DriverManager.DriverManager_trainstatus;

import java.time.Duration;

public class Hooks {
	@Before
	public void setUp() {

		String browser = System.getProperty("browser", "chrome"); // default chrome
		WebDriver driver;

		switch (browser.toLowerCase()) {

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver = new org.openqa.selenium.edge.EdgeDriver();
			break;

		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver = new org.openqa.selenium.firefox.FirefoxDriver();
			break;

		case "chrome":
		default:
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			break;
		}

		driver.manage().window().maximize();
		DriverManager_trainstatus.setDriver(driver);

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

		// Step 1 — RedBus home
		driver.get("https://www.redbus.in/");

		// Step 2 — Railways tab
		wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("(//a[@href='https://www.redbus.in/railways'])[1]")))
				.click();

		// Step 3 — Train Running Status chip
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//a[@class='risChips___04086b'])[2]"))).click();

		// Step 4 — Wait until search input is visible
		wait.until(ExpectedConditions
				.visibilityOfElementLocated(By.xpath("//input[@placeholder='Enter train name or number']")));
	}

	@After
	public void tearDown(Scenario scenario) {
		WebDriver driver = DriverManager_trainstatus.getDriver();
		if (driver != null) {
			driver.quit();
	        DriverManager_trainstatus.removeDriver();
		}
	}
}
