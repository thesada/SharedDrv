package stepdefination.litpro;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;
import pageobject.litpro.InLibraryPg;
import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;

public class LpImportBooksStDf {
	SlzLoginPg slzLogin=null;
	SlzHomePg slzHome;
	LitProHomePg lpHome;
	InLibraryPg lpInLibPg;
	WebDriver driver;

	public LpImportBooksStDf(SharedDriver driver){
		this.driver=driver;
	}
	@Given("^I browse to In Library Page as \"(.*?)\"$")
	public void i_browse_to_In_Library_Page_as(String userType, Map<String, String> creds) {
	   
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));
		
		if (userType.equals("Student")) {
			lpHome = slzHome.launchLitPro(LitProUserType.STUDENT);
		} else if (userType.contains("Admin") || userType.contains("Teacher")) {
			lpHome = slzHome.launchLitPro(LitProUserType.SCHOOL_ADMIN);
		}
		lpInLibPg = lpHome.goToInLibraryPage();
	}

	@Then("^I should see In Library Page Header - \"(.*?)\"$")
	public void i_should_see_In_Library_Page_Header(String expected) throws Throwable {
		String actual = lpInLibPg.getPageHeader();
		assertTrue("The expected Page Header was '" + expected + "' but actually was: " + actual,
				actual.equalsIgnoreCase(expected)); 
	}

	@When("^I select csv file$")
	public void i_select_csv_file() throws Throwable {
		lpInLibPg.selectFile();
	}

	@When("^I click Upload button$")
	public void i_click_Upload_button() throws Throwable {
	    
	}

	@Then("^Import status should be \"(.*?)\"$")
	public void import_status_should_be(String arg1) throws Throwable {
	    
	}
}
