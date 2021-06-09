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
 * @RunWith(BlockJUnit4ClassRunner.class) public class TopologyTargetsPreflightTest extends TestBase {
 * 
 * @Test
 * 
 * @Timeout(group = Group.SLOW) public void testTargetsPreflightTest() throws Exception { // String expectedtitle =
 * "ECL Watch"; // Login to Ecl Watch driver.navigate().to(appUrl); Thread.sleep(3000); // Click On Operations Link
 * driver.findElement(By.id("stubStackController_stub_OPS")).click(); System.out.println("Topology page loaded");
 * Thread.sleep(3000); waitForTopologyPageLoad(); // Click on Hole Cluster String path =
 * "/html/body/div[7]/div/div[2]/div[5]/div/div/div/div[2]/div[1]/div/div/div/div/div[3]/div/div/div[3]/div/div[2]/div/div[2]/table/tr/td[2]/div"
 * ; driver.findElement(By.xpath(path)).click(); Thread.sleep(5000);
 * System.out.print("Hole Cluster Arrow button clicked"); // Enable checkbox driver.findElement(By.xpath(
 * "/html/body/div[7]/div/div[2]/div[5]/div/div/div/div[2]/div[1]/div/div/div/div/div[3]/div/div/div[3]/div/div[2]/div/div[3]/div[3]/table/tr/td[1]/input"
 * )).click(); Thread.sleep(5000); System.out.println("Macine Info enabled"); // Click on Machine Information
 * driver.findElement(By.id("FilterDropDownWidget_0FilterDropDown")).click(); waitForMachineInformationDialogBoxLoad();
 * System.out.println("Macine Info displayed"); Thread.sleep(5000);
 * 
 * // Click on Apply Button driver.findElement(By.id("FilterDropDownWidget_0FilterApply_label")).click();
 * System.out.println("Filter Applied"); Thread.sleep(2000); // Click on Preflight hthor_sta tab
 * driver.findElement(By.xpath
 * (".//*[@id='stub_OPS-DL_Topology-DLDetailsTabContainer_tablist_stub_OPS-DL_Topology-DLDetails_RequestInformation']"
 * )).click(); System.out.println("Preflight selected");
 * 
 * // Click on Fetched hthor staging driver.findElement(By.xpath(
 * ".//*[@id='stub_OPS-DL_Topology-DLDetails_RequestInformationTabContainer_tablist']/div[4]/div/div[2]")).click();
 * System.out.println("Preflight results load"); Thread.sleep(10000); //
 * waitForElementToLoad(By.id("stub_OPS-DL_Topology-DL_hthorxsta02x26x18x22x46x52BorderContainer"));
 * 
 * waitForTargetsPreflightResultsTable(); System.out.println("element loaded"); //
 * assertEquals("10.241.20.187 /var/lib/HPCCSystems/staging_eclserver/", // driver.findElement(By.xpath(
 * "//*[@id='stub_OPS-DL_Topology-DL_hthorxsta02x24x18x00x05x48Grid-row-undefined']/table/tr/td[1]")).getText().trim());
 * // assertEquals("10.241.12.205 /var/lib/HPCCSystems/staging_eclagent/", // driver.findElement(By.xpath(
 * "//*[@id='stub_OPS-DL_Topology-DL_hthorxsta02x24x18x00x05x48Grid-row-undefined']/table/tr/td[2]")).getText().trim());
 * // assertEquals("10.241.20.187 /var/lib/HPCCSystems/sta_eclscheduler/", // driver.findElement(By.xpath(
 * "//*[@id='stub_OPS-DL_Topology-DL_hthorxsta02x24x18x00x05x48Grid-row-undefined']/table/tr/td[3]")).getText().trim());
 * 
 * }
 * 
 * }
 */