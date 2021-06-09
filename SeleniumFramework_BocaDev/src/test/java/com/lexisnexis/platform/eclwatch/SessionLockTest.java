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
public class SessionLockTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testSessionLockTest() throws Exception {
		String expectedTitle = "ECL Watch";

		// Login to Ecl Watch
		// driver.navigate().to(appUrl);

		driver.navigate().to(appUrl);
		Thread.sleep(2000);
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("app.user"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("app.pass"));
		driver.findElement(By.id("button")).click();
		System.out.println("Home page displayed");

		// Click on lock link
		driver.findElement(By.xpath("//*[@id='Lock']")).click();
		System.out.println("Clicked on lock link");
		Thread.sleep(3000);

		waitForSessionUnlockScreenToLoad();
		assertEquals("Unlock", driver.findElement(By.id("LockDialogWidget_0Unlock_label")).getText().trim());

	}
}
