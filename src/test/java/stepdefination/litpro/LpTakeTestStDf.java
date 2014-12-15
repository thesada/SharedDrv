package stepdefination.litpro;

import static org.junit.Assert.assertTrue;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.*;
import pageobject.litpro.AssessmentPg;
import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;
import pageobject.litpro.ReportsPg;
import pageobject.litpro.SettingsPg;
import pageobject.litpro.TestPg;
import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;

public class LpTakeTestStDf {
	SlzLoginPg slzLogin = null;
	SlzHomePg slzHome;
	LitProHomePg lpHome;
	SettingsPg lpSettingsPg;
	TestPg lpTestPg;
	AssessmentPg lpAssessmentPg;
	ReportsPg lpReportsPg;
	WebDriver driver;
	Scenario scenario;
	 
	public LpTakeTestStDf(SharedDriver driver) {
		this.driver = driver;
	}

	@Before
	public void before(Scenario scenario){
		this.scenario = scenario;
	}
	 
	@Given("^I browse to LitPro Test Page as \"(.*?)\"$")
	public void i_browse_to_Setting_Page_as(String userType, Map<String, String> creds) {
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));

		if (userType.equals("Student")) {
			lpHome = slzHome.launchLitPro(LitProUserType.STUDENT);
		} else if (userType.contains("Admin") || userType.contains("Teacher")) {
			lpHome = slzHome.launchLitPro(LitProUserType.TEACHER);
		}
		lpTestPg = lpHome.goToTakeTestPage();
	}
	

	@Then("^I should see welcome message with get started button$")
	public void i_should_see_welcome_message_with_get_started_button() {
		if(lpTestPg!=null){
	    String actual = lpTestPg.getGreetingMessage();
	    String expected =  "It's time to take a LitPro Test.";
	    assertTrue("Complete welcome message NOT displayed. ", actual.contains(expected)); 
		}
		else{
			assertTrue("LitPro Test tab is disabled, Perhaps Test is already taken", false);
			scenario.write("LitPro Test tab is disabled, Perhaps Test is already taken");
		}
	}

	@When("^I click on lets get started button$")
	public void i_click_on_lets_get_started_button() {
		lpAssessmentPg = lpTestPg.launchAssessmentPg();
	}

	@When("^I complete the assessment$")
	public void i_complete_the_assessment() {
		try{
		lpAssessmentPg.startAssessment();
		lpAssessmentPg.completeAssessment();
		}
		catch(Exception e){
			lpHome.switchDriverToHomePg();
			String exp = e.getMessage();
			assertTrue("Error while attending test. "+ exp, false);
		}
	}

	@Then("^I should be shown 'Create My Reading list' Button$")
	public void i_should_be_shown_Create_My_Reading_list_Button(){
		lpAssessmentPg.clickCreateReadingList();
	}

	@When("^I click 'Create My Reading list' Button$")
	public void i_click_Create_My_Reading_list_Button() {
	}

	@Then("^Home Page should be shown$")
	public void dashboard_should_be_shown() {
		lpHome.switchDriverToHomePg();
		
	}
	
	@Given("^I browse to Metrics section of Home Page as \"(.*?)\"$")
	public void i_browse_to_Metrics_section_of_Home_Page_as(String userType, Map<String, String> creds) throws Throwable {
		
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));

		if (userType.equals("Student")) {
			lpHome = slzHome.launchLitPro(LitProUserType.STUDENT);
		} else if (userType.contains("Admin") || userType.contains("Teacher")) {
			lpHome = slzHome.launchLitPro(LitProUserType.TEACHER);
		}
		
	}

	@Then("^I should see Header Text stating with \"(.*?)\"$")
	public void i_should_see_Header_Text_stating_with(String header) throws Throwable {
		String expected = header;
		String actual = lpHome.getHeader();
		assertTrue("The expected Header was '" + expected + "' but actually was: " + actual, actual.contains("Metrics for"));
	}
	 

	@Then("^Metrics Section should display following metrics$")
	public void metrics_Section_should_display_following_metrics(List<Map<String,String>> metNames) throws Throwable {
		Iterator<Map<String,String>> metIterator = metNames.iterator();
		metIterator.next();
		while(metIterator.hasNext()){
			Map<String,String> metricName = metIterator.next();
			String metricValue = lpHome.getMetricValue(metricName.get("Metric Name"));
			scenario.write(metricName.get("Metric Name") +": "+metricValue);
		}
		
	}

	@Then("^Following charts should display$")
	public void following_charts_should_display(List<String> metNames) throws Throwable {
	    
	}
	
	@When("^I browse to Reports page$")
	public void i_browse_to_Reports_page() throws Throwable {
		lpReportsPg = lpHome.goToReportsPage();
	}

	@Then("^I should see following reports link$")
	public void i_should_see_following_reports_link(List<String> repsList) {
		Iterator<String> repsIterator = repsList.iterator();
		repsIterator.next();
		while(repsIterator.hasNext()){
			String repName = repsIterator.next();
			assertTrue("'" + repName + "' Report link NOT displayed ", lpReportsPg.isReportDisplayed(repName) );
		}
	}
	@When("^I open \"(.*?)\" report$")
	public void i_open_report(String repName) throws Throwable {
		lpReportsPg.openReport(repName);
	}

	@Then("^Popup should open with header \"(.*?)\"$")
	public void popup_should_open_with_header(String repName) throws Throwable {
		String expected = repName;
		String actual = lpReportsPg.getReportModalHeaderText();
		lpReportsPg.closeReport();
		assertTrue("The expected Header was '" + expected + "' but actually was: " + actual, expected.equals(actual));
	 
	}

	@When("^I search report card for student \"(.*?)\"$")
	public void i_search_report_card_for_student(String studentId) throws Throwable {
		lpReportsPg.searchStudentReportByUserName(studentId); 
	}

	@Then("^I should see report card with following items$")
	public void i_should_see_report_card_with_following_information(List<String> elems) throws Throwable {
		Iterator<String> repsIterator = elems.iterator();
		repsIterator.next();
		while(repsIterator.hasNext()){
			String repItem = repsIterator.next();
			assertTrue("Report item '" + repItem + "' NOT displayed ", !lpReportsPg.getReportCardItemValue(repItem).equals("NULL"));
		}
	}
}
