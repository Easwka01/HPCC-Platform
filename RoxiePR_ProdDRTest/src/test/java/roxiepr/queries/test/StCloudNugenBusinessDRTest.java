package roxiepr.queries.test;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.lexisnexis.qatools.testharness.Timeout;
import com.lexisnexis.qatools.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class StCloudNugenBusinessDRTest extends TestBaseRoxiePRQATools {

	@Test
	@Timeout(group = Group.SLOW)
	public void testStCloudNugenBusinessDRTest() throws Exception {
		String expectedTitle = "QATools Application";

		// Login to QA tools Application
		driver.navigate().to(appUrl);
		Thread.sleep(2000);
		driver.findElement(By.name("username")).sendKeys(properties.getProperty("app.user"));
		driver.findElement(By.name("password")).sendKeys(properties.getProperty("app.pass"));
		driver.findElement(By.name("btnSignIn")).click();
		System.out.println("Home page displayed");
		Thread.sleep(2000);

		// Select St Cloud group
		new Select(driver.findElement(By.id("usergroup"))).selectByVisibleText("St Cloud");
		Thread.sleep(5000);

		// Select Template Tab
		driver.findElement(By.xpath("//*[@id='tabs']/li[4]/a")).click();
		Thread.sleep(5000);
		System.out.println("clicked on template tab");

		// Select Compare Template Job
		driver.findElement(By.xpath("//*[@id='job-wizard-submit_current_compare4712']/span")).click();
		Thread.sleep(2000);
		System.out.println("clicked on compare template tab");

		// Task Wizard
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[2]/div/div/button[2]")).click();
		Thread.sleep(2000);
		System.out.println("clicked task next button");

		// Task Product
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[2]/div/div/button[2]")).click();
		Thread.sleep(2000);
		System.out.println("clicked product button");

		// Task Type
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[2]/div/div/button[2]")).click();
		Thread.sleep(4000);

		/*
		 * // Click Server link driver.findElement(By.id("service-142-server-count-ones")).click();
		 * 
		 * System.out.println("Server page loaded"); Thread.sleep(10000);
		 * 
		 * this.processServerListScreen(); // System.out.println("Selected cert server");
		 * 
		 * Thread.sleep(10000); }
		 * 
		 * private void processServerListScreen() throws InterruptedException { List<WebElement> allRows =
		 * driver.findElements(By.xpath("//*[@id='server-table']/tbody/tr")); for (WebElement row : allRows) {
		 * List<WebElement> cells = row.findElements(By.tagName("td")); if (cells.size() == 9) { if
		 * (cells.get(0).getText().equalsIgnoreCase("http://10.173.115.101:8002/roxie_115/")) { WebElement input =
		 * cells.get(8).findElement(By.tagName("div")); input.click(); } } }
		 * 
		 * // Click the close button driver.findElement(By.xpath("//*[@id='server']/div/div[3]/button")).click();
		 * Thread.sleep(5000); System.out.println("server table closed");
		 */

		// Service Type
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[2]/div/div/button[2]")).click();
		Thread.sleep(10000);
		System.out.println("clicked service button");

		// Compare Type
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[2]/div/div/button[2]")).click();
		Thread.sleep(6000);
		System.out.println("clicked compare button");

		// Schedule
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[2]/div/div/button[2]")).click();
		Thread.sleep(2000);
		System.out.println("clicked schudule button");

		// Enter Submit credentials
		driver.findElement(By.id("job-description")).sendKeys(properties.getProperty("app.descriptionStCloudNugenBusinessDR"));
		driver.findElement(By.id("job-user_id")).sendKeys(properties.getProperty("ecl.user"));
		driver.findElement(By.id("job-user_password")).sendKeys(properties.getProperty("ecl.pass"));
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[2]/div/div/button[2]")).click();
		System.out.println("clicked submit button");
		Thread.sleep(10000);

		// Click on Done Button
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div[2]/form/div/div[1]/div[8]/a[2]")).click();
		System.out.println("Done button clicked");
		Thread.sleep(4000);

		// Verify the running table
		waitForJobstatusTableToLoad();
		System.out.println("Page Loaded");

	}

}
