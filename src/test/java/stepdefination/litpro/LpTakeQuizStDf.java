package stepdefination.litpro;

import static org.junit.Assert.assertTrue;

 







import java.util.Map;

import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import pageobject.litpro.AssessmentPg;
import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;
import pageobject.litpro.MyResultsPg;
import pageobject.litpro.MyResultsPg.QuizResult;
import pageobject.litpro.QuizModal;
import pageobject.litpro.ReportsPg;
import pageobject.litpro.SearchPg;
import pageobject.litpro.SettingsPg;
import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;

public class LpTakeQuizStDf {
	SlzLoginPg slzLogin = null;
	SlzHomePg slzHome;
	LitProHomePg lpHome;
	SettingsPg lpSettingsPg;
	AssessmentPg lpAssessmentPg;
	ReportsPg lpReportsPg;
	SearchPg lpSearchPg;
	QuizModal lpQuizModal;
	MyResultsPg lpMyResPg;
	WebDriver driver;
	Scenario scenario;
	QuizResult quizResult;
	String bookTitle="Mouse Tales";
	String score;
	String percentage;
	 
	public LpTakeQuizStDf(SharedDriver driver) {
		this.driver = driver;
	}

	@Before
	public void before(Scenario scenario){
		this.scenario = scenario;
	}
	 
	@Given("^As a student, I am on the search page showing results for word \"(.*?)\"$")
	public void i_am_on_the_search_page_showing_results_for_word(String key, Map<String, String> creds) throws Throwable {
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));
		lpHome = slzHome.launchLitPro(LitProUserType.STUDENT);
		lpSearchPg = lpHome.goToSeachPage();
		
		String actual = lpSearchPg.getPageHeader();
		String expected ="Search";
		assertTrue("The expected Search Page Header was '" + expected + "' but actually was: " + actual, actual.equalsIgnoreCase(expected)); 
		lpSearchPg.checkQuizzesOnly();
		lpSearchPg.typeKeyAndSearch(key);
	}

	@When("^I click Take Quiz button for a random book$")
	public void i_click_Take_Quiz_button_for_a_random_book() throws Throwable {
		bookTitle = lpSearchPg.clickRandomBookTakeQuizButton();
		assertTrue("No Books found", bookTitle.trim().length()>1); 
		scenario.write("Attended quiz for book: "+ bookTitle);
	}

	@Then("^I should see quiz popup with header text starting with \"(.*?)\"$")
	public void i_should_see_quiz_popup_with_header_text_starting_with(String header) throws Throwable {
		lpQuizModal = new QuizModal(driver);
		String expected = header;
		String actual = lpQuizModal.getModalHeader();
		assertTrue("The expected Header was '" + expected + "' but actually was: " + actual, actual.contains(expected));
	}

	@When("^I click Take Quiz button in Quiz Popup$")
	public void i_click_Take_Quiz_button_in_Quiz_Popup() throws Throwable {
		lpQuizModal.clickTakeQuiz();
	}

	@When("^I complete the quiz$")
	public void i_complete_the_quiz() throws Throwable {
		try{
			lpQuizModal.completeQuiz();
			}
		catch(Exception e){
				String exp = e.getMessage();
				assertTrue("Error while attending quiz. "+ exp, false);
			}
		
		if(lpQuizModal.selectRating()){
			scenario.write("Quiz Result: Pass");
		}else{
			scenario.write("Quiz Result: Fail");
		}
	}

	@Then("^I should see score and percentage$")
	public void i_should_see_score_and_percentage() throws Throwable {
		 score = lpQuizModal.getQuizScore();
		 percentage = lpQuizModal.getQuizPercentage();
		scenario.write("Score: "+ score + ". Percentage: "+ percentage);
		assertTrue("Score NOT displayed", score.trim().length()>0);
		assertTrue("Percentage NOT displayed", percentage.trim().length()>0);
		lpQuizModal.clickDoneBtn();
	}
	

@When("^I goto My Results page$")
public void i_goto_My_Results_page() throws Throwable {
	lpMyResPg = lpHome.goToMyResultsPage();
}

@Then("^I should see book details on which quiz was taken$")
public void i_should_see_book_details_on_which_quiz_was_taken() throws Throwable {
	 quizResult = lpMyResPg.getQuizResultForBook(bookTitle);
	assertTrue("Quiz results NOT displyed for the book: "+ bookTitle, quizResult!=null);
	
}

@Then("^Score and Quiz date should match$")
public void score_and_Quiz_date_should_match() throws Throwable {
	assertTrue("Quiz Percentage NOT matched. Expected: " + this.percentage + ". Actual:"+ quizResult.percentage , quizResult.percentage.equalsIgnoreCase(this.percentage));
}



	
}
