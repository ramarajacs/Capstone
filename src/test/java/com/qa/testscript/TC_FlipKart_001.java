package com.qa.testscript;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TC_FlipKart_001 extends TestBase{
	@Test(priority = 0)
	public void checkPopUpCloseButton() throws IOException {
		boolean close=FlipKartPagesOR.getPopUpCloseButton().isEnabled();
		if(close) {
			Assert.assertTrue(true);
		}
		else {
			captureScreenshot(driver,"checkPopUpCloseButton");
			Assert.assertTrue(false,"Popup close button is not enabled");
		}
		FlipKartPagesOR.getPopUpCloseButton().click();
	}
	@Test(priority = 1,dependsOnMethods = "checkPopUpCloseButton")
	public void checkPosition() throws IOException {
		if(FlipKartPagesOR.getFlipKart().isDisplayed()) {
			Assert.assertTrue(true,"Flipkart logo is present");
			int logolocation=FlipKartPagesOR.getFlipKart().getLocation().getX();
			
			if(logolocation<500) {
				Assert.assertTrue(true,"Logo is on left side");
			}
			else {
				captureScreenshot(driver,"checkPosition");
				Assert.assertTrue(false,"Logo is not available on left side");
			}
		}
		else {
			captureScreenshot(driver,"checkPosition");
			Assert.assertTrue(false,"Flipkart logo is not present");
		}
	}
	
	@Test(priority = 2,dependsOnMethods = "checkPopUpCloseButton")
	public void checkSearch() throws InterruptedException, IOException {
		FlipKartPagesOR.getSearchInputBox().sendKeys("Iphone 11");
		Thread.sleep(2000);
		boolean search_button=FlipKartPagesOR.getSearchButton().isEnabled();
		if(search_button) {
			Assert.assertTrue(true);
		}
		else {
			captureScreenshot(driver,"checkSearch");
			Assert.assertTrue(false);
		}
		FlipKartPagesOR.getSearchButton().click();
		int items=FlipKartPagesOR.getSearchedItem().size();
		System.out.println("No of items Displayed: "+items);
		String firstItem=FlipKartPagesOR.getSearchedItem().get(0).getText();
		//click on first displayed Search Item
        FlipKartPagesOR.getSearchedItem().get(0).click();
        Set<String> handler=driver.getWindowHandles();
        Iterator<String> li=handler.iterator();
        String ParentHandleID=li.next();
        String ChildHandleID=li.next();
        driver.switchTo().window(ChildHandleID);
        Thread.sleep(5000);
        String selectedfirstitem=driver.getTitle();
        if(driver.getPageSource().contains(firstItem) ) {
        	Assert.assertTrue(true);
        }
        else {
        	System.out.println(selectedfirstitem);
        	System.out.println(firstItem);
        	captureScreenshot(driver,"checkSearch");
        	Assert.assertTrue(false,"selected item not navigated to correct page");
        }
        Assert.assertEquals(selectedfirstitem,"APPLE iPhone 11 ( 128 GB Storage, 0 GB RAM ) Online at Best Price On Flipkart.com",
        		"title is not correct");
	}
}
