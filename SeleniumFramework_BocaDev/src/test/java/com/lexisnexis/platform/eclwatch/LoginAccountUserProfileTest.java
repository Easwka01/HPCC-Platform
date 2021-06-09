package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class LoginAccountUserProfileTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.FAST)
	public void testUserDetailsTest() throws Exception {
		// Login to Ecl Watch

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		doLogin();
		// Click on profile link
		driver.findElement(By.id("stubUserID")).click();
		waitForScreenTitle("User Details");
		System.out.println("profile link clicked");
		assertEquals("svc-PlatformAuto", driver.findElement(By.id("stubUserInfo-DLUsername")).getText().trim());

	}
}
