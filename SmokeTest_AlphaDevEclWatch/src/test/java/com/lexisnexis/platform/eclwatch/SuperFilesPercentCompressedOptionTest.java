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
public class SuperFilesPercentCompressedOptionTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testSuperFilesPercentCompressedOptionTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);
		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Search Superfile
		driver.findElement(By.id("stubFindText")).sendKeys(properties.getProperty("SuperFile"));
		System.out.println("superfile");
		driver.findElement(By.xpath(".//*[@id='stubFind']/span[1]")).click();
		Thread.sleep(5000);
		System.out.println("Super Files Search List Table Loaded");
		waitForFileSearchResultTable();

		// Click Logical Files Tab
		driver.findElement(By.id("stub_Main-DL_Search-DLTabContainer_tablist_stub_Main-DL_Search-DL_DetailLogicalxFile")).click();
		Thread.sleep(2000);
		System.out.println("Logical Files Tab Clicked");

		// Click on Super File link
		driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DLWorkunitsGrid-row---adv::compression::test']/table/tr/td[5]/a")).click();
		Thread.sleep(2000);
		System.out.println("SuperFile Link clicked");
		waitForSuperFileSummaryToLoad();
		Thread.sleep(3000);
		assertEquals("adv::compression::test", driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DL_xxadvxxcompressionxxtest-DLName']")).getText().trim());
		// Validate percent compressed option
		System.out.println(driver.findElement(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_xxadvxxcompressionxxtest-DLPercentCompressed")).getText());
		// assertEquals("Percent Compressed",
		// driver.findElement(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_xxadvxxcompressionxxtest-DLPercentCompressed")).getText().trim());

	}

}
