package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TrainPage {

	WebDriver driver;
	WebDriverWait wait;

	// ── WEB ELEMENTS ──────────────────────────────────────────────────────────

	@FindBy(xpath = "//input[@placeholder='Enter train name or number']")
	WebElement searchInput;

	// First autocomplete suggestion in the dropdown
	@FindBy(xpath = "(//div[@class='lts_solr_wrap'])[1]")
	WebElement firstSuggestion;

	@FindBy(xpath = "//button[contains(text(),'Check Status')]")
	WebElement checkStatusButton;

//    // First train row's availability button in the top-10 list
//    @FindBy(xpath = "(//button[@class='availability-button'])[1]")
//    WebElement availabilityButton;

	// Right arrow to go to next month in the calendar
	@FindBy(xpath = "//div[contains(@class,'DayNavigator__IconBlock')][last()]")
	WebElement nextMonthArrow;

//    // 3rd date tile — resolves to 15th in most months
//    @FindBy(xpath = "(//div[contains(@class,'DayTiles__CalendarDaysBlock')])[3]")
//    WebElement dateToSelect;

	// ── CONSTRUCTOR ───────────────────────────────────────────────────────────

	public TrainPage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		PageFactory.initElements(driver, this);
	}

	// ── ACTIONS ───────────────────────────────────────────────────────────────

	/**
	 * Valid search: type input → pick first suggestion → click Check Status. Used
	 * for TC_01 to TC_04.
	 */
	public void searchTrain(String input) {
		wait.until(ExpectedConditions.visibilityOf(searchInput));
		searchInput.clear();
		searchInput.sendKeys(input);

		wait.until(ExpectedConditions.visibilityOf(firstSuggestion));
		firstSuggestion.click();

		wait.until(ExpectedConditions.elementToBeClickable(checkStatusButton));
		checkStatusButton.click();
	}

	/**
	 * Invalid search: only types — no suggestion will appear, button stays
	 * disabled. Used for TC_07 and TC_08.
	 */
	public void typeOnly(String input) {
		wait.until(ExpectedConditions.visibilityOf(searchInput));
		searchInput.clear();
		searchInput.sendKeys(input);
		try {
			Thread.sleep(2500);
		} catch (InterruptedException ignored) {
		}
	}

	/**
	 * Clicks the availability button on the train details page. Scrolls into view
	 * first to avoid ElementClickInterceptedException.
	 */

	public void clickAvailabilityButton() {

		// Scroll to section
		((JavascriptExecutor) driver).executeScript("window.scrollBy(0,800)");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException ignored) {
		}

		By locator = By.xpath("//button[contains(text(),'Show Availability')]");

		WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

		// 🔥 SCROLL INTO CENTER (VERY IMPORTANT)
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ignored) {
		}

		// 🔥 CLICK USING JS (BYPASS NAVBAR ISSUE)
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
	}

	/** Clicks the right arrow to move to the next month in the calendar. */
	public void navigateToNextMonth() {
		wait.until(ExpectedConditions.elementToBeClickable(nextMonthArrow));
		nextMonthArrow.click();
	}

	/** Selects the 3rd date tile (≈ 15th) in the currently shown month. */
	public void selectDate() {

		// 🔥 Wait for ANY calendar date to appear (not container)
		By dateLocator = By.xpath("//span[text()='15']");

		WebElement date = wait.until(ExpectedConditions.visibilityOfElementLocated(dateLocator));

		// Scroll to center (avoid overlay)
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", date);

		try {
			Thread.sleep(500);
		} catch (InterruptedException ignored) {
		}

		// JS click (important for modal)
		((JavascriptExecutor) driver).executeScript("arguments[0].click();", date);
	}

	// ── VERIFICATIONS ─────────────────────────────────────────────────────────

	/** Confirms navigation to the liveTrainDetails page after a valid search. */
	public boolean isTrainDetailsPageOpened() {
		wait.until(ExpectedConditions.urlContains("liveTrainDetails"));
		return driver.getCurrentUrl().contains("liveTrainDetails");
	}

	/**
	 * Returns true when the Check Status button is disabled. Covers both the HTML
	 * 'disabled' attribute and CSS 'disabled' class name.
	 */
	public boolean isCheckStatusButtonDisabled() {
		String disabled = checkStatusButton.getAttribute("disabled");
		String classes = checkStatusButton.getAttribute("class");
		return disabled != null || (classes != null && classes.contains("disabled"));
	}

	/**
	 * Confirms the seat availability result page has opened after date selection.
	 */
	public boolean isAvailabilityResultPageOpened() {
		String url = driver.getCurrentUrl();
		return url.contains("railways/search") && url.contains("doj="); // date present
	}
}
