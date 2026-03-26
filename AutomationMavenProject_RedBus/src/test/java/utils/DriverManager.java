package utils;

import org.openqa.selenium.WebDriver;

public class DriverManager {

    // Each thread gets its own WebDriver — parallel safe
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    public static void setDriver(WebDriver driver) {
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    // Call this after driver.quit() to prevent memory leaks
    public static void removeDriver() {
        driverThreadLocal.remove();
    }
}