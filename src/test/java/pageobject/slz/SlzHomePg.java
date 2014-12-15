package pageobject.slz;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import pageobject.ParentPage;
import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;

public class SlzHomePg extends ParentPage {
	WebDriver driver;

	@FindBy(how = How.XPATH, using = "//p[contains(text(),'Hi ')]")
	private WebElement greetText;

	@FindBy(how = How.XPATH, using = "//a[contains(@href,'zone=LF')]")
	private WebElement lpProduct;

	public SlzHomePg(WebDriver driver) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
	}

	public String getUserGreetingText() {
		return this.getText(greetText);
	}

	public LitProHomePg launchLitPro(LitProUserType userType) {
		this.reportLog("Launch LitPro");
		this.click(lpProduct);
		this.switchToNewWindow();
		return new LitProHomePg(driver, userType);
	}
 
	 
}
