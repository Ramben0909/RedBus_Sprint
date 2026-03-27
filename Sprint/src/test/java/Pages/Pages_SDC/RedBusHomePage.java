package Pages.Pages_SDC;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class RedBusHomePage {

	private final WebDriver driver;
	private final WebDriverWait wait;

	@FindBy(id = "srcinput")
	private WebElement srcInput;

	@FindBy(id = "destinput")
	private WebElement destInput;

	@FindBy(xpath = "//button[@aria-label='Search buses']")
	private WebElement searchButton;

	public RedBusHomePage(WebDriver driver) {
		this.driver = driver;
		this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
		PageFactory.initElements(driver, this);
	}

	public void openHomepage() {
		driver.get("https://www.redbus.in");
	}

	public void enterSource(String city) {
		WebElement src = wait.until(ExpectedConditions.elementToBeClickable(srcInput));
		src.clear();
		src.sendKeys(city);
		sleep(1500);
	}

	public void selectSourceSuggestion(String city) {
		String xpath = "//div[contains(text(),'" + city + "')]";
		WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		suggestion.click();
	}

	public void enterDestination(String city) {
		WebElement dest = wait.until(ExpectedConditions.elementToBeClickable(destInput));
		dest.clear();
		dest.sendKeys(city);
		sleep(1500);
	}

	public void selectDestinationSuggestion(String city) {
		String xpath = "//div[contains(text(),'" + city + "')]";
		WebElement suggestion = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		suggestion.click();
	}

	public void clickSearchButton() {
		WebElement search = wait.until(ExpectedConditions.elementToBeClickable(searchButton));
		search.click();
	}

	public void closePopupIfAppears() {
		try {
			Thread.sleep(2500);
			WebElement closeBtn = driver.findElement(By.xpath("//button[@aria-label='Close']"));
			closeBtn.click();
		} catch (Exception e) {
			// Popup did not appear
		}
	}

	public String getErrorMessageText() {
		WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("message___22fabd")));
		return error.getText().trim();
	}

	public void clickDateField() {
		WebElement dateWrapper = wait
				.until(ExpectedConditions.elementToBeClickable(By.className("dateInputWrapper___dfa43b")));
		dateWrapper.click();
	}

	public void selectDate(String day) {
		String xpath = "//span[text()='" + day + "']";
		WebElement dateEl = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		dateEl.click();
	}

	public void removeSource() {
		WebElement srcContainer = wait.until(
				ExpectedConditions.elementToBeClickable(By.xpath("(//div[@class=\"srcDestWrapper___9196c8 \"])[1]")));
		srcContainer.click();
		srcInput.click();
		WebElement cross = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@aria-label=\"Clear input\"]")));
		cross.click();
		closeAnyDropdown();
	}

	public boolean isSourceCleared() {
		WebElement srcContainer = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("(//div[@class=\"srcDestWrapper___9196c8 \"])[1]")));
		return srcContainer.getText().trim().isEmpty();
	}

	public void closeAnyDropdown() {
		driver.findElement(By.tagName("body")).sendKeys(Keys.ESCAPE);
		sleep(1000);
	}

	private void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}