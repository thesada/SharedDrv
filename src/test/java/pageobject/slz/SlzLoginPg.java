package pageobject.slz;

import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import pageobject.ParentPage;
import stepdefination.SharedDriver;

public class SlzLoginPg extends ParentPage {
	WebDriver driver;
	private String launchUrl = null;
	@FindBy(how = How.ID_OR_NAME, using = "username")
	private WebElement usernameTextBox;

	@FindBy(how = How.ID_OR_NAME, using = "password")
	private WebElement passwordTextBox;

	@FindBy(how = How.XPATH, using = "//input[@value='Login']")
	private WebElement login;

	@FindBy(xpath = "//a[text()='Logout']")
	private WebElement logoutLink;

	@FindBy(how = How.CSS, using = "p.warning")
	private WebElement loginError;

	public SlzLoginPg(WebDriver driver) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
	}

	public String getURL() {
		return launchUrl;
	}

	public void launchMe() {
		launchUrl = SharedDriver.appConfig.getProperty("app.url.full");
		this.reportLog("Launch Slz Login Page..");
		this.reportLog("URL: "+ launchUrl);
		Set<String> handles = driver.getWindowHandles();
		
		if (launchUrl != null) {
			driver.get(launchUrl);
		}
		//this 
		if (handles.size() > 1) {
			this.logout();
		}
	}

	public void logout() {
		if (this.isVisible(logoutLink)) {
			this.reportLog("Logout from Slz Home");
			this.click(logoutLink);
		}
	}

	public SlzHomePg login(String username, String password) {
		this.type(usernameTextBox, username);
		this.type(passwordTextBox, password);
		passwordTextBox.submit();
		this.reportLog("Login to Scholastic Learning Zone");
		return new SlzHomePg(driver);
	}

	public String getLoginErrorText() {
		return loginError.getText();
	}

}
