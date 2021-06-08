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

/*
 * @RunWith(BlockJUnit4ClassRunner.class) public class TopologyServicesLogsFilterTest extends TestBase {
 * 
 * @Test
 * 
 * @Timeout(group = Group.SLOW) public void testServicesLogsFilterTest() throws Exception { String expectedTitle =
 * "ECL Watch"; // Login to Ecl Watch driver.navigate().to(appUrl); Thread.sleep(3000);
 * 
 * // Click On Operations Link driver.findElement(By.id("stubStackController_stub_OPS")).click();
 * System.out.println("Topology page loaded"); Thread.sleep(3000); waitForTopologyPageLoad();
 * 
 * // Click on Services Link driver.findElement(By.id("dijit_form_ToggleButton_2_label")).click(); Thread.sleep(3000);
 * System.out.println("ServicesTopology box loaded");
 * 
 * // Click on Dali folder icon String path1 =
 * ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali']/table/tr/td[2]/div";
 * driver.findElement(By.xpath(path1)).click(); System.out.print("Dali folder clicked");
 * 
 * // Click on Dali folder file String path2 =
 * ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali->Service::dali']/table/tr/td[2]/div";
 * driver.findElement(By.xpath(path2)).click(); System.out.print("Dali folder file clicked");
 * 
 * // Enable checkbox String path3 =
 * ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali->Service::dali->TpMachine::DaliServerProcess_10.241.12.201_null_/var/lib/HPCCSystems/dali']/table/tr/td[2]"
 * ; driver.findElement(By.xpath(path3)).click(); System.out.print("Check box Clicked and Logs Tab enabled");
 * driver.findElement(By.xpath(
 * ".//*[@id='stub_OPS-DL_Topology-DLGrid-row-ServiceType::TpDali->Service::dali->TpMachine::DaliServerProcess_10.241.12.201_null_/var/lib/HPCCSystems/dali']/table/tr/td[1]/input"
 * )).click(); Thread.sleep(5000); System.out.println("Logs Tab enabled");
 * 
 * // Click on Logs Tab
 * driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetailsTabContainer_tablist']/div[4]/div/div[3]"
 * )).click(); Thread.sleep(5000); System.out.println("Logs Tab Clicked"); waitForTargetsLogsTableToLoad();
 * 
 * // Click on Logs Filter
 * driver.findElement(By.id("stub_OPS-DL_Topology-DLDetails_LogsFilterFilterDropDown_label")).click();
 * waitForTargetsLogsTableToLoad(); System.out.println("Logs Filter Pop Up Displayed"); Thread.sleep(3000);
 * 
 * // Enter the values in in logs filter set dialog
 * driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsFirstNRows']")).sendKeys("10");
 * 
 * driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsLastNRows']")).sendKeys("100"); // Select
 * From Time
 * driver.findElement(By.xpath(".//*[@id='widget_stub_OPS-DL_Topology-DLDetails_LogsFromTime']/div[1]/input")).click();
 * driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsFromTime_popup']/div[37]/div")).click();
 * Thread.sleep(3000); // Select To Time
 * driver.findElement(By.xpath(".//*[@id='widget_stub_OPS-DL_Topology-DLDetails_LogsToTime']/div[1]/input")).click();
 * driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsToTime_popup']/div[61]/div")).click();
 * Thread.sleep(3000);
 * 
 * driver.findElement(By.xpath(".//*[@id='stub_OPS-DL_Topology-DLDetails_LogsLastNHours']")).sendKeys("6");
 * 
 * // Click on Apply Button
 * driver.findElement(By.id("stub_OPS-DL_Topology-DLDetails_LogsFilterFilterApply_label")).click();
 * System.out.println("Filter Applied"); Thread.sleep(5000); waitForTargetLogsFilterToLoad();
 * 
 * }
 * 
 * }
 */