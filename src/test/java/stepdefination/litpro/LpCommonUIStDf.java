package stepdefination.litpro;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;
import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class LpCommonUIStDf  {
	 
	SlzLoginPg slzLogin=null;
	SlzHomePg slzHome;
	LitProHomePg lpHome;
	WebDriver driver;
	
	public LpCommonUIStDf(SharedDriver driver){
		this.driver=driver;
	}
	@When("^I launch LitPro as \"(.*?)\"$")
	public void i_launch_LitPro(String userType, Map<String, String> creds)
		 {
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));
		
		if (userType.equals("Student")) {
			lpHome = slzHome.launchLitPro(LitProUserType.STUDENT);
		} else if (userType.contains("Teacher")) {
			lpHome = slzHome.launchLitPro(LitProUserType.TEACHER);
		}
	 }

	@Then("^I should see Scholastic Literacy Pro Home Page$")
	public void i_should_see_Scholastic_Literacy_Pro_Home_Page(){
		String expected = lpHome.getExpectedTitle();
		String actual = driver.getTitle(); 
		assertTrue("The expected Page Title was '" + expected + "' but actually was: " + actual, actual.equalsIgnoreCase(expected));
	}

	@Then("^I should see user greeting text \"(.*?)\"$")
	public void i_should_see_user_greeting_text(String greetTxt) {
		String expected = greetTxt;
		String actual = lpHome.getUserGreetingText();
		assertTrue("The expected Greet Text was '" + expected + "' but actually was: " + actual, actual.contains("Welcome, "));
	}

	@Then("^I should see below tabs:$")
	public void i_should_see_following_tabs(List<String> tabList){
		
		String actual = lpHome.getDisplayedTabNames().toString();
		String expected = tabList.toString();
		assertTrue("The expected Tabs list was '" + expected + "' but actually was: " + actual, actual.contains(expected));

	}

	@Then("^I should see Logout link$")
	public void i_should_see_link() throws Throwable {
		assertTrue("Logout link NOT displayed", lpHome.isLogoutLinkPresent());
	}

}
