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
public class LogicalFileHistoryInformationTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFileHistoryInformationTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Search file
		driver.findElement(By.id("stubFindText")).sendKeys(".::advtest");
		System.out.println("file");
		driver.findElement(By.xpath(".//*[@id='stubFind']/span[1]")).click();
		Thread.sleep(5000);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// driver.findElement(By.id("dijitReset dijitInline dijitIcon iconFind")).click();
		System.out.println("Logical Files Search List Table Loaded");
		waitForFileSearchResultTable();

		// Click Logical Files Tab
		driver.findElement(By.id("stub_Main-DL_Search-DLTabContainer_tablist_stub_Main-DL_Search-DL_DetailLogicalxFile")).click();
		Thread.sleep(2000);
		System.out.println("Logical Files Tab Clicked");

		// Click Logical File link
		driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DLWorkunitsGrid-row-hthor__dev_eclagent_2--.::advtest']/table/tr/td[5]/a")).click();
		Thread.sleep(2000);
		System.out.println("Logical File Link clicked");
		// waitForLogicalFileSummaryTabLoaded();;
		Thread.sleep(2000);
		// assertEquals(".::advtest",
		// driver.findElement(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_thor50xdev05x2xxxxxadvtest-DLName")).getText().trim());

		// Click on History Tab
		driver.findElement(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DLTabContainer_tablist_stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory")).click();
		System.out.println("History Tab link clicked");
		Thread.sleep(2000);

		waitForHistoryInformationTabLoaded();
		assertEquals("ECrash_Prod_0.csv", driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory-DLGrid-row-ECrash_Prod_0.csv']/table/tr/td[1]")).getText().trim());
		assertEquals("10.140.128.250", driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory-DLGrid-row-ECrash_Prod_0.csv']/table/tr/td[2]")).getText().trim());
		assertEquals("DFUimport", driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory-DLGrid-row-ECrash_Prod_0.csv']/table/tr/td[3]")).getText().trim());
		assertEquals("/data/walkbr01/", driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory-DLGrid-row-ECrash_Prod_0.csv']/table/tr/td[5]")).getText().trim());
		assertEquals("2018-03-28T17:11:52", driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory-DLGrid-row-ECrash_Prod_0.csv']/table/tr/td[6]")).getText().trim());

	}

}
