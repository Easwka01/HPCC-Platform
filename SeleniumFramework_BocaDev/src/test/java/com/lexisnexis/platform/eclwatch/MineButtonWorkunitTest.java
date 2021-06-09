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
public class MineButtonWorkunitTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testMineButtonTest() throws Exception {
		String expectedTitle = "ECL Watch";

		// Login to Ecl Watch
		// driver.navigate().to(appUrl);

		driver.navigate().to(appUrl);
		Thread.sleep(2000);
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("app.user"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("app.pass"));
		driver.findElement(By.id("button")).click();
		System.out.println("Home page displayed");

		// Click on Workunit main link
		driver.findElement(By.id("stubStackController_stub_ECL")).click();
		Thread.sleep(2000);
		System.out.println("List of workunits Displayed");

		// Click on Mine Button
		driver.findElement(By.id("stub_ECL-DL_Workunits-DLMine")).click();
		Thread.sleep(2000);
		System.out.println("Mine button clicked");

		// Verify Workunit page loaded with logged in user name
		waitForWorkunitMineToLoad();
		System.out.println("Workunit page loaded with logged user name");

		assertEquals("svc-PlatformAuto", driver.findElement(By.xpath("//*[@id='stub_ECL-DL_Workunits-DLWorkunitsGrid-row-W20190201-161314']/table/tr/td[4]")).getText().trim());

	}

}
