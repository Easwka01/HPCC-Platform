package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class WorkunitFilterArchivedWUIDOnlyTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testWorkunitFilterArchivedWUIDOnlyTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);
		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// click on Workunit Gear
		driver.findElement(By.id("stubStackController_stub_ECL")).click();
		Thread.sleep(5000);
		System.out.println("Workunits page should be loaded");
		waitForWorkunitspagetoload();

		// Click on the Workunit filter Set
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLFilterFilterDropDown")).click();
		Thread.sleep(2000);

		// Click on the Archived Only check box
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLType")).click();
		Thread.sleep(2000);

		// Enter owner name
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLWuid")).sendKeys(properties.getProperty("WUIDArchived"));

		// Click on Apply
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLFilterFilterApply_label")).click();
		Thread.sleep(2000);

		// Verify the Archived Wuid Filter Load
		waitForArchivedWorkunitOnly();

	}
}