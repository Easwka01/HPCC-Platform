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
public class TopologyTargetsLogsFilterTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testTargetsLogsFilterTest() throws Exception {
		// String expectedtitle = "ECL Watch";
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

		// Click on Hole Cluster
		String path1 = "/html/body/div[7]/div/div[2]/div[5]/div/div/div/div[2]/div[1]/div/div/div/div/div[3]/div/div/div[3]/div/div[2]/div/div[2]/table/tr/td[2]/div";
		driver.findElement(By.xpath(path1)).click();
		Thread.sleep(2000);
		System.out.print("Hole Cluster Arrow button clicked");

		// Click on hthor_staging folder
		String path2 = ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-TargetClusterType::HoleCluster->TargetCluster::hthor_sta']/table/tr/td[2]/div";
		driver.findElement(By.xpath(path2)).click();
		Thread.sleep(2000);
		System.out.print("Hole Cluster Arrow button clicked");

		// Click on Ecl Server Process staging
		String path3 = ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-TargetClusterType::HoleCluster->TargetCluster::hthor_sta->TpEclServer::staging_eclserver']/table/tr/td[2]/div";
		driver.findElement(By.xpath(path3)).click();
		Thread.sleep(2000);
		System.out.print("Ecl Server Process staging clicked");

		// Enable Logs Tab by chossing ip
		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLGrid-row-TargetClusterType::HoleCluster->TargetCluster::hthor_sta->TpEclServer::staging_eclserver->TpMachine::EclServerProcess_10.173.14.209_null_/var/lib/HPCCSystems/staging_eclserver']/table/tr/td[2]")).click();
		Thread.sleep(2000);
		System.out.println("Logs Buton Enabled");

		// Click on Logs Tab
		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetailsTabContainer_tablist']/div[4]/div/div[3]")).click();
		Thread.sleep(5000);
		System.out.println("Logs Tab Clicked");
		waitForTargetsLogsTableToLoad();

		// Click on Logs Filter
		driver.findElement(By.id("stub_OPS-DL_Topology-DLDetails_LogsFilterFilterDropDown_label")).click();
		waitForTargetsLogsTableToLoad();
		System.out.println("Logs Filter Pop Up Displayed");
		Thread.sleep(3000);

		// Enter the values in in logs filter set dialog
		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsFirstNRows']")).sendKeys("1");

		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsLastNRows']")).sendKeys("10");
		// Select From Time
		driver.findElement(By.xpath(".//*[@id='widget_stub_OPS-DL_Topology-DLDetails_LogsFromTime']/div[1]/input")).click();
		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsFromTime_popup']/div[37]/div")).click();
		Thread.sleep(3000);
		// Select To Time
		driver.findElement(By.xpath(".//*[@id='widget_stub_OPS-DL_Topology-DLDetails_LogsToTime']/div[1]/input")).click();
		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsToTime_popup']/div[61]/div")).click();
		Thread.sleep(3000);

		driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsLastNHours']")).sendKeys("6");

		// Click on Apply Button
		driver.findElement(By.id("stub_OPS-DL_Topology-DLDetails_LogsFilterFilterApply_label")).click();
		System.out.println("Filter Applied");
		Thread.sleep(5000);
		waitForTargetLogsFilterToLoad();

	}
}
