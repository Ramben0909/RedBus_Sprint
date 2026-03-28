package Hooks.Hooks_SDC;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import testRunner.Runner_SDC.TestRunner;

import java.util.HashMap;
import java.util.Map;

public class Hooks {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driver.get();
    }

    @Before
    public void setUp() {
        String browser = TestRunner.getBrowser();
        WebDriver webDriver = null;

        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--disable-notifications");
                Map<String, Object> chromePrefs = new HashMap<>();
                chromePrefs.put("profile.default_content_setting_values.notifications", 2);
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                webDriver = new ChromeDriver(chromeOptions);
                System.out.println("=== Chrome Browser Launched on Thread " + Thread.currentThread() + " ===");
                break;

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--disable-notifications");
                Map<String, Object> edgePrefs = new HashMap<>();
                edgePrefs.put("profile.default_content_setting_values.notifications", 2);
                edgeOptions.setExperimentalOption("prefs", edgePrefs);
                webDriver = new EdgeDriver(edgeOptions);
                System.out.println("=== Edge Browser Launched on Thread " + Thread.currentThread() + " ===");
                break;

            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--disable-notifications");
                webDriver = new FirefoxDriver(firefoxOptions);
                System.out.println("=== Firefox Browser Launched on Thread " + Thread.currentThread() + " ===");
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser + ". Supported: chrome, edge, firefox");
        }

        webDriver.manage().window().maximize();
        driver.set(webDriver);
    }

    @After
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
            System.out.println("=== Browser Closed on Thread ===");
        }
    }
}