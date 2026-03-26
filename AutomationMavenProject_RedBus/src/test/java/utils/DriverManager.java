package utils;

import org.openqa.selenium.WebDriver;

// This class holds the WebDriver so all files can share one browser instance
public class DriverManager {

    private static WebDriver driver;

    public static void setDriver(WebDriver d) {
        driver = d;
    }

    public static WebDriver getDriver() {
        return driver;
    }
}