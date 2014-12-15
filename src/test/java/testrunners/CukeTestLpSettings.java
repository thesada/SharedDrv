package testrunners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
			format = {"json:target/reports/json/3.json"}, 
			features = "src/test/resources/features",
			glue="stepdefination",
			tags="@settings")
public class CukeTestLpSettings {

} 