package DriverManager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import java.time.Duration;

public class DriverManagerRB {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static volatile String browserName = "chrome";  

    public static void setBrowserName(String browser) {
        browserName = browser;
    }

    public static String getBrowserName() {
        return browserName;
    }

    public static void initDriver() {
        String browser = getBrowserName();
        System.out.println("Initializing browser: " + browser);
        WebDriver d;
        switch (browser.toLowerCase()) {
            case "edge":
                d = new EdgeDriver();
                break;
            case "firefox":
                FirefoxOptions options = new FirefoxOptions();
                options.addPreference("network.http.spdy.enabled", false);
                options.addPreference("network.http.spdy.enabled.http2", false);
                options.addPreference("network.http.spdy.enabled.v3-1", false);
                options.addPreference("network.http.spdy.enabled.deps", false);
                options.addPreference("security.mixed_content.block_active_content", false);
                options.addPreference("security.mixed_content.block_display_content", false);
                options.addPreference("security.tls.version.min", 1);
                options.addPreference("security.tls.version.max", 4);
                options.setAcceptInsecureCerts(true);
                d = new FirefoxDriver(options);
                break;
            case "chrome":
            default:
                d = new ChromeDriver();
                break;
        }
        d.manage().window().maximize();
        d.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.set(d);
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            driver.get().quit();
            driver.remove();
        }
    }
}