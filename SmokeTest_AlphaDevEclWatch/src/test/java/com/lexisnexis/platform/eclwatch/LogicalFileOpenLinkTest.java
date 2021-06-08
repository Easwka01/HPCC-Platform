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
public class LogicalFileOpenLinkTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFileOpenTest() throws Exception {
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

		// Enter Name in Filter Box
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLName")).sendKeys(properties.getProperty("LogicalFile"));
		Thread.sleep(2000);

		// Click on Apply
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterApply_label")).click();
		waitForLogicalFileFilterNameToLoad();
		Thread.sleep(2000);
		// assertEquals("thor::platform_test::test_13264_xml_fixed_compressed (hthor__dev_eclagent_1)",
		// driver.findElement(By.xpath("//*[@id=\"stub_Main-DL_Search-DLGrid-row-2\"]/table/tr/td[4]/a")).getText().trim());
		Thread.sleep(2000);

		// Select logical file
		driver.findElement(By.xpath("//*[@id=\"stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-hthor__dev_eclagent_1--thor::platform_test::test_13264_xml_fixed_compressed\"]/table/tr/td[1]/input")).click();
		System.out.println("Logical File Selected");
		Thread.sleep(2000);

		// Click on Open link button
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLOpen_label")).click();
		waitForAlphaDevLogicalFilesOpenSummaryToLoad();
		Thread.sleep(2000);
		assertEquals("thor::platform_test::test_13264_xml_fixed_compressed", driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentx1xxthorxxplatformxtestxxtestx13264xxmlxfixedxcompressed-DLName")).getText().trim());

	}
}