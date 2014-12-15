package pageobject.litpro;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.pagefactory.ElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageobject.ParentPage;

public class AssessmentPg extends ParentPage {
	WebDriver driver;
	final String PAGE_TITLE = "Scholastic";
	@FindBy(xpath = "//button[contains(text(),'Get Started')]")
	private WebElement letsGetStartedBtn;

	@FindBy(xpath = "//div[@class='quizContentDiv']//div[contains(@class,'testIcon-0')]")
	private WebElement firstAnswer;

	@FindBy(xpath = "//div[contains(@class,'testIcon-1')]")
	private WebElement secAnswer;

	@FindBy(xpath = "//div[contains(@class,'testIcon-2')]")
	private WebElement thirdAnswer;

	@FindBy(xpath = "//div[contains(@class,'testIcon-3')]")
	private WebElement fourthAnswer;

	@FindBy(xpath = "//div[contains(@class,'testIcon-0-true')]")
	private WebElement firstAnswerSelected;
	
	@FindBy(xpath = "//div[@class='quizContentDiv']/div[@class='selected-true']")
	private WebElement selectedAnsText;
 
	@FindBy(xpath = "//div[@class='buttonArea']/button[contains(text(),'Next')]")
	private WebElement nextBtn;
	
	@FindBy(xpath = "//div[@class='sriQuizPage']/div[contains(@class, 'question')]")
	private WebElement questionText;

	@FindBy(xpath = "//div[contains(text(),'Congratulations')]")
	private WebElement congratsMessage;

	@FindBy(xpath = "//button[contains(text(),'Create Reading List')]")
	private WebElement creatingReadingListBtn;
	
	

	public AssessmentPg(WebDriver driver) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
	}

	public void startAssessment() {
		this.reportLog("Click 'Lets get started' '");
		this.click(letsGetStartedBtn);
	}

	public void completeAssessment() {
		while (!this.isTestOver()) {
			this.reportLog("Question: " + this.getText(questionText));
			this.answerRandomly();
			this.reportLog("Ans Selected: " + this.getText(selectedAnsText));
			this.click(this.nextBtn);
		}
	}

	private boolean isTestOver() {
		try{
			this.waitForNextQuestion();
			driver.findElement(By.xpath("//button[contains(text(),'Create Reading List')]"));
			return true;
		}
		catch(Exception e){}
		return false;
	}
	
	/*waits until there is no selected ans, that is new question*/
	private void waitForNextQuestion() {
		try {
			(new WebDriverWait(driver, 2)).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='quizContentDiv']/div[@class='selected-true']")));
		} catch (Exception e) {

		}
	}
	
	//select a random answer
	private void answerRandomly(){
		int random = (int)(Math.random() * 4);
		String xpath = "//div[@class='quizContentDiv']//div[contains(@class,'testIcon-"+random+"')]";
		
		WebElement answere = this.getElement(By.xpath(xpath));
		if(answere!=null){
			answere.click();
		}
		else{
		
			this.click(this.firstAnswer);
		}
	}
	public void clickCreateReadingList(){
		this.reportLog("Click 'Create My Reading list' Button");
		this.click(creatingReadingListBtn);
	}
	
	public void goToLpHomePg(){
		this.reportLog("Goto LitPro Home");
		this.click(creatingReadingListBtn);
	}
 
}
