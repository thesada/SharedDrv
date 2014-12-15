package pageobject.litpro;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import pageobject.ParentPage;
import pageobject.litpro.LitProHomePg.LitProUserType;

public class SettingsPg extends ParentPage {
	WebDriver driver;
	final String PAGE_TITLE = "Scholastic Literacy Pro";
	
	@FindBy(xpath = "//div[@id='main-content']//h2")
	private WebElement pgHeader;
	
	@FindBy(xpath = "//button[span[text()='Save']]")
	private WebElement saveButton;
	
	@FindBy(xpath = "//button[span[text()='Restore Defaults']]")
	private WebElement restoreDefaultButton;
	
	@FindBy(xpath = "//div[contains(@class,'notification-content')]/h3")
	private WebElement saveNotification;
	
	 
	public SettingsPg(WebDriver driver, LitProUserType userType) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver, DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
		this.lazyWait(10);
	}
 
	public String getExpectedTitle() {
		return PAGE_TITLE;
	}
	
	public String getPageHeader(){
		String header = this.getText(pgHeader);
		System.out.println("Get Page Header: "+ header);
		return header;
	}
	
	
	/*checks setting presence*/
	public boolean isSettingExist(String settingName){
		boolean flag = false;
		String controlXpath = "//label[span[contains(text(),'" + settingName + "')]]";
		By by = By.xpath(controlXpath);
		WebElement settingElement = this.getElement(by);
		
		if(settingElement!=null){
			flag = true;
			this.reportLog("Setting: '"+settingName+"' Displayed");
		}else{
			this.reportLog("Setting: '"+settingName+"' NOT Displayed");
		}
		return flag;
	}
	/*checks if setting is enabled*/
	public String isSettingEnabled(String settingName){
		String flag = "No";
		String controlXpath = "//label[span[contains(text(),'" + settingName + "')]]/input[@type='checkbox']";
		By by = By.xpath(controlXpath);
		WebElement settingElement = this.getElement(by);
		
		if(settingElement!=null){
			if(settingElement.isSelected()){
				this.reportLog("Setting: '"+settingName+"' Enabled");
				flag="Yes";
			}else{
				this.reportLog("Setting: '"+settingName+"' NOT Enabled");
			}
		} 
		return flag;
	}
	public String getSettingValue(String settingName){
		String flag =null;
		 
		String controlXpath = "//div[label[span[contains(text(),'"+settingName + "')]]]//input[@type='number']";
		By by = By.xpath(controlXpath);
		WebElement settingElement = this.getElement(by);
		
		if(settingElement!=null){
			flag = settingElement.getAttribute("value").trim();
			this.reportLog("Setting: '"+settingName+"' Get Value: " + flag);
		} 
		return flag;
	}
	
	public boolean setSettingValue(String settingName, String setValue){
		boolean flag =false;
		 
		String controlXpath = "//div[label[span[contains(text(),'"+settingName + "')]]]//input[@type='number']";
		By by = By.xpath(controlXpath);
		WebElement settingElement = this.getElement(by);
		
		if(settingElement!=null){
			this.reportLog("Setting: '"+settingName+"' Change Value: " + setValue);
			settingElement.clear();
			settingElement.sendKeys(setValue);
			flag = true;
		} 
		return flag;
	}
	
	
	/*changes the setting*/
	public boolean enableSetting(String settingName){
		boolean flag = false;
		String controlXpath = "//label[span[contains(text(),'" + settingName + "')]]/input[@type='checkbox']";
		By by = By.xpath(controlXpath);
		WebElement settingElement = this.getElement(by);
		
		if(settingElement!=null){
			if(!settingElement.isSelected()){
				this.reportLog("Setting: '"+settingName+"' Enabled");
				this.click(settingElement);
			}
			flag = true;
		}
		return flag;
	}
	/*changes the setting*/
	public boolean disableSetting(String settingName){
		boolean flag = false;
		String controlXpath = "//label[span[contains(text(),'" + settingName + "')]]/input[@type='checkbox']";
		By by = By.xpath(controlXpath);
		WebElement settingElement = this.getElement(by);
		
		if(settingElement!=null){
			if(settingElement.isSelected()){
				this.reportLog("Setting: '"+settingName+"' Disabled");
				this.click(settingElement);
			}
			flag = true;
		}
		return flag;
	}
	
	public void saveSettings(){
		this.click(this.saveButton);
		
	}
	public String getSaveNotification(){
		return this.getText(saveNotification).trim();
	}
	 


}
