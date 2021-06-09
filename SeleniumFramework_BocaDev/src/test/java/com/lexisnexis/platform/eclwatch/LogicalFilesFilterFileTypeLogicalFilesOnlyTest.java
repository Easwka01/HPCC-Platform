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
public class LogicalFilesFilterFileTypeLogicalFilesOnlyTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFilesFilterFileTypeLogicalFilesOnlyTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Click Files Icon
		driver.findElement(By.xpath(".//*[@id='stubStackController_stub_Files']/span[1]")).click();
		Thread.sleep(5000);
		System.out.println("Files Icon Clicked");

		// Click on Filter Drop Down
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterDropDown")).click();
		Thread.sleep(2000);
		System.out.println("Filter Icon Clicked");
		// waitForLogicalFileFilterBoxToLoad();
		System.out.println("Filter Box Loaded");

		// Select Logical Files Only
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLFileType']/tbody/tr/td[2]/input")).click();
		driver.findElement(By.id("dijit_MenuItem_22_text")).click();

		// Click on Apply
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterApply_label")).click();
		Thread.sleep(3000);
		waitForLogicalFilterFileTypeLogicalFilesOnlyToLoad();
		assertEquals(".::0000autotest", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-hthor__dev_eclagent_2--.::0000autotest']/table/tr/td[5]/a")).getText().trim());

	}
}
