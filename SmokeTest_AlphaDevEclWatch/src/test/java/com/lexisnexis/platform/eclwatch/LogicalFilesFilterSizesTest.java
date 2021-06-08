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
public class LogicalFilesFilterSizesTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFilesFilterSizesTest() throws Exception {
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

		// Enter FileSizes
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFromSize")).sendKeys("300");
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLToSize")).sendKeys("5000");

		// Click on Apply
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterApply_label")).click();
		Thread.sleep(3000);
		waitForLogicalFileFilterSizesToLoad();
		assertEquals("415", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-thor400_dev--.::deed_20160704020333.txt']/table/tr/td[11]")).getText().trim());
		assertEquals("4,440", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-thor400_dev--abush::out::comm_prefilltruckers10::base_file_matched']/table/tr/td[11]")).getText().trim());

	}
}
