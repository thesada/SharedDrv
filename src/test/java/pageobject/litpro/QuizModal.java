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

public class QuizModal extends ParentPage {
	WebDriver driver;
	final String PAGE_TITLE = "Scholastic";

	
	@FindBy(xpath = "//div[contains(@class,'available-quizzes-modal')]//h3[contains(text(),'Quizzes for')]")
	private WebElement quizModalHeader;
	
	@FindBy(xpath = "//div[contains(@class,'available-quizzes-modal')]//button[span[text()='Take Quiz']]")
	private WebElement takeQuizBtn;
	
	@FindBy(xpath = "//div[contains(@class,'available-quizzes-modal')]//button[i[@class='img-btn-close']]")
	 WebElement quizModalCloseBtn;
	

	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//div[contains(@class,'quizIcon-0')]")
	private WebElement firstAnswer;

	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//div[contains(@class,'quizIcon-1')]")
	private WebElement secAnswer;

	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//div[contains(@class,'quizIcon-2')]")
	private WebElement thirdAnswer;

	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//div[contains(@class,'quizIcon-3')]")
	private WebElement fourthAnswer;

	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//div[contains(@class,'quizIcon-0-true')]")
	private WebElement firstAnswerSelected;
	
	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//div[@class='answerrow-true-live']")
	private WebElement selectedAnsText;
 
	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//input[@value='Next']")
	private WebElement nextBtn;
	
	@FindBy(xpath = "//div[contains(@class,'SRCQuizView')]//div[contains(@class,'quizContentDiv')]/div[contains(@class, 'question')]")
	private WebElement questionText;
		
	@FindBy(xpath = "//div[@class='myCommentsModal']//input[@value='Done']")
	private WebElement doneBtn;
	
	@FindBy(xpath = "//input[@value='Finish']")
	private WebElement finishBtn;
	
	@FindBy(xpath = "//div[@class='myCommentsModal']/div[@class='scorerow']/div[1]")
	private WebElement scoreText;
	
	@FindBy(xpath = "//div[@class='myCommentsModal']/div[@class='scorerow']/div[2]")
	private WebElement scorePercentage;
	
	@FindBy(xpath = "//div[@class='myCommentsModal']/div[@class='ratingrow']//input[@value='Loved it!']")
	private WebElement ratingLovedIt;
	

	public QuizModal(WebDriver driver) {
		super(driver);
		ElementLocatorFactory finder = new AjaxElementLocatorFactory(driver,DRIVER_WAIT);
		PageFactory.initElements(finder, this);
		this.driver = driver;
		this.waitForPageLoad(DRIVER_WAIT);
	}

	public String getModalHeader(){
		return this.getText(quizModalHeader);
	}
	
	public void clickTakeQuiz(){
		this.reportLog("Click 'Take Quiz' button");
		this.click(this.takeQuizBtn);
	}
	public void completeQuiz() {
		this.reportLog("Attending quiz..");
		boolean lastQuestion = this.isQuizOver();
		boolean clickFinish = lastQuestion;
		
		while (!lastQuestion || clickFinish) {
			this.reportLog("Question: " + this.getText(questionText));
			this.answerRandomly();
			this.reportLog("Ans Selected: " + this.getText(selectedAnsText));
			if (clickFinish) {
				this.reportLog("Click 'Finish' Button");
				this.click(this.finishBtn);
				lastQuestion = true;
				clickFinish=false;
			} else {
				this.reportLog("Click 'Next' Button");
				this.click(this.nextBtn);
				lastQuestion = this.isQuizOver();
				clickFinish = lastQuestion;
			}
		}
 }
	
	private void answerRandomly(){
		int random = (int)(Math.random() * 4 );
		String xpath = "//div[contains(@class,'SRCQuizView')]//div[contains(@class,'quizIcon-" +random +"')]";
		WebElement answere = this.getElement(By.xpath(xpath));
		if(answere!=null){
			this.reportLog("Select Answer#"+ random);
			answere.click();
		}
		else{
			this.reportLog("Answer#"+ random +" NOT displayed, so selecting answer#1");
			this.click(this.firstAnswer);
		}
	}
	
	public String getQuizScore(){
		String scr = this.getText(scoreText);
		this.reportLog("Quiz Score: "+ scr);
		return scr;
	}
	
	public String getQuizPercentage(){
		String per = this.getText(scorePercentage);
		this.reportLog("Quiz Percentage: "+ per);
		return per;
	}
	
	public void clickDoneBtn(){
		this.reportLog("Click 'Done' Button");
		 this.click(this.doneBtn);
	}
	
	public boolean selectRating(){
		String xpath = "//div[@class='myCommentsModal']/div[@class='ratingrow']//input[@value='Loved it!']";
		WebElement lovedItButton = this.getElement(By.xpath(xpath));
		if(lovedItButton!=null){
			this.reportLog("Quiz Cleared Successfully");
			lovedItButton.click();
			return true;
		}
		return false;
	}
	

	private boolean isQuizOver() {
		try{
			this.waitForNextQuestion();
			driver.findElement(By.xpath("//input[@value='Finish']"));
			return true;
		}
		catch(Exception e){}
		return false;
	}
	private void waitForNextQuestion() {
		try {
			(new WebDriverWait(driver, 2))
					.until(ExpectedConditions.invisibilityOfElementLocated(By
							.xpath("//div[contains(@class,'SRCQuizView')]//div[@class='answerrow-true-live']")));
		} catch (Exception e) {

		}
	}
	
	 
 
}
