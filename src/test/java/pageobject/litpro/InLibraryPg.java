package pageobject.litpro;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;

import pageobject.ParentPage;
import pageobject.litpro.LitProHomePg.LitProUserType;

public class InLibraryPg extends ParentPage{
	WebDriver driver;
	final String PAGE_TITLE = "Scholastic Literacy Pro";
	final String PAGE_HEADER = "Match Your Library Books";
	
	@FindBy(xpath = "//div[input[@type='file']]/input")
	private WebElement selectFileButton; 
	
	@FindBy(xpath = "//button[text()='Upload']")
	private WebElement uploadButton; 
	
	@FindBy(xpath = "//div[@id='heading']/h3")
	private WebElement pgHeader; 
	
	
	public InLibraryPg(WebDriver driver, LitProUserType userType) {
		super(driver);
		ElementLocatorFactory finder =  new AjaxElementLocatorFactory(driver, DRIVER_WAIT); 
		PageFactory.initElements(finder, this); 
		this.driver = driver; 
	}
	 
 
	public String getExpectedTitle() {
		return PAGE_TITLE;
	}
 
	public String getPageHeader() {
		return this.getText(pgHeader);
	}
	
	public void selectFile(){
		//this.sync(selectFileButton).sendKeys("D:/dev/LiteracyProAutomation/src/test/resources/upload_files/import_books.csv");
		//this.click(selectFileButton);
		WebElement we = this.driver.findElement(By.xpath("//input[@type='file']"));
				//we.sendKeys("D:/dev/LiteracyProAutomation/src/test/resources/upload_files/import_books.csv");
				
				String jscript = "var e=arguments[0];"
						+ " e.style.visibility = 'visible'";
				((JavascriptExecutor) driver).executeScript(jscript, we);
		try {
			Thread.sleep(10000);
			this.click(uploadButton);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			 e.printStackTrace();
		}
		
	}
}
