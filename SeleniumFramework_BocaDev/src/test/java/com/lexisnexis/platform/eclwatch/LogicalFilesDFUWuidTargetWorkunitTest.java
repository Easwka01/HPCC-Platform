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
public class LogicalFilesDFUWuidTargetWorkunitTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFilesDFUWuidTargetWorkunitTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Search File
		driver.findElement(By.id("stubFindText")).sendKeys(properties.getProperty("DFUWUID"));
		System.out.println("DFU workunit Summary Page Displayed");
		driver.findElement(By.xpath(".//*[@id='stubFind']/span[1]")).click();
		Thread.sleep(2000);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.findElement(By.id("dijitReset dijitInline dijitIcon iconFind")).click();
		waitForDFUWorkunitSummaryTable();

		// Click Target Tab
		driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DLTabContainer_tablist_stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName")).click();
		Thread.sleep(2000);
		System.out.println("DFU Target Tab Clicked");

		// Click on Target Queries Tab
		driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DLTabContainer_tablist_stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_DFUWorkunit")).click();
		Thread.sleep(2000);
		System.out.println("Target Workunit Tab Clicked");

		waitForLogicalFilesDFUWuidTargetWorkunitToLoad();
		Thread.sleep(2000);
		assertEquals("D20180404-102850", driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_DFUWorkunit-DLWuid']")).getText().trim());
		System.out.println("DFUWUID Target Workunit Tab verified");

	}
}