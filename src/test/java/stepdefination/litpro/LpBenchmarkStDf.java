package stepdefination.litpro;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import pageobject.litpro.BenchmarkPg;
import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;
import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;

public class LpBenchmarkStDf {
	SlzLoginPg slzLogin = null;
	SlzHomePg slzHome;
	LitProHomePg lpHome;
	BenchmarkPg lpBenchmarkPg;
	WebDriver driver;
	Scenario scenario;
	 
	public LpBenchmarkStDf(SharedDriver driver) {
		this.driver = driver;
	}

	@Before
	public void before(Scenario scenario){
		this.scenario = scenario;
	}
	 
	@Given("^I browse to Benchmark Page as \"(.*?)\"$")
	public void i_browse_to_Benchmark_Page_as(String userType, Map<String, String> creds) throws Throwable {
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));
		lpHome = slzHome.launchLitPro(LitProUserType.TEACHER);
		 lpBenchmarkPg = lpHome.goToBenchmarksPage();
	}

	@Then("^I should see Benchmark Page Page Header starting with \"(.*?)\"$")
	public void i_should_see_Benchmark_Page_Page_Header_starting_with(String expected) throws Throwable {
		String actual = lpBenchmarkPg.getHeaderText();
		assertTrue("The expected Page Header was '" + expected + "' but actually was: " + actual, actual.contains(expected)); 
	}

	@Then("^I should see default selected benchmark as \"(.*?)\"$")
	public void i_should_see_default_selected_benchmark_as(String arg1) {
		lpBenchmarkPg.clickBenchmarkDropdown();
	}

	@Then("^I should see benchmark table with following category$")
	public void i_should_see_benchmark_table_with_following_category(List<String> catNames) throws Throwable {
	   Iterator<String> catIterator = catNames.iterator(); 
	   while(catIterator.hasNext()){
		   String categoryName = catIterator.next();
		    assertTrue("The Benchmark Category '" + categoryName + "' NOT displayed ",  lpBenchmarkPg.isBenchmarkCategoryDisplayed(categoryName)); 
		}
	}

	@When("^I click on Save$")
	public void i_click_on_Save() throws Throwable {
		lpBenchmarkPg.selectBenachmarkAndSave("LitPro Standard");
	}

	@Then("^'Banchmark Saved' Message should display$")
	public void banchmark_Saved_Message_should_be_displayed() throws Throwable {
		String actual = lpBenchmarkPg.getNotificationText();
	    assertTrue("'Benchmark Saved' message NOT matched. Actual: " + actual,  actual.equalsIgnoreCase("Benchmark Saved")); 
	}

}
