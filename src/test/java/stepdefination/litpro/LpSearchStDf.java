package stepdefination.litpro;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
 



import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;
import pageobject.litpro.SearchPg;
import pageobject.litpro.SearchPg.ResultBook;
import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;

public class LpSearchStDf {
	SlzLoginPg slzLogin = null;
	SlzHomePg slzHome;
	LitProHomePg lpHome;
	SearchPg lpSearchPg;
	WebDriver driver;
	Scenario scenario;
	List<ResultBook> resultBooks;
	

	
	 @Before
	public void before(Scenario scenario) {
	    this.scenario = scenario;
	}
	 
	public LpSearchStDf(SharedDriver driver) {
		this.driver = driver;
	}

	 
	@Given("^I browse to Search Page as \"(.*?)\"$")
	public void i_browse_to_Search_Page_as(String userType, Map<String, String> creds) {
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));

		if (userType.equals("Student")) {
			lpHome = slzHome.launchLitPro(LitProUserType.STUDENT);
		} else if (userType.contains("Admin") || userType.contains("Teacher")) {
			lpHome = slzHome.launchLitPro(LitProUserType.TEACHER);
		}
		lpSearchPg = lpHome.goToSeachPage();
	}

	@Then("^I should see Search Page Header \"(.*?)\"$")
	public void i_should_see_Search_Page_Header(String expected) {
		String actual = lpSearchPg.getPageHeader();
		assertTrue("The expected Page Header was '" + expected + "' but actually was: " + actual, actual.equalsIgnoreCase(expected)); 
	}

	@When("^I search for books having Title \"(.*?)\"$")
	public void i_search_for_books_having_Title(String key) {
		lpSearchPg.typeKeyAndSearch(key);
	 }

	@Then("^Search results should show Book Titles that contain the word - \"(.*?)\"$")
	public void search_results_should_show_Book_Titles_that_contain_the_word(String key) {
		resultBooks = lpSearchPg.getSearchResultBooks();
		scenario.write("#of Books found: " + resultBooks.size() );
		if(!resultBooks.isEmpty()){
			for(ResultBook book : resultBooks){
				assertTrue("Book '" + book.title + "' does NOT contain the word " +  key, book.title.contains(key));
			}
		}
		else{
			scenario.write("No book found for the title - "+ key);
		}
	}

	@When("^I search for books having Title \"(.*?)\" with Quiz$")
	public void i_search_for_books_having_Title_with_Quiz(String key){
		lpHome.goToHomePage();
		lpSearchPg = lpHome.goToSeachPage();
		lpSearchPg.checkQuizzesOnly();
		lpSearchPg.typeKeyAndSearch(key);
	}

	@Then("^All books should have an associated Quiz$")
	public void all_books_should_have_an_associated_Quiz() {
		scenario.write("#of Books found: " + resultBooks.size() );
		if(!resultBooks.isEmpty()){
			for(ResultBook book : resultBooks){
				assertTrue("Book '" + book.title + "' does NOT contain Quiz, but showed in Search Result " , book.hasQuizButton);
			}
		}
		else{
			scenario.write("No book found for the title");
		}
	
	}

}
