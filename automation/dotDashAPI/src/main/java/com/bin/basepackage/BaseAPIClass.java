package com.bin.basepackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;

public class BaseAPIClass {
	
	public Properties prop;
	public static WebDriver driver;
	
	public BaseAPIClass() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "/resources/data.properties");
		prop.load(fis);
	}
	
	public void initiliazeWebDriver() {
		String browser = prop.getProperty("browser");
		String completeURL = prop.getProperty("completeurl");
		if (driver == null) {
			if (browser.equals("chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\ChromeDriver\\chromedriver.exe");
				driver = new ChromeDriver();
			} else if (browser.equals("firefox")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\gecko\\geckodriver.exe");
				driver = new FirefoxDriver();
			}  else if (browser.equals("headless")) {
				File path=new File((System.getProperty("user.dir")) + "\\phantomjs\\phantomjs.exe");
				System.setProperty("phantomjs.binary.path",path.getAbsolutePath());
						
				driver = new PhantomJSDriver();
			} 
		}
		driver.manage().window().maximize();
		driver.get(completeURL);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
	}
	
	
	public static void takeScreenshotAtEndOfTest() throws IOException {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String currentDir = System.getProperty("user.dir");
		FileUtils.copyFile(scrFile, new File(currentDir + "/screenshots/" + System.currentTimeMillis() + ".png"));
	}

}
