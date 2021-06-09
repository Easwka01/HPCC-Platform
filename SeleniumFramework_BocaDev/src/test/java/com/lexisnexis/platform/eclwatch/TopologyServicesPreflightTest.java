package com.lexisnexis.platform.eclwatch;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.openqa.selenium.By;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

public class TopologyServicesPreflightTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testServicePreflightTest() throws Exception {
		doLogin();

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		driver.findElement(By.id("stubStackController_stub_OPS")).click();
		System.out.println("Topology page loaded");
		waitForTopologyPageLoad();
		driver.findElement(By.id("dijit_form_ToggleButton_2_label")).click();
		Thread.sleep(3000);
		System.out.println("ServicesTopology box loaded");

		// Click on Dali folder icon
		String path1 = ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali']/table/tr/td[2]/div";
		driver.findElement(By.xpath(path1)).click();
		System.out.print("Dali folder clicked");
		// Click on Dali folder file
		String path2 = ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali->Service::dali']/table/tr/td[2]/div";
		driver.findElement(By.xpath(path2)).click();
		System.out.print("Dali folder file clicked");
		// Enable checkbox
		String path3 = ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali->Service::dali->TpMachine::DaliServerProcess_10.241.12.201_null_/var/lib/HPCCSystems/dali']/table/tr/td[2]";
		driver.findElement(By.xpath(path3)).click();
		System.out.print("Check box Clicked and Machine information tab enabled");
		// driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali->Service::dali->TpMachine::DaliServerProcess_10.241.12.201_null_/var/lib/HPCCSystems/dali']/table/tr/td[1]/input")).click();
		Thread.sleep(5000);
		System.out.println("Macine Info enabled");

		// Click on Machine Information
		driver.findElement(By.id("FilterDropDownWidget_0FilterDropDown")).click();
		waitForMachineInformationDialogBoxLoad();
		System.out.println("Macine Info displayed");

		// Click on Apply Button
		driver.findElement(By.id("FilterDropDownWidget_0FilterApply_label")).click();
		System.out.println("Filter Applied");
		// Click on Preflight IP tab
		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_RequestInformationTabContainer_tablist']/div[4]/div/div[2]")).click();
		waitForServicesPreflightResultsTable();
		assertEquals("10.241.12.201 2/", driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DL_10x241x12x201Grid-row-undefined']/table/tr/td[1]")).getText().trim());
		assertEquals("10.241.12.201|[DaliServerProcess]", driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DL_10x241x12x201Grid-row-undefined']/table/tr/td[2]")).getText().trim());
	}
}
