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
public class PublishedQueriesPublishedByMeAlphaDevTest extends TestBaseBocaDev {

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
		Thread.sleep(2000);

		// Click on the Published by me check box
		driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLMine")).click();
		System.out.println("open PQ page");
		Thread.sleep(3000);
		waitForPublishedQueriesPublishedByMeTableAlphaDev();
		assertEquals("svc-PlatformAuto", driver.findElement(By.xpath("//*[@id='stub_RoxieQueries-DL_Queries-DLQuerySetGrid-row-thor20_167_dev_eclcc_dev:platform_test_-_compr_test.1']/table/tr/td[11]")).getText().trim());

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
