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
public class FileXrefFoundFileTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testFileXrefFoundFileTest() throws Exception {
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
		Thread.sleep(5000);

		try {
			// driver.findElement(By.xpath("//*[@id='stub_Files-DLStackController']/span[4]")).click();
			driver.findElement(By.id("stub_Files-DLStackController_stub_Files-DL_Xref")).click();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// driver.findElement(By.xpath("//*[@id='stub_Files-DLStackController_stub_Files-DL_Xref_label']")).click();
		Thread.sleep(3000);
		System.out.println("XRef Page Loaded");
		waitForXrefTableToLoad();
		// Click on Xref Cluster
		driver.findElement(By.xpath(".//*[@id=\'stub_Files-DL_Xref-DLGrid-row-thor400_126\']/table/tr/td[2]/a")).click();
		Thread.sleep(2000);
		System.out.println("XRef Cluster Loaded");
		waitForXrefSummaryToLoadAlphaDev();
		System.out.println("XRef sumamry Loaded");

		// Click on Found File
		driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400x126-DLTabContainer_tablist_stub_Files-DL_Xref-DL_thor400x126-DL_FoundFiles")).click();
		Thread.sleep(3000);
		waitForXreFFoundFileToLoadAlphDev();
		System.out.println("XRef found file Loaded");
		assertEquals("Name", driver.findElement(By.xpath("//*[@id=\"stub_Files-DL_Xref-DL_thor400x126-DL_FoundFiles-DLGrid-header\"]/tr[2]/th[2]/div")).getText().trim());
		assertEquals("Modified", driver.findElement(By.xpath("//*[@id=\"stub_Files-DL_Xref-DL_thor400x126-DL_FoundFiles-DLGrid-header\"]/tr[2]/th[3]/div")).getText().trim());
		assertEquals("Parts", driver.findElement(By.xpath("//*[@id=\"stub_Files-DL_Xref-DL_thor400x126-DL_FoundFiles-DLGrid-header\"]/tr[2]/th[4]/div")).getText().trim());
		assertEquals("Size", driver.findElement(By.xpath("//*[@id=\"stub_Files-DL_Xref-DL_thor400x126-DL_FoundFiles-DLGrid-header\"]/tr[2]/th[5]/div")).getText().trim());
		System.out.println("Verified Xref found file");
	}

}
