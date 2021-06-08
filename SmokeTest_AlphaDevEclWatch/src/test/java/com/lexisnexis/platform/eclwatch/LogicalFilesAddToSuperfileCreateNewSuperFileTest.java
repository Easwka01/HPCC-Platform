package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class LogicalFilesAddToSuperfileCreateNewSuperFileTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFilesAddToSuperfileCreateNewSuperFileTest() throws Exception {
		String expectedTitle = "ECL Watch";
		// Login to Ecl Watch
		driver.navigate().to(appUrl);
		Thread.sleep(3000);

		// Login
		driver.findElement(By.id("username")).sendKeys(properties.getProperty("Username"));
		driver.findElement(By.id("password")).sendKeys(properties.getProperty("Password"));
		driver.findElement(By.id("button")).click();
		Thread.sleep(2000);

		// Click Files Icon
		driver.findElement(By.xpath(".//*[@id='stubStackController_stub_Files']/span[1]")).click();
		Thread.sleep(8000);
		System.out.println("Files Icon Clicked");

		// Click on pop up message
		driver.findElement(By.xpath(".//*[@id='hpcc_toaster']/div/div")).click();
		System.out.println("Files pop up clicked");

		// Click on Filter Drop Down
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterDropDown")).click();
		Thread.sleep(2000);
		System.out.println("Filter Icon Clicked");
		// waitForLogicalFileFilterBoxToLoad();
		System.out.println("Filter Box Loaded");

		// Enter Owner in Filter Box
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLOwner")).sendKeys(properties.getProperty("Owner1"));
		Thread.sleep(2000);

		// Click on Apply
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterApply_label")).click();
		waitForLogicalFileFilterOwnerToLoad();
		Thread.sleep(5000);
		// assertEquals("varani",
		// driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-hthor__dev_eclagent_2--.::adv:corp:event:sample']/table/tr/td[6]")).getText().trim());

		// Click on File
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-hthor__dev_eclagent_2--.::afile2']/table/tr/td[1]")).click();
		System.out.println("afile file selected");
		Thread.sleep(4000);

		// Click on Add to Superfile drop down
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLAddtoDropDown']/span[3]")).click();
		System.out.println("add to superfile drop down clicked");
		Thread.sleep(2000);

		// enter create new superfile name
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLAddToSuperfileTargetName")).clear();
		Thread.sleep(3000);
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLAddToSuperfileTargetName")).sendKeys("NewSuperFileTest");

		// Click on Add button
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLAddToSuperfileForm']/div/span/span")).click();
		System.out.println("created new super file");
		Thread.sleep(3000);
		assertEquals(".::newsuperfile", driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row---.::newsuperfile']/table/tr/td[5]/a")).getText().trim());

		// Click on newsuperfile link
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row---.::newsuperfile']/table/tr/td[5]/a")).click();

		// Select file to remove subfile
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DL_xxxxxnewsuperfile-DLSubfilesGrid-row-.::afile2']/table/tr/td[1]")).click();
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_xxxxxnewsuperfile-DLRemove")).click();

	}

}
