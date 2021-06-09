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
public class TopologyMachinesMachineInformationDialogBoxTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testTopologyMachinesTest() throws Exception {
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

		// Click on Machines Link
		driver.findElement(By.id("dijit_form_ToggleButton_1_label")).click();
		Thread.sleep(3000);
		System.out.println("MachinesTopology box loaded");

		// Click on Server list
		String path1 = ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-TpMachine::null_10.48.72.187_null_/']/table/tr/td[2]";
		driver.findElement(By.xpath(path1)).click();
		System.out.print("Server list clicked");

		// Click on Machine Information
		driver.findElement(By.id("FilterDropDownWidget_0FilterDropDown")).click();
		waitForMachineInformationDialogBoxLoad();
		System.out.println("Macine Info displayed");
		Thread.sleep(5000);

		// Enter the values in machine information dialog
		driver.findElement(By.xpath(".//*[@id='AutoRefresh']")).clear();
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='AutoRefresh']")).sendKeys("4");

		driver.findElement(By.xpath(".//*[@id='CpuThreshold']")).clear();
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='CpuThreshold']")).sendKeys("95");

		driver.findElement(By.xpath(".//*[@id='MemThreshold']")).clear();
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='MemThreshold']")).sendKeys("3");

		driver.findElement(By.xpath(".//*[@id='DiskThreshold']")).clear();
		Thread.sleep(3000);
		driver.findElement(By.xpath(".//*[@id='DiskThreshold']")).sendKeys("5");

		// Click on Apply Button
		driver.findElement(By.id("FilterDropDownWidget_0FilterApply_label")).click();
		System.out.println("Filter Applied");
		Thread.sleep(5000);
		// Request Information page
		assertEquals(expectedTitle, driver.getTitle());
		System.out.println("Title Verified");
		waitForMachineInformationRequestInformation();
		Thread.sleep(3000);
		assertEquals("4", driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_RequestInformation_RequestDetails']/table/tr[12]/td[2]")).getText().trim());
		assertEquals("5", driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_RequestInformation_RequestDetails']/table/tr[10]/td[2]")).getText().trim());
		assertEquals("95", driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_RequestInformation_RequestDetails']/table/tr[11]/td[2]")).getText().trim());
		assertEquals("3", driver.findElement(By.xpath("//*[@id='stub_OPS-DL_Topology-DLDetails_RequestInformation_RequestDetails']/table/tr[13]/td[2]")).getText().trim());

	}

}
