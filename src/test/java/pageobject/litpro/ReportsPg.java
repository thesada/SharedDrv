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

public class ReportsPg extends ParentPage {
	WebDriver driver;
	 
	@FindBy(xpath = "//h3[contains(text(), 'Reports for')]")
	 WebElement pgHeader;
	
	@FindBy(xpath = "//div[contains(@class, 'modal-header')]//h3")
	 WebElement reportModalHeader;
	
	@FindBy(xpath = "//button[i[@class='img-btn-close']]")
	 WebElement reportModalCloseBtn;
	
	@FindBy(xpath = "//div[@id='cohortSearch']/input")
	 WebElement searchTxtBox;
	
	@FindBy(xpath = "//div[@id='cohortSearch']/button")
	 WebElement searchBtn;

	

	public ReportsPg(WebDriver driver, LitProUserType userType) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
	}

	public String getHeaderText(){
		return this.getText(pgHeader);
	}
	 
	public boolean isReportDisplayed(String reportName){
		
		String xpath = "//div[h4[text()='" + reportName +"']]";
		WebElement ReportTextElement = this.getElement(By.xpath(xpath));
		
		if(ReportTextElement!=null)
			return true;
		
		return false;
	}
	
	public void openReport(String reportName){
		String xpath = "//div[div[h4[text()='" + reportName +"']]]//button[text()='View Report']";
		WebElement ReportLink = this.getElement(By.xpath(xpath));
		this.click(ReportLink);
	}
	
	public String getReportModalHeaderText(){
		return this.getText(reportModalHeader);
	}
	
	public void closeReport(){
		System.out.println("Closing Report");
		this.click(this.reportModalCloseBtn);
	}

	public void searchStudentReportByUserName(String studentId){
		this.type(this.searchTxtBox, studentId);
		this.searchTxtBox.submit();
	}
	
	public String  getReportCardItemValue(String itemName){
		String xpath = "//div[contains(@class,'report-card-item') and .//span[text()='"+ itemName +"']]//span[contains(@class,'report-card-data')]";
		WebElement reportLink = this.getElement(By.xpath(xpath));
		
		if(reportLink!=null){
			String repItemValue = this.getText(reportLink);
			System.out.println("Report Item '"+ itemName +"' value: "+repItemValue);
			return repItemValue;
		}
		return "NULL";
	}
	
}
