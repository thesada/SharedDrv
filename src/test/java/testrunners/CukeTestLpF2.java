package testrunners;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
			format = {"json:target/reports/json/f2.json"}, 
			features = "src/test/resources/features",
			glue="stepdefination",
			tags="@f2")
public class CukeTestLpF2 {

} 