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
public class PrimaryMonitoringTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLoginTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Home Screen Monitoring button
		driver.findElement(By.id("MonitorStatus")).click();
		System.out.println("Clicked");
		Thread.sleep(3000);
		// System.out.println(driver.getTitle());
		assertEquals(expectedTitle, driver.getTitle());
		System.out.println("Title Verified");
		waitForPrimaryMonitoringDataTable();
		System.out.print("Monitoring Table Loaded");

	}
}
