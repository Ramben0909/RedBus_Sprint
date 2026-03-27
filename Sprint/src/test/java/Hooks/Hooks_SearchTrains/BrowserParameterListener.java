package Hooks;

import org.testng.ITestContext;
import org.testng.ITestListener;

public class BrowserParameterListener implements ITestListener {

    @Override
    public void onStart(ITestContext context) {
        String browser = context.getCurrentXmlTest()
                                .getParameter("browser");
        if (browser != null) {
            // store per thread instead of global system property
            DriverManager.setBrowserName(browser);
            System.out.println("Browser set to: " + browser);
        }
    }
}