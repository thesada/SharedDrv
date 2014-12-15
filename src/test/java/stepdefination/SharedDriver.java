package stepdefination;

import cucumber.api.Scenario;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Based on shared web driver implementation in cucumber-jvm examples
 * A new instance of SharedDriver is created for each Scenario and passed to  Stepdef classes via Dependency Injection
 */
public class SharedDriver extends EventFiringWebDriver {
    private static  WebDriver REAL_DRIVER;
    public static Properties appConfig;
    public static DesiredCapabilities capabilities = new DesiredCapabilities();
    public Scenario scenario;
    public static String browser=System.getProperty("browser");
    
    //Thread to close the Driver
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.quit();
        }
    };

    static {
    	System.out.println("BROWSER:"+ System.getProperty("browser"));
    
    	if(browser!=null){
    		setRealDriver();
    		REAL_DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    	}
    }
    public SharedDriver() {
        super(REAL_DRIVER);
        this.loadConf();
    }
    
    
    private void loadConf(){
		   appConfig = new Properties();
			try {
				appConfig.load(getClass().getResourceAsStream("/conf.properties")); 
				System.out.println("App configs loaded successfully");
			} 
			catch (Exception e) {
				System.out.println("Unable to load conf.properties. "+e.getMessage());
			}  
	 }
    
    
   private static void setRealDriver(){
	    if(browser.equalsIgnoreCase("chrome")){
		   chromeDriver();
		}
	   else if(browser.equalsIgnoreCase("ie")){
		   ieDriver();
	   }
	   else{
		   fireFoxDriver();
		   }
   }
  

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @cucumber.api.java.Before
    /**
     * Delete all cookies at the start of each scenario to avoid
     * shared state between tests
     */
    public void before(Scenario scenario) {
       // manage().deleteAllCookies();
        System.out.println("\n------------------------------------------------------------------------------");
        System.out.println("Running Scenario: " + scenario.getName());
        System.out.println("------------------------------------------------------------------------------");
        this.scenario=scenario;
    }

    @cucumber.api.java.After
    /**
     * Embed a screenshot in test report if test is marked as failed
     */
    public void after(Scenario scenario) {
       scenario.write("\n Page URL: " + getCurrentUrl());
        try {
            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");
        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }
    
    /*drivers setup*/
    private static void chromeDriver() {
    	try{
        System.setProperty("webdriver.chrome.driver","src/test/resources/drivers/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        REAL_DRIVER = new ChromeDriver(options);
        REAL_DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        REAL_DRIVER.manage().window().maximize();
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }

    private static void ieDriver() {
    	try{
    	System.setProperty("webdriver.ie.driver","src/test/resources/drivers/IEDriverServer.exe");
    	capabilities = DesiredCapabilities.internetExplorer();
		//capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
		capabilities.setCapability("ignoreZoomSetting", true);
		REAL_DRIVER = new InternetExplorerDriver(capabilities);
        REAL_DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        REAL_DRIVER.manage().window().maximize();
    }
	catch(Exception e){
		System.out.println(e.getMessage());
	}
    }

    private static void fireFoxDriver() {
    	try{
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.manager.showWhenStarting", false);
        profile.setPreference("browser.helperApps.alwaysAsk.force", true);
        profile.setPreference("browser.download.useDownloadDir", false);
        profile.setPreference("network.http.phishy-userpass-length", 255);
        /*try {
            String filePath = CukeHelper.file_path("downloads/firebug-2.0.2-fx.xpi").getAbsolutePath();
            profile.addExtension(new File(filePath));
        } catch(IOException e){
            System.out.println("There was a problem adding Firebug!");
        }*/
        REAL_DRIVER = new FirefoxDriver(profile);
        REAL_DRIVER.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        REAL_DRIVER.manage().window().maximize();
    	}
        catch(Exception e){
        	System.out.println(e.getMessage());
    	}
    }
    
    public void log(String msg){
    	System.out.println(msg);
    }

}
