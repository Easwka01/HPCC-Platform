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
public class TopologyServicesMachineInformationDialogBoxTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testMachineInfoTest() throws Exception {
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
		Thread.sleep(3000);
		waitForTopologyPageLoad();

		// Click on Services Link
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
