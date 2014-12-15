package stepdefination.slz;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class SlzLoginStDf {

	SlzLoginPg slzLoginPg;
	SlzHomePg slzHomePg;
	WebDriver driver;

	public SlzLoginStDf(SharedDriver driver) {
		this.driver = driver;
	}

	@Given("^I am on Scholastic Learning Zone Login Page$")
	public void i_am_on_Scholastic_Learning_Zone_Login_Page() throws Throwable {
		//driver.get("https://educator-qa-slz01.scholasticlearningzone.com/auth/intl/Login/AUST7RP");
		slzLoginPg = new SlzLoginPg(driver);
		slzLoginPg.launchMe();
	}

	@When("^I login with following credentials$")
	public void i_login_with_following_credentials(Map<String, String> creds)
			throws Throwable {
		slzHomePg = slzLoginPg.login(creds.get("UserName"), creds.get("Password"));
	}

	@Then("^I should see Scholastic Learning Zone Home Page$")
	public void i_should_see_Scholastic_Learning_Zone_Home_Page()
			throws Throwable {
		String actual = slzHomePg.getUserGreetingText();
		String expected = "Hi ";
		assertTrue("The expected Greet Text was '" + expected
				+ "' but actually was: " + actual, actual.contains(expected));
	}

	@When("^I login using '(.*?)' and '(.*?)'$")
	public void i_login_using(String un, String pw) throws Throwable {
		slzLoginPg.login(un, pw);
	}

	@Then("^I should see Error Message \"(.*?)\"$")
	public void i_should_see_Error_Message(String expected) throws Throwable {
		String actual = slzLoginPg.getLoginErrorText();
		assertTrue("The expected Error Text was '" + expected
				+ "' but actually was: " + actual, actual.equals(expected));
	}

}
