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
public class MainFilesXrefTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testMainFilesXrefTest() throws Exception {
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
		assertEquals("thor400_dev01", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DLGrid-row-thor400_dev01']/table/tr/td[2]/a")).getText().trim());
		assertEquals("thor50_dev01", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DLGrid-row-thor50_dev01']/table/tr/td[2]/a")).getText().trim());
		assertEquals("thor400_sta01", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DLGrid-row-thor400_sta01']/table/tr/td[2]/a")).getText().trim());
		// assertEquals("thor400_dev02_2",
		// driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DLGrid-row-thor400_dev02_2']/table/tr/td[2]/a")).getText().trim());
		assertEquals("thor50_dev05", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DLGrid-row-thor50_dev05']/table/tr/td[2]/a")).getText().trim());
		assertEquals("SuperFiles", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_Xref-DLGrid-row-SuperFiles']/table/tr/td[2]/a")).getText().trim());
	}

}
