package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import java.awt.Robot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class FileXrefSummaryTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testFileXrefSummaryTest() throws Exception {
		String expectedTitle = "ECL Watch";

		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(2000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Click on files main link
		WebElement element = driver.findElement(By.id("stubStackController_stub_Files"));
		element.click();
		mouseOverOnElement(element);

		// Thread.sleep(3000);
		System.out.println("Logical Files Page Loaded");
		waitForLogicalFilesLoad();
		Thread.sleep(7000);

		try {
			// driver.findElement(By.xpath("//*[@id='stub_Files-DLStackController']/span[4]")).click();
			driver.findElement(By.id("stub_Files-DLStackController_stub_Files-DL_Xref")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Thread.sleep(3000);
		System.out.println("XRef Page Loaded");
		waitForXrefTableToLoad();
		Thread.sleep(3000);
		// Click on Xref Cluster
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DLGrid-row-thor400_dev01']/table/tr/td[2]/a")).click();
		Thread.sleep(4000);
		System.out.println("Xref Summary Load");
		waitForXrefSummaryToLoad();
		assertEquals("Last Run:", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DL_thor400xdev01-DLSummaryForm']/ul/li[1]/label")).getText().trim());
		assertEquals("Last Message:", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DL_thor400xdev01-DLSummaryForm']/ul/li[2]/label")).getText().trim());
		assertEquals("Found Files:", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DL_thor400xdev01-DLSummaryForm']/ul/li[3]/label")).getText().trim());
		assertEquals("Orphan File:", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DL_thor400xdev01-DLSummaryForm']/ul/li[4]/label")).getText().trim());
		assertEquals("Lost Files:", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DL_thor400xdev01-DLSummaryForm']/ul/li[5]/label")).getText().trim());

	}

}
