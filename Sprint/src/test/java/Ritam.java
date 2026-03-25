import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Ritam {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("STEP 1: Launching Chrome...");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.get("https://www.redbus.in/");
        System.out.println("STEP 1: Done");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

        System.out.println("STEP 2: Clicking Train tab...");
        driver.findElement(By.xpath("//span[contains(text(),'Train')]")).click();
        Thread.sleep(1500);
        System.out.println("STEP 2: Done");

        // ─────────────────────────────────────────
        // FROM FIELD
        // ─────────────────────────────────────────
        System.out.println("STEP 3: Clicking FROM wrapper to reveal input...");
        driver.findElement(By.xpath("//div[@id='rails-search-widget-source']")).click();
        Thread.sleep(1000);
        WebElement srcInput = driver.findElement(By.id("srcDest"));
        srcInput.sendKeys("How");
        System.out.println("STEP 3: Done — typed 'How'");

        System.out.println("STEP 4: Waiting for FROM suggestions...");
        Thread.sleep(2000);
        String srcXpath = "//div[@id='src-suggestions']//div[contains(@class,'listItem')]";
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(srcXpath), 0));
        List<WebElement> srcSuggestions = driver.findElements(By.xpath(srcXpath));
        System.out.println("STEP 4: Found " + srcSuggestions.size() + " suggestions");

        String fromTarget = "Howrah";
        System.out.println("STEP 5: Clicking '" + fromTarget + "' (HWH)...");
        boolean fromFound = false;
        for (WebElement el : srcSuggestions) {
            String firstLine = el.getText().trim().split("\n")[0].trim();
            if (firstLine.equalsIgnoreCase(fromTarget)) {
                el.click();
                fromFound = true;
                System.out.println("STEP 5: Done — clicked '" + fromTarget + "'");
                break;
            }
        }
        if (!fromFound) {
            System.out.println("STEP 5: FAILED — not found.");
            driver.quit();
            return;
        }

        Thread.sleep(1000);

        // ─────────────────────────────────────────
        // TO FIELD
        // ─────────────────────────────────────────
        System.out.println("STEP 6: Clicking TO wrapper to reveal input...");
        driver.findElement(By.xpath("//div[@data-field='dst']")).click();
        Thread.sleep(1000);
        WebElement dstInput = driver.findElement(By.id("srcDest"));
        dstInput.sendKeys("New Delhi");
        System.out.println("STEP 6: Done — typed 'New Delhi'");

        System.out.println("STEP 7: Waiting for TO suggestions...");
        Thread.sleep(2000);
        String dstXpath = "//div[@id='dst-suggestions']//div[contains(@class,'listItem')]";
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(dstXpath), 0));
        List<WebElement> dstSuggestions = driver.findElements(By.xpath(dstXpath));
        System.out.println("STEP 7: Found " + dstSuggestions.size() + " suggestions");

        String toTarget = "New Delhi";
        System.out.println("STEP 8: Clicking '" + toTarget + "' (NDLS)...");
        boolean toFound = false;
        for (WebElement el : dstSuggestions) {
            String firstLine = el.getText().trim().split("\n")[0].trim();
            System.out.println("        >> " + firstLine);
            if (firstLine.equalsIgnoreCase(toTarget)) {
                el.click();
                toFound = true;
                System.out.println("STEP 8: Done — clicked '" + toTarget + "'");
                break;
            }
        }
        if (!toFound) {
            System.out.println("STEP 8: FAILED — not found.");
            driver.quit();
            return;
        }

        Thread.sleep(1000);
        
     // ─────────────────────────────────────────
     // CALENDAR FIX
     // ─────────────────────────────────────────
     System.out.println("STEP 9: Opening calendar...");

     // ✅ Click outside to close dropdown
     driver.findElement(By.tagName("body")).click();
     Thread.sleep(500);

     // ✅ Use JS click to avoid overlay issues
     WebElement calendar = driver.findElement(
         By.xpath("//div[@data-field='date']//div[contains(@class,'dateInputWrapper')]")
     );

     ((org.openqa.selenium.JavascriptExecutor) driver)
         .executeScript("arguments[0].click();", calendar);

     Thread.sleep(1000);

     System.out.println("Calendar opened");

     // ─────────────────────────────────────────
     // SELECT TODAY
     // ─────────────────────────────────────────
     String todayDay = String.valueOf(java.time.LocalDate.now().getDayOfMonth()+2);
     System.out.println("Today's date: " + todayDay);

     String dateXpath = "//li[contains(@class,'dateItem')]//div//div//span";

     wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(dateXpath)));

     List<WebElement> dates = driver.findElements(By.xpath(dateXpath));

     boolean found = false;

     for (WebElement date : dates) {
         String day = date.getText().trim();

         if (day.equals(todayDay) && date.isDisplayed()) {
             date.click();
             System.out.println("Clicked today's date: " + day);
             found = true;
             break;
         }
     }

     if (!found) {
         System.out.println("Today's date not found!");
     }
     
     
  // ─────────────────────────────────────────
  // CLICK INPUT FIELD (ID SWITCH)
  // ─────────────────────────────────────────
  driver.findElement(By.id("switch")).click();
  Thread.sleep(500);

  // ─────────────────────────────────────────
  // CLICK SEARCH TRAINS
  // ─────────────────────────────────────────
  WebElement searchBtn = driver.findElement(
      By.xpath("//button[contains(@class,'primary') and @aria-label='Search Trains']")
  );

  ((org.openqa.selenium.JavascriptExecutor) driver)
      .executeScript("arguments[0].click();", searchBtn);

  System.out.println("Clicked Search Trains");

//─────────────────────────────────────────
//STEP 1: CLICK ARRIVAL SORT BUTTON
//─────────────────────────────────────────
System.out.println("STEP: Clicking Arrival sort...");

WebElement arrivalBtn = wait.until(
   ExpectedConditions.presenceOfElementLocated(
       By.xpath("//button[contains(@aria-label,'Arrival toggle button')]")
   )
);

((org.openqa.selenium.JavascriptExecutor) driver)
   .executeScript("arguments[0].scrollIntoView(true);", arrivalBtn);

Thread.sleep(2000);


//─────────────────────────────────────────
//STEP 2: GET ONLY REAL TRAIN RESULTS (Normal)
//─────────────────────────────────────────
String trainXpath = "//div[contains(@id,'Normal')]";

wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath(trainXpath), 0));

List<WebElement> trains = driver.findElements(By.xpath(trainXpath));

System.out.println("Total real trains found: " + trains.size());


//─────────────────────────────────────────
//STEP 3: EXTRACT ARRIVAL TIMES (FIRST 15)
//─────────────────────────────────────────
List<Integer> timesInMinutes = new ArrayList<>();

int limit = Math.min(15, trains.size());

for (int i = 0; i < limit; i++) {

   WebElement train = trains.get(i);

   // ✅ relative xpath for arrival time inside each train
   WebElement arrivalTimeEl = train.findElement(
       By.xpath(".//div[contains(@class,'arrival')]//span[contains(@class,'time')]")
   );

   String timeText = arrivalTimeEl.getText().trim();
   System.out.println("Train " + (i+1) + " Arrival: " + timeText);

   // ───── convert to minutes ─────
   String[] parts = timeText.split(" ");
   String[] hm = parts[0].split(":");

   int hour = Integer.parseInt(hm[0]);
   int minute = Integer.parseInt(hm[1]);
   String ampm = parts[1];

   if (ampm.equalsIgnoreCase("PM") && hour != 12) {
       hour += 12;
   } else if (ampm.equalsIgnoreCase("AM") && hour == 12) {
       hour = 0;
   }

   timesInMinutes.add(hour * 60 + minute);
}


//─────────────────────────────────────────
//STEP 4: VERIFY ASCENDING ORDER
//─────────────────────────────────────────
System.out.println("STEP: Verifying ascending order...");

boolean isSorted = true;

for (int i = 0; i < timesInMinutes.size() - 1; i++) {

   if (timesInMinutes.get(i) > timesInMinutes.get(i + 1)) {
       isSorted = false;
       System.out.println("❌ Sorting error at index " + i);
       break;
   }
}


//─────────────────────────────────────────
//FINAL RESULT
//─────────────────────────────────────────
if (isSorted) {
   System.out.println("✅ Arrival times sorted correctly (Normal trains only)");
} else {
   System.out.println("❌ Arrival sorting failed");
}
    }
}