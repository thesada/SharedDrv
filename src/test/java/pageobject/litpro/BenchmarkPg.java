package pageobject.litpro;


import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import pageobject.ParentPage;
import pageobject.litpro.LitProHomePg.LitProUserType;

public class BenchmarkPg extends ParentPage {
	WebDriver driver;
	 
	@FindBy(xpath = "//h2[contains(text(), 'Benchmark Proficiency Bands for')]")
	 WebElement pgHeader;
	
	@FindBy(xpath = "//button[i[@class='img-btn-dwnarrow']]")
	 WebElement benchmarkDropDownBtn;
	
	@FindBy(xpath = "//ul[@class='dropdown-menu']/li")
	 List<WebElement> benchmarkItems;
	

	@FindBy(xpath = "//div[@id='cohortSearch']/input")
	 WebElement searchTxtBox;
	
	@FindBy(xpath = "//button[span[text()='Save']]")
	 WebElement saveBtn;
	
	@FindBy(xpath = "//div[contains(@class,'notification-content')]/h3")
	 WebElement saveNotification;
	
	@FindBy(xpath = "//div[@id='benchmarkTable']//thead//th")
	List<WebElement> benchmarkCategories;
	
	

	

	public BenchmarkPg(WebDriver driver, LitProUserType userType) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
	}

	public String getHeaderText(){
		return this.getText(pgHeader);
	}
	public void clickBenchmarkDropdown(){
		this.reportLog("Click Benchmark dropdown");
		this.click(this.benchmarkDropDownBtn);
	}
	 
	public List<String> getBenchmarkNames(){
		List<String> bnames = new ArrayList<String>();
		for(WebElement e: benchmarkItems){
			String str = this.getText(e);
			this.reportLog(str);
			bnames.add(str);
		}
		
		return bnames;
	}
	
	public boolean isBenchmarkCategoryDisplayed(String catName){
		 String xpath = "//div[@id='benchmarkTable']//thead//th[div/span[text()='"+catName+"']]";
		 WebElement benchmarkItem = this.getElement(By.xpath(xpath));
		 if(benchmarkItem!=null)
			 return true;
		 
		 return false;
	}
	
	public boolean selectBenachmarkAndSave(String benchmarkName){
		//String xpath = "//ul[@class='dropdown-menu']/li[a[text()='"+ benchmarkName +"']]";
		this.reportLog("Select Benchmark item from Dropdown");
		this.click(this.benchmarkDropDownBtn);
		this.reportLog("Click Benchmark Save button");
		this.click(this.saveBtn);
		
		//WebElement benchmarkItem = this.getElement(By.xpath(xpath));
		/*if(benchmarkItem!=null){
			this.click(benchmarkItem);
			this.click(this.saveBtn);
			return true;
		}*/
		
		return false;
	}
	
	public String getNotificationText(){
		String notiString = this.getText(saveNotification).trim();
		this.reportLog("Notification Text- "+ notiString);
		return notiString;
	}
}
