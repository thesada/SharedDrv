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

public class MyResultsPg extends ParentPage {
	WebDriver driver;
	
	@FindBy(xpath = "//div[@class='myResultsTabContentHeader']/div[text()='Date']")
	private WebElement dateSortLink;

	

	public MyResultsPg(WebDriver driver, LitProUserType userType) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
	}

	public void sortByDate(){
		this.reportLog("Sort by Date");
		this.click(dateSortLink);
	}
	public QuizResult getQuizResultForBook(String bookTitle){
		QuizResult result =null;
		this.sortByDate();
		String bookXpath = "//div[div[contains(@class,'title') and text()=\"" + bookTitle + "\"]]";
		 WebElement  children = this.getElement(By.xpath(bookXpath));
		if(children!=null){
			result = new QuizResult();
			result.bookTitle = bookTitle;
			result.percentage = this.getChildElement(children, By.xpath("./div[5]")).getText();
			result.quizDate = this.getChildElement(children, By.xpath("./div[7]")).getText();
			this.reportLog("Quiz Result: "+ result.toString());
		}
		return result;
	}
	
	public class QuizResult{
		public String bookTitle = "";
		public String author = "";
		public String percentage="";
		public String points = "";
		public String quizDate = "";
		
		@Override
		 public String toString(){
			return "{BookTitle:"+bookTitle+", Author:"+ author +",%:"+ percentage+",Points:"+ points +",Date:"+ quizDate +"}";
		}
	}
	
}

