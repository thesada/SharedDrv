package pageobject.litpro;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import pageobject.ParentPage;

public class LitProHomePg extends ParentPage {
	WebDriver driver;

	final String PAGE_TITLE = "Scholastic Literacy Pro";

	public static enum LitProUserType {
		SCHOOL_ADMIN, STUDENT, CS_REP, TEACHER
	};

	private LitProUserType lpUserType;

	@FindBy(xpath = "//*[contains(text(),'Welcome,')]")
	private WebElement greetText;

	@FindBy(id = "navigation")
	private WebElement adminNavigationBar;

	@FindBy(xpath = "//div[div[@id='myResults']]")
	private WebElement studNavigationBar;

	@FindBy(xpath = "//a[contains(@href,'inlibrary')]")
	private WebElement inLibLink;

	@FindBy(xpath = "//a[span[contains(text(),'Search')]]")
	private WebElement searchTab;

	@FindBy(xpath = "//a[span[contains(text(),'Home')]]")
	private WebElement homeTab;
	
	@FindBy(xpath = "//a[span[contains(text(),'Settings')]]")
	private WebElement settingsTab;
	
	@FindBy(xpath = "//a[span[contains(text(),'LitPro Test')]]")
	private WebElement takeTesTab;
	
	@FindBy(xpath = "//a[span[contains(text(),'Reports')]]")
	private WebElement reportsTab;
	
	@FindBy(xpath = "//a[span[contains(text(),'My Results')]]")
	private WebElement myResultsTab;
	
	@FindBy(xpath = "//a[span[contains(text(),'Benchmarks')]]")
	private WebElement benchmarksTab;
	
	@FindBy(id = "takeTest")
	private WebElement takeTestDiv;
	
	@FindBy(xpath = "//div[@id='educatorHome']//h2")
	private WebElement educatorPgHeader;
	

	@FindBy(how = How.XPATH, using = "//a[contains(text(),'Logout')]")
	private WebElement logoutLink;

	public LitProHomePg(WebDriver driver, LitProUserType userType) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.lpUserType = userType;
		this.waitForPageLoad(DRIVER_WAIT);
	}
	
	
	 
	public String getExpectedTitle() {
		return PAGE_TITLE;
	}

	public String getUserGreetingText() {
		String rStr = this.getText(greetText);
		this.reportLog("Home Page Greeting text: " + rStr);
		return rStr;
	}

	/* returns comma separated tab names */
	public List<String> getDisplayedTabNames() {
		List<String> tabs = null;
		List<WebElement> tabList = null;

		if (lpUserType == LitProUserType.TEACHER) {
			tabList = this.getChildElements(adminNavigationBar,By.xpath(".//div"));
		} else if (lpUserType == LitProUserType.STUDENT) {
			tabList = this.getChildElements(studNavigationBar,By.xpath(".//div"));
		}

		if (tabList.size() > 0) {
			tabs = new ArrayList<String>();
			for (WebElement tab : tabList) {
				String tabName = this.getText(tab);
				if (!(tabName.isEmpty()) && (tabName != null))
					tabs.add(tabName);
			}
		}
		return tabs;
	}

	public boolean isLogoutLinkPresent() {
		return this.isVisible(logoutLink);
	}

	public InLibraryPg goToInLibraryPage() {
		this.click(this.inLibLink);
		return new InLibraryPg(driver, lpUserType);
	}

	public SearchPg goToSeachPage() {
		this.reportLog("Goto Search Page");
		this.click(this.searchTab);
		return new SearchPg(driver, lpUserType);
	}
	
	public SettingsPg goToSettingPage() {
		this.reportLog("Goto Settings Page");
		this.click(this.settingsTab);
		return new SettingsPg(driver, lpUserType);
	}
	
	public TestPg goToTakeTestPage() {
		this.reportLog("Goto Take Test Page");
		if(!this.getAttribute(takeTestDiv, "class").contains("disable")){
			this.click(this.takeTesTab);
			return new TestPg(driver, lpUserType);
		}
		return null;
	}

	public ReportsPg goToReportsPage() {
		this.reportLog("Goto Reports Page");
		 this.click(this.reportsTab);
			return new ReportsPg(driver, lpUserType);
	}
	
	public MyResultsPg goToMyResultsPage() {
		this.reportLog("Goto My Results Page");
		 this.click(this.myResultsTab);
		return new MyResultsPg(driver, lpUserType);
	}
	
	public BenchmarkPg goToBenchmarksPage() {
		this.reportLog("Goto Benchmark Page");
		 this.click(this.benchmarksTab);
		return new BenchmarkPg(driver, lpUserType);
	}
	
	public void goToHomePage() {
		this.reportLog("Goto LitPro Home Page");
		this.click(this.homeTab);
	}

	public void switchDriverToHomePg(){
		driver.switchTo().window(this.getWindowHandle());
		this.reportLog("Driver switched to LitPro Home Page - " + this.getWindowHandle());
	}
	
	public String getMetricValue(String metricName){
		String xpathString = "//div[@id='metrics']/div[div/span[text()='" + metricName +"']]//div[contains(@class,'metric-number')]";
		WebElement metricBox = this.getElement(By.xpath(xpathString));
		if(metricBox!=null){
			this.reportLog(metricName + " Value: ");
			return metricBox.getText();
		}
		return "";
	}
	
	public String getHeader(){
		return this.getText(educatorPgHeader).trim();
	}

}
