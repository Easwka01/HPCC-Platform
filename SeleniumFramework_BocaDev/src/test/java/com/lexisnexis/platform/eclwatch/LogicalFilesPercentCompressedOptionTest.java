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
public class LogicalFilesPercentCompressedOptionTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFilesPercentCompressedOptionTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);
		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Search Logical File
		driver.findElement(By.id("stubFindText")).sendKeys(properties.getProperty("PercentCompressedFile"));
		System.out.println("logical file clicked");
		driver.findElement(By.xpath(".//*[@id='stubFind']/span[1]")).click();
		Thread.sleep(5000);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.findElement(By.id("dijitReset dijitInline dijitIcon iconFind")).click();
		System.out.println("Logical Files Search List Table Loaded");
		waitForFileSearchResultTable();

		// Click Logical Files Tab
		// driver.findElement(By.id("stub_Main-DL_Search-DLTabContainer_tablist_stub_Main-DL_Search-DL_DetailLogicalxFile")).click();
		// Thread.sleep(2000);
		// System.out.println("Logical Files Tab Clicked");

		// Click Logical File link
		driver.findElement(By.xpath("//*[@id='stub_Main-DL_Search-DLGrid-row-1']/table/tr/td[4]/a")).click();
		Thread.sleep(2000);
		System.out.println("Logical File Link clicked");
		waitForLogicalFileSummaryToLoad();
		Thread.sleep(2000);
		// assertEquals("Percent Compressed:",
		// driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLplatformxxtestxxcompression-DLSummaryForm']/ul/li[11]/label")).getText().trim());
		// Validate percent compressed option
		// System.out.println(driver.findElement(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx214xxadvxxtestxx1-DLPercentCompressed")).getText());
		assertEquals("5.95", driver.findElement(By.id("stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLplatformxxtestxxcompression-DLPercentCompressed")).getText().trim());
	}

}
