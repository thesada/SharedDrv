/**
 * 
 */
package pageobject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertTrue;

/**
 * @author Sadananda
 * 
 */
public abstract class ParentPage {
	public int DRIVER_WAIT = 60; // in seconds
	WebDriver driver;
	String windowHandle="";

	public ParentPage(WebDriver driver) {
		org.openqa.selenium.support.pagefactory.ElementLocatorFactory finder = new org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory(
				driver, DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.setWindowHandle();
	}
	
 
	private  void setWindowHandle(){
		if(driver!=null){
			Set<String> winHandles = driver.getWindowHandles();
			windowHandle = winHandles.toArray()[winHandles.size()-1].toString();
			String pStr = "Object/PageTitle/WinHandler: "+this.getClass().getName() +"/"+ driver.getTitle()+"/"+windowHandle;
			this.reportLog("Current active entities: \n"+ pStr);
			
			
		}
	}
	public  String getWindowHandle(){
		return this.windowHandle;
	}
	
	
	public WebElement sync(WebElement e) {
		(new WebDriverWait(driver, DRIVER_WAIT)).until(ExpectedConditions.visibilityOf(e));
		return e;
	}
	
	 
	public String getText(WebElement e) {
		return this.sync(e).getText().trim();
	}

	public void click(WebElement e) {
		this.sync(e).click();
	}
	public void type(WebElement e, String text) {
		this.sync(e).clear();
		e.sendKeys(text);
	}

	public boolean isVisible(WebElement e) {
		return this.sync(e).isDisplayed();
	}
	
	public WebElement getElement(final By by) {
		ExpectedCondition<WebElement> condition = new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, DRIVER_WAIT);
		try {
			return wait.until(condition);
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	public List<WebElement> getElements(final By by) {
		ExpectedCondition<List<WebElement>> condition = new ExpectedCondition<List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return driver.findElements(by);
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, DRIVER_WAIT);
		try {
			return wait.until(condition);
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	public WebElement getChildElement(final WebElement we, final By by) {
		sync(we);
		ExpectedCondition<WebElement> condition = new ExpectedCondition<WebElement>() {
			public WebElement apply(WebDriver driver) {
				return we.findElement(by);
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, DRIVER_WAIT);
		try {
			return wait.until(condition);
		} catch (TimeoutException e) {
			return null;
		}
	}

	public List<WebElement> getChildElements(final WebElement we, final By by) {
		sync(we);
		
		ExpectedCondition<List<WebElement>> condition = new ExpectedCondition<List<WebElement>>() {
			public List<WebElement> apply(WebDriver driver) {
				return we.findElements(by);
			}
		};

		Wait<WebDriver> wait = new WebDriverWait(driver, DRIVER_WAIT);
		try {
			return wait.until(condition);
		} catch (TimeoutException e) {
			return null;
		}
	}
	
	/*switches to new window, assuming current window is parent*/
	public boolean switchToNewWindow(){
		boolean flag=false;
		this.lazyWait(20);
		Set<String> winHandles = driver.getWindowHandles();
		//this.log("There were '"+winHandles.size()+"' browser windows");
		if(winHandles.size()>=1){
			String winHndl = winHandles.toArray()[winHandles.size()-1].toString();
			driver.switchTo().window(winHndl);
			driver.manage().window().maximize();
			this.reportLog("Driver switched to New window:'"+ winHndl +"'");
			flag=true;
		}
		return flag;
	}
	
	public String getAttribute(WebElement e, String attribue){
		return sync(e).getAttribute(attribue);
	}
	
	public void lazyWait(int sec){
		try {
			Thread.sleep(sec*1000);
		}
		catch (InterruptedException e) {}
	}
	
	public void waitForPageLoad(int sec){
		long startTime, endTime = 0;
		double duration = 0;
		String pageTtl;
		 this.reportLog("Waiting for Page load...");
		 
		 ExpectedCondition<Boolean> ex  = new ExpectedCondition<Boolean>() {
			   public Boolean apply(WebDriver driver) {
			    return ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete");
			   }
			  };
			  
	      startTime = System.nanoTime();
		  Wait<WebDriver> wait = new WebDriverWait(driver, sec);
		  endTime = System.nanoTime();
		  duration = (endTime - startTime)/ 1.0E09; 
		  try {
		   wait.until(ex);
		   pageTtl = driver.getTitle().trim();
		   pageTtl =  (pageTtl.length()>0)? pageTtl:"Blank Web Driver";
		   this.reportLog(pageTtl + " Page displayed (time took: ~" +String.valueOf(new DecimalFormat("##.00").format(duration))  + " secs)");
		  } 
		  catch (TimeoutException e) {
			  this.reportLog("Wait for page load timedout (waited: "+sec+" secs)");
			  assertTrue("Page load Timedout", false);
		  }
		    
		 }
	 
	public void reportLog(String msg){
		String methodName =Thread.currentThread().getStackTrace()[2].getMethodName();
		String timeNow = new SimpleDateFormat("hh:mm:ss a").format(new Date());
		System.out.println("["+timeNow +" - " + methodName + "]: " +msg);
	}
	void printActiveEnties(){
		
	}

}
