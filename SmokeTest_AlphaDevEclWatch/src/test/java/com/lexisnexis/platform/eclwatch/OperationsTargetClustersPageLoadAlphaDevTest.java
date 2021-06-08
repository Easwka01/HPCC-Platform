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
public class OperationsTargetClustersPageLoadAlphaDevTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testOperationsTargetClustersPageLoadAlphaDevTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Click On Operations Link
		driver.findElement(By.id("stubStackController_stub_OPS")).click();
		System.out.println("Topology page loaded");
		waitForTopologyPageLoad();

		// Click on Target Clusters Link
		driver.findElement(By.id("stub_OPS-DLStackController_stub_OPS-DL_TargetClustersQuery_label")).click();
		System.out.println("Cluster Processes page loaded");
		waitForOperationsTargetClustersPageLoad();

	}

}