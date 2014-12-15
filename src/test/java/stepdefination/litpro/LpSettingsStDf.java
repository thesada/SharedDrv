package stepdefination.litpro;

import static org.junit.Assert.assertTrue;
 


import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import cucumber.api.Scenario;
import cucumber.api.java.en.*;
import pageobject.litpro.LitProHomePg;
import pageobject.litpro.LitProHomePg.LitProUserType;
import pageobject.litpro.SettingsPg;
import pageobject.slz.SlzHomePg;
import pageobject.slz.SlzLoginPg;
import stepdefination.SharedDriver;

public class LpSettingsStDf {
	SlzLoginPg slzLogin = null;
	SlzHomePg slzHome;
	LitProHomePg lpHome;
	SettingsPg lpSettingsPg;
	WebDriver driver;
	Scenario scenario;
	 
	public LpSettingsStDf(SharedDriver driver) {
		this.driver = driver;
	}

	 
	@Given("^I browse to Settings Page as \"(.*?)\"$")
	public void i_browse_to_Setting_Page_as(String userType, Map<String, String> creds) {
		slzLogin = new SlzLoginPg(driver);
		slzLogin.launchMe();
		slzHome = slzLogin.login(creds.get("UserName"), creds.get("Password"));

		if (userType.equals("Student")) {
			lpHome = slzHome.launchLitPro(LitProUserType.STUDENT);
		} else if (userType.contains("Admin") || userType.contains("Teacher")) {
			lpHome = slzHome.launchLitPro(LitProUserType.TEACHER);
		}
		lpSettingsPg = lpHome.goToSettingPage();
	}
	
	@Then("^I should see class name$")
	public void i_should_see_same_School_name_that_is_displayed_in_toolbar() throws Throwable {
		assertTrue("The Page Header is empty " , !lpSettingsPg.getPageHeader().trim().isEmpty() ); 
	 }

	@Then("^I should see following settings under LitPro Test Settings:$")
	public void i_should_see_following_settings_under_LitPro_Test_Settings(List<Map<String, String>> input) throws Throwable {
		Iterator<Map<String, String>> inIterator = input.iterator();
		 while(inIterator.hasNext()){
			 Map<String, String> row = inIterator.next();
			 assertTrue("Test Setting '"+ row.get("Setting Name") +"' NOT displayed " ,lpSettingsPg.isSettingExist(row.get("Setting Name")));
			 /*
			 //verify default enabled
			 if(!row.get("Enabled").equalsIgnoreCase("NA")){
				 String enDis = row.get("Enabled").equalsIgnoreCase("Yes")?"Enabled":"Disabled";
				 assertTrue("Test Setting '"+ row.get("Setting Name") +"' NOT " + enDis + " by default"  ,lpSettingsPg.isSettingEnabled(row.get("Setting Name")).equalsIgnoreCase(row.get("Enabled")) );
			 }
			 //verify default value
			 if(!row.get("Default Value").equalsIgnoreCase("NA")){
				 String expected = row.get("Default Value");
				 String actual = lpSettingsPg.getSettingValue(row.get("Setting Name"));
				 assertTrue("Test Setting '"+ row.get("Setting Name") +"' default value NOT matched. Expected: " + expected + ", Actual:" + actual, 
						 actual.equals(expected));
			 }
			 */
			}
	}

	@Then("^I should see following settings under Book Quiz Settings:$")
	public void i_should_see_following_settings_under_Book_Quiz_Settings(List<Map<String, String>> input) throws Throwable {
		Iterator<Map<String, String>> inIterator = input.iterator();
		 while(inIterator.hasNext()){
			 Map<String, String> row = inIterator.next();
			 assertTrue("Quiz Setting '"+ row.get("Setting Name") +"' NOT displayed " ,lpSettingsPg.isSettingExist(row.get("Setting Name")));
			 /*
			 //verify default enabled
			 if(!row.get("Enabled").equalsIgnoreCase("NA")){
				 String enDis = row.get("Enabled").equalsIgnoreCase("Yes")?"Enabled":"Disabled";
				 assertTrue("Test Setting '"+ row.get("Setting Name") +"' NOT " + enDis + " by default"  ,lpSettingsPg.isSettingEnabled(row.get("Setting Name")).equalsIgnoreCase(row.get("Enabled")) );
			 }*/
		 }
	}
	
	@When("^I change following Test settings:$")
	public void i_change_following_Test_settings(List<Map<String, String>> input){
		Iterator<Map<String, String>> inIterator = input.iterator();
		 while(inIterator.hasNext()){
			 Map<String, String> row = inIterator.next();
			
			 //enable disable based on Action
			 if(row.get("Action").equalsIgnoreCase("Check")){
				 lpSettingsPg.enableSetting(row.get("Setting Name"));
			 }
			 else if(row.get("Action").equalsIgnoreCase("Uncheck")){
				 lpSettingsPg.disableSetting(row.get("Setting Name"));
			 }
			 
			 //change the value
			 if(!row.get("New Value").equalsIgnoreCase("NA")){
				 lpSettingsPg.setSettingValue(row.get("Setting Name"), row.get("New Value"));
			 }
		 }
	}

	@When("^I change following Quiz settings:$")
	public void i_change_following_Quiz_settings(List<Map<String, String>> input){
		Iterator<Map<String, String>> inIterator = input.iterator();
		 while(inIterator.hasNext()){
			 Map<String, String> row = inIterator.next();
			
			 //enable disable based on Action
			 if(row.get("Action").equalsIgnoreCase("Check")){
				 lpSettingsPg.enableSetting(row.get("Setting Name"));
			 }
			 else if(row.get("Action").equalsIgnoreCase("Uncheck")){
				 lpSettingsPg.disableSetting(row.get("Setting Name"));
			 }
			 
			 //change the value
			 if(!row.get("New Value").equalsIgnoreCase("NA")){
				 lpSettingsPg.setSettingValue(row.get("Setting Name"), row.get("New Value"));
			 }
		 }
	}

	@When("^I click Save$")
	public void i_click_Save() throws Throwable {
		lpSettingsPg.saveSettings(); 
	}

	@Then("^'Settings Saved' Message should be displayed$")
	public void settings_Saved_Message_should_be_displayed(){
		String actual = lpSettingsPg.getSaveNotification();
		String expected = "Settings Saved";
		 assertTrue("Setting saved message NOT matched. Expected: " + expected + ", Actual:" + actual,  actual.equals(expected));
	}


}
