package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class PublishedQueriesDeactivateTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testPublishedQueriesDeactivateTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);
		// Click on published Query link
		driver.findElement(By.id("stubStackController_stub_RoxieQueries")).click();
		System.out.println("open PQ page");
		Thread.sleep(3000);
		// Click on Filter DropDown
		driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLFilterFilterDropDown_label")).click();
		System.out.println("open Filter drop down box");
		Thread.sleep(2000);
		driver.findElement(By.xpath(".//*[@id='stub_RoxieQueries-DL_Queries-DLPublishedBy']")).sendKeys("svc-PlatformAuto");
		// Click on Apply Button
		driver.findElement(By.xpath(".//*[@id='stub_RoxieQueries-DL_Queries-DLFilterFilterApply_label']")).click();
		Thread.sleep(2000);

	}

}
