package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import java.awt.Robot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class WorkunitFilterOwnerTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testWorkunitFilterOwnerOnlyTest() throws Exception {
		String expectedTitle = "ECL Watch";

		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(2000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// click on Workunit Gear
		driver.findElement(By.id("stubStackController_stub_ECL")).click();
		Thread.sleep(8000);
		System.out.println("Workunits page should be loaded");
		waitForWorkunitspagetoload();

		// Click on the Workunit filter
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLFilterFilterDropDown")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLOwner")).sendKeys(properties.getProperty("Owner"));
		System.out.println("Filter drop down owner name entered");
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLFilterFilterApply_label")).click();
		Thread.sleep(2000);
		waitForWorkunittFilterWUIDpageload();

	}
}
