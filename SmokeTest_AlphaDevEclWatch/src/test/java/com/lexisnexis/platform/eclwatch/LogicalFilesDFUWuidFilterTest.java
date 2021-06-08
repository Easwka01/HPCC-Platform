package com.lexisnexis.platform.eclwatch;

import static org.junit.Assert.assertEquals;

//import java.util.concurrent.TimeUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class LogicalFilesDFUWuidFilterTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFilesDFUWuidFilterTest() throws Exception {
		String expectedTitle = "ECL Watch";

		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(2000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Click on Files main link
		driver.findElement(By.id("stubStackController_stub_Files")).click();
		Thread.sleep(3000);
		System.out.println("Logical Files Page Loaded");
		// Click on Logical Files Workunits
		driver.findElement(By.id("stub_Files-DLStackController_stub_Files-DL_Workunits_label")).click();
		Thread.sleep(2000);
		System.out.println("Logical Files Workunits Loaded");

		// Click on the Filter Drop down
		driver.findElement(By.id("stub_Files-DL_Workunits-DLFilterFilterDropDown_label")).click();
		Thread.sleep(2000);
		System.out.println("Logical File Workunits Filter drop down Loaded");

		// Enter the Logical Files WUID in filter
		driver.findElement(By.id("stub_Files-DL_Workunits-DLWuid")).sendKeys("D20180328-131151");
		driver.findElement(By.id("stub_Files-DL_Workunits-DLFilterFilterApply_label")).click();
		Thread.sleep(2000);

		// Verify Filter Result Table
		waitForLogicalFilesDFUWUIDFilterToLoad();
		System.out.println("Logical Files DFUWUID Filter Results Loaded");

	}
}
