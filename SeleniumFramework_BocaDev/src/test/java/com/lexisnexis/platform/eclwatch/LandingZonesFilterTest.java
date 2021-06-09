package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class LandingZonesFilterTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.FAST)
	public void testLoginTest() throws Exception {
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
		// Click on Landing Zone Link
		driver.findElement(By.id("stub_Files-DLStackController_stub_Files-DL_LandingZones_label")).click();
		waitForLandingZonesDataTable();
		System.out.println("LandingZone Page Loaded");
		Thread.sleep(5000);

		// Click on landing zone filter set

		driver.findElement(By.id("stub_Files-DL_LandingZones-DLFilterFilterDropDown_label")).click();
		System.out.println("Filter set pop up box displayed");
		Thread.sleep(3000);

		// Click on drop zone list
		driver.findElement(By.id("stub_Files-DL_LandingZones-DLDropZoneName2")).click();
		System.out.println("Drop zone list displayed");
		Thread.sleep(3000);
		// Select the dropzone list
		// driver.findElement(By.id("stub_Files-DL_LandingZones-DLDropZoneName2_dropdown")).click();
		// System.out.println("Drop zone selected");
		// Thread.sleep(5000);

		driver.findElement(By.id("dijit_MenuItem_23_text")).click();
		System.out.println("Drop zone selected");
		Thread.sleep(3000);

		// Enter the file name
		driver.findElement(By.id("stub_Files-DL_LandingZones-DLNameFilter")).sendKeys("0000autotest");
		System.out.println("File Name entered");

		// Click on Apply Link
		driver.findElement(By.id("stub_Files-DL_LandingZones-DLFilterFilterApply_label")).click();
		System.out.println("Filtered file displayed");
		waitForLandingZonesDataTable();
		System.out.println("Table Loaded");
		assertEquals(expectedTitle, driver.getTitle());
		System.out.println("Title Verified");
		// waitForElementToLoad(By.id("stub_Files-DL_LandingZones-DLLandingZonesGrid-row-10.140.128.250/data//0000autotest"));
		assertEquals("0000autotest", driver.findElement(By.xpath("/html/body/div[7]/div/div[2]/div[3]/div/div/div/div[2]/div[2]/div/div/div/div/div[3]/div/div/div[3]/div/div[2]/div/div[2]/table/tr/td[2]")).getText().trim());
	}

}
