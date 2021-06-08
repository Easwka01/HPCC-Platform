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
public class TopologyMachinePreflightTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testMachinesPreflightTest() throws Exception {
		String expectedtitle = "ECL Watch";
		// Login to Ecl Watch
		doLogin();
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Click On Operations Link
		driver.findElement(By.id("stubStackController_stub_OPS")).click();
		System.out.println("Topology page loaded");
		Thread.sleep(3000);
		waitForTopologyPageLoad();

		// Click on Machines Link
		driver.findElement(By.id("dijit_form_ToggleButton_1_label")).click();
		Thread.sleep(3000);
		System.out.println("MachinesTopology box loaded");

		// Click on Server List
		String path1 = ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-TpMachine::null_10.48.72.194_null_/']/table/tr/td[2]";
		driver.findElement(By.xpath(path1)).click();
		System.out.print("Server list clicked");

		// Click on Machine Information
		driver.findElement(By.id("FilterDropDownWidget_0FilterDropDown")).click();
		waitForMachineInformationDialogBoxLoad();
		System.out.println("Macine Info displayed");
		Thread.sleep(5000);

		// Click on Apply Button
		driver.findElement(By.id("FilterDropDownWidget_0FilterApply_label")).click();
		System.out.println("Filter Applied");
		Thread.sleep(5000);
		Thread.sleep(5000);

		// Click on Preflight IP tab
		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_RequestInformationTabContainer_tablist']/div[4]/div/div[2]")).click();
		Thread.sleep(5000);
		waitForMachinesPreflightResultsTable();
		assertEquals("10.48.72.194/data", driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DL_10x48x72x194Grid-row-undefined']/table/tr/td[1]")).getText().trim());
		assertEquals("null[ServerList]", driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DL_10x48x72x194Grid-row-undefined']/table/tr/td[2]")).getText().trim());
	}

}
