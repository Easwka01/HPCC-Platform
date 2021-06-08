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
public class LogicalFiles_File_Content_Filter_ClearTest extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.SLOW)
	public void testLogicalFiles_File_Content_Filter_ClearTest() throws Exception {
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
		Thread.sleep(5000);
		System.out.println("Files Icon Clicked");

		// Click on pop-up
		driver.findElement(By.xpath(".//*[@id='hpcc_toaster']/div/div")).click();

		// Click on Filter Drop Down
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterDropDown")).click();
		Thread.sleep(2000);
		System.out.println("Filter Icon Clicked");
		// waitForLogicalFileFilterBoxToLoad();
		System.out.println("Filter Box Loaded");

		// Enter Name in Filter Box
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLName")).sendKeys(properties.getProperty("LogicalFile1"));
		Thread.sleep(2000);

		// Click on Apply
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLFilterFilterApply_label")).click();
		waitForLogicalFileFilterNameToLoad();
		Thread.sleep(2000);
		// assertEquals(".::advtest1",
		// driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-hthor__dev_eclagent_2--.::advtest1']/table/tr/td[5]/a")).getText().trim());

		// Click on File Name CheckBox
		driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLWorkunitsGrid-row-hthor__dev_eclagent--.::advtest1']/table/tr/td[1]/input")).click();
		System.out.println("Logical File check box clicked");

		// Click on Open
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLOpen_label")).click();
		Thread.sleep(10000);
		waitForLogicalFiles_File_SummaryTable();
		assertEquals("Owner:", driver.findElement(By.xpath("//*[@id='stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DLSummaryForm']/ul/li[2]/label")).getText().trim());

		// Click on Contents Tab
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DLTabContainer_tablist_stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content")).click();
		Thread.sleep(5000);
		waitForLogicalFiles_File_ContentTable();

		// Click on filter drop down
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLFilterFilterDropDown")).click();
		System.out.println("Content drop down displayed");

		// Enter Name in Content Filter Drop dowm
		driver.findElement(By.id("dijit_form_TextBox_1")).sendKeys(properties.getProperty("FilePosition"));
		// driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLFilterFilterApply_label")).click();

		// Click on Clear Button
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLFilterFilterClear_label")).click();
		driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLFilterFilterApply_label")).click();
		Thread.sleep(2000);
		System.out.println("Filter value cleared");

	}
}
