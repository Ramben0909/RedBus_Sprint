package Hooks.Hooks_SDC;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class Hooks {

    // ThreadLocal ensures thread safety for parallel execution
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();


    // Getter to access the driver in step definitions
    public static WebDriver getDriver() {
        return driver.get();
    }

    @Before
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_setting_values.notifications", 2);
        options.setExperimentalOption("prefs", prefs);

        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().maximize();
        
        // Set the driver to the current thread
        driver.set(webDriver);
        System.out.println("=== Browser Launched on Thread ===");
    }

    @After
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove(); // Essential to prevent memory leaks
            System.out.println("=== Browser Closed on Thread ===");
        }
    }
}