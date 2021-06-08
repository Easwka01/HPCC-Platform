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
public class PublishedQueriesOptionsCheckSingleNodeAlphaDevTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLoginTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Click on published Query link
		driver.findElement(By.id("stubStackController_stub_RoxieQueries")).click();
		System.out.println("open PQ page");
		Thread.sleep(3000);

		// Click on OPtion Dropdown
		driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLOptions_label")).click();
		System.out.println("open Option pop-up box");
		Thread.sleep(3000);
		waitForPublishedQueriesOptionDropDownAlphaDev();

		// Click on the published query option check single node
		driver.findElement(By.id("dijit_form_Button_5_label")).click();
		System.out.println("check single node apply button clicked");
		Thread.sleep(3000);
		waitForPublishedQueriesOptionCheckSingleNodeAlphaDev();

		// // Click on Apply Button
		// driver.findElement(By.id("dijit_form_Button_3_label")).click();
		// System.out.println("table loaded");
		// Thread.sleep(3000);
		//
		// // verify the data table
		// assertEquals(expectedTitle, driver.getTitle());
		// System.out.println("Title Verified");
		// waitForPublishedQueriesOptionsDataTable();
		// System.out.print("PQ Options Data table loaded");

	}

}
