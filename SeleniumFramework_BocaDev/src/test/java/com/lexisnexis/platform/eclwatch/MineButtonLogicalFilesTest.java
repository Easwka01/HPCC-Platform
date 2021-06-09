package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import java.awt.Robot;
import java.util.concurrent.TimeUnit;

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
public class MineButtonLogicalFilesTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testMineButtonLogicalFilesTest() throws Exception {
		String expectedTitle = "ECL Watch";

		// Login to Ecl Watch
		// driver.navigate().to(appUrl);

		driver.navigate().to(appUrl);
		Thread.sleep(2000);
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("app.user"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("app.pass"));
		driver.findElement(By.id("button")).click();
		System.out.println("Home page displayed");

		// Click on Logical Files main link
		driver.findElement(By.id("stubStackController_stub_Files")).click();
		Thread.sleep(2000);
		System.out.println("List of logical files displayed");

		// Click on Mine Button
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLMine")).click();
		Thread.sleep(2000);
		System.out.println("Mine button clicked");

		// Verify Logical page loaded with logged in user name
		waitForLogicalFilesMineToLoad();
		System.out.println("Logical Page loaded with logged user name");

		assertEquals("svc-PlatformAuto", driver.findElement(By.xpath("//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row---thor::platform::superfile::platformtest']/table/tr/td[6]")).getText().trim());

	}

}

// *[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row---thor::platform::superfile::platformtest']/table/tr/td[6]