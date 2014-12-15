package clear;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
 
import net.masterthought.cucumber.ReportBuilder;

public class ReportGen {
 
	public static void main(String[] args) {
		 
		File reportOutputDirectory = new File("target/reports/awesome/one");
		List<String> jsonReportFiles = new ArrayList<String>();
		
		
		  //jsonReportFiles.add("target/reports/json/1.json"); 
		  jsonReportFiles.add("target/reports/json/2.json"); 
		/*  jsonReportFiles.add("target/reports/json/3.json");
		 jsonReportFiles.add("target/reports/json/4.json"); */
		// jsonReportFiles.add("target/reports/json/5.json");
		 //jsonReportFiles.add("target/reports/json/6.json");
		 //jsonReportFiles.add("target/reports/json/f2.json");
		
		String buildNumber = "16";
		String buildProjectName = "16";
		/*Boolean skippedFails = false;
		Boolean undefinedFails = false;
		Boolean flashCharts = true;
		Boolean runWithJenkins = true;*/
		ReportBuilder reportBuilder;
		//ReportBuilder(List<String> jsonReports, File reportDirectory, String pluginUrlPath, String buildNumber, String buildProject, boolean skippedFails, boolean undefinedFails, boolean flashCharts, boolean runWithJenkins, boolean artifactsEnabled, String artifactConfig, boolean highCharts) 
		try {
			reportBuilder = new ReportBuilder(jsonReportFiles,reportOutputDirectory,"", buildNumber,buildProjectName, false,false,true,false,false,"",false);
			reportBuilder.generateReports();
		} catch (Exception e) {
			System.out.println("EXCEPTION" + e);
		}
		
	}

}
