package com.qa.testscript;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.asserts.SoftAssert;

import com.qa.pages.FlipkartPages;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	WebDriver driver;
	Actions action;
	JavascriptExecutor js;
	SoftAssert softAssert;
	FlipkartPages FlipKartPagesOR;
	@Parameters({ "Browser", "Url" })
	@BeforeSuite
	public void setUp(String Browser, String Url) {
		// invoke the Chrome browser
		if (Browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver","C:\\chromedriver.exe");
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
			// invoke the Edge browser
		} else if (Browser.equalsIgnoreCase("Edge")) {
			 System.setProperty("webdriver.edge.driver","C:\\msedgedriver.exe");
			WebDriverManager.edgedriver().setup();
			driver = new EdgeDriver();
		}
		else if (Browser.equalsIgnoreCase("Firefox")) {
			 System.setProperty("webdriver.gecko.driver","C:\\geckodriver.exe");
			//WebDriverManager.edgedriver().setup();
			driver = new FirefoxDriver();
		}
		// Load the URl
		driver.get(Url);
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		FlipKartPagesOR=new FlipkartPages(driver);
	}

	@AfterSuite
	public void tearDown() {
	   //driver.quit();
	}

	public void captureScreenshot(WebDriver driver, String tName) throws IOException {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		File target = new File(System.getProperty("user.dir") + "/Screenshots/" + tName + ".png");
		FileUtils.copyFile(source, target);
	}

}
