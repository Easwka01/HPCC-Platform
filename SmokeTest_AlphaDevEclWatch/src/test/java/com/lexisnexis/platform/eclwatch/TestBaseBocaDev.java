package com.lexisnexis.platform.eclwatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Robot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Predicate;
import com.lexisnexis.platform.common.TestConstants;
import com.lexisnexis.platform.eclwatch.testharness.TestHarness;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

public class TestBaseBocaDev extends TestHarness {

	protected void doLogin() {

		// Go to home page
		driver.navigate().to(appUrl);

	}

	/*
	 * protected void choose() throws InterruptedException {
	 * 
	 * new Select(driver.findElement(By.id("clientId"))).selectByVisibleText(""); Thread.sleep(2000);
	 * driver.findElement(By.id("btnSubmit")).click(); //
	 * wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSubmit"))).click();
	 * waitForScreenTitle("Dataset Selection"); }
	 */

	/**
	 * Verifies current page is the dataset selection; Chooses the configured dataset, and the specified entry point.
	 * Verifies the page transitions to the correct entrypoint, fails otherwise.
	 *
	 * @param entryPoint
	 * @throws InterruptedException
	 */
	/*
	 * protected void chooseDatasetAndEntryPoint(final String entryPoint) throws InterruptedException {
	 * 
	 * if ("Selection".equals(driver.findElement(By.id("_divScreenTitle")).getText())) { chooseOption(); }
	 * 
	 * new Select(driver.findElement(By.id("Name"))).selectByVisibleText(data Type); new
	 * Select(driver.findElement(By.id("dsId"))).selectByVisibleText(Name); new
	 * Select(driver.findElement(By.id("screen"))).selectByVisibleText(entryPoint);
	 * 
	 * Thread.sleep(2000); driver.findElement(By.id("btnSubmit")).click();
	 * 
	 * // wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("btnSubmit"))).click();
	 * waitForScreenTitle(entryPoint); expectPageTitle(TestConstants.PageTitle.PREFIX, entryPoint); }
	 */

	protected void doLogout() {
		driver.findElement(By.id("menuAppsLogOut")).click();
		driver.findElement(By.xpath("(//button[@type='button'])[7]")).click();
		assertEquals("abc", driver.getTitle());
		assertEquals("You have successfully logged out.", driver.findElement(By.xpath("//div[@id='iiEntryPointFormBox']/div")).getText());
	}

	/**
	 * Asserts that the title of the current page matches the parts passed in. Not that this method is not a "wait"
	 * method -- no waiting is done, and if the title does not match at the instant this method is called, the assert
	 * will fail.
	 *
	 * @param parts
	 *            Parts are joined together with spaces between them to form a final expected title. Use multiple parts
	 *            for common prefixes and known suffixes.
	 */
	protected void expectPageTitle(final String... parts) {
		final String expectedTitle = String.join(" ", parts);
		assertEquals(expectedTitle, driver.getTitle());
	}

	protected void waitUntil(final Predicate<WebDriver> predicate) {

		final Group group = this.timeoutRule.getGroup();
		final long timeoutSeconds = this.timeoutGroups.get(group);
		final WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutSeconds);
		webDriverWait.until(predicate);
	}

	/**
	 * Waits for the contents of a tag to match a value. If after the timeout value it does not match, an assertion will
	 * fail.
	 *
	 * @param elementId
	 *            The id of the element to wait for.
	 * @param textValue
	 *            The expected value.
	 */
	protected void waitForText(final String elementId, final String textValue) {
		waitUntil(driver -> {
			try {
				return textValue.equals(driver.findElement(By.id(elementId)).getText());
			} catch (final Exception e) {
				return false;
			}
		});

		assertEquals(textValue, driver.findElement(By.id(elementId)).getText());
	}

	protected void waitForTopologyText(final String elementId, final String textValue) {
		waitUntil(driver -> {
			try {
				return textValue.equals(driver.findElement(By.id(elementId)).getText());
			} catch (final Exception e) {
				return false;
			}
		});

		assertEquals(textValue, driver.findElement(By.id(elementId)).getText());
	}

	/**
	 * Waits for the contents of a tag to match a value. If after the timeout value it does not match, an assertion will
	 * fail.
	 *
	 * @param xPathExpression
	 *            The xPathExpression of the element to wait for.
	 * @param textValue
	 *            The expected value.
	 */
	protected void waitForTextByPath(final String xPathExpression, final String textValue) {
		waitUntil(driver -> {
			try {
				return textValue.equals(driver.findElement(By.xpath(xPathExpression)).getText());
			} catch (final Exception e) {
				return false;
			}
		});

		assertEquals(textValue, driver.findElement(By.xpath(xPathExpression)).getText());
	}

	protected void waitForDependentDropdownText(final String elementId, final String textValue) {
		waitUntil(driver -> {
			try {
				return textValue.equals(new Select(driver.findElement(By.id(elementId))).getFirstSelectedOption().getText());
			} catch (final Exception e) {
				return false;
			}
		});

		assertEquals(textValue, new Select(driver.findElement(By.id(elementId))).getFirstSelectedOption().getText());
	}

	/**
	 * Waits for the contents of a tag to match a value. If after the timeout value it does not match, an assertion will
	 * fail. A more generic version of waitForText(String, String)
	 *
	 * @param element
	 *            A By.xxx() clause that will match a tag in the document.
	 * @param textValue
	 *            The expected value.
	 */
	protected void waitForText(final By element, final String textValue) {
		waitUntil(driver -> {
			try {
				return textValue.equals(driver.findElement(element).getText());

			} catch (final Exception e) {
				return false;
			}
		});

		assertEquals(textValue, driver.findElement(element).getText());
	}

	/**
	 * Wait for the Screen title (the part located in the white bar with the icons) to match the parameter value. Note
	 * that waiting is necessary as opposed to just expecting, because this is updated dynamically on ajax update.
	 *
	 * @param title
	 */

	protected void waitForScreenTitle(final String title) {
		waitForText("stubUserDialog_title", title);
	}

	/**
	 * Wait for the Screen title (the part located in the bar with the icons) to match the parameter value. Note that
	 * waiting is necessary as opposed to just expecting, because this is updated dynamically on ajax update.
	 *
	 * @param xpath
	 * @param title
	 */

	protected void waitForScreenTitleByXpath(final String xPath, final String textValue) {
		waitForTextByPath(xPath, textValue);
	}

	/**
	 * Wait for an element to appear in the document. If not found after configured time, an assertion will fail.
	 *
	 * @param element
	 *            The element to wait for.
	 * @return The found element. Guaranteed to be not null, as null will fail an assertion.
	 */
	protected WebElement waitForElementToLoad(final By element) {
		waitUntil(driver -> {
			try {
				return driver.findElement(element) != null;
			} catch (final Exception e) {
				return false;
			}
		});

		final WebElement found = driver.findElement(element);

		assertNotNull(String.format("Expected element [{}] to be present", element), found);

		return found;
	}

	protected WebElement waitForPreflightResultsToLoad(final By element) {
		waitUntil(driver -> {
			try {
				return driver.findElement(element) != null;
			} catch (final Exception e) {
				return false;
			}
		});

		final WebElement found = driver.findElement(element);

		assertNotNull(String.format("Expected element [{}] to be present", element), found);

		return found;
	}

	protected WebElement waitForSlaveLogsPopUpBox(final By element) {
		waitUntil(driver -> {
			try {
				return driver.findElement(element) != null;
			} catch (final Exception e) {
				return false;
			}
		});

		final WebElement found = driver.findElement(element);

		assertNotNull(String.format("Expected element [{}] to be present", element), found);

		return found;
	}

	/**
	 * Waits for a data table to load. When loaded, compares row quantity to expected values. If it does not load within
	 * the configured timeout range, or the row count is not as expected, an assertion will fail.
	 *
	 * @param minExpectedTotalRows
	 *            The minimum number of total rows the table should have.
	 * @param minExpectedLoadedRows
	 *            The minimum number of rows that should be loaded into the table upon display.
	 * @param tableId
	 *            The id of the table element.
	 * @return The table element.
	 * 
	 */

	protected WebElement waitForTargetsLogsTableToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Topology-DLDetails_LogsLogGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Topology-DLDetails_LogsLogGrid"));
	}

	protected WebElement waitForLogicalFilesDFUWUIDFilterToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Workunits-DLWorkunitsGrid-row-D20180328-131151")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Workunits-DLWorkunitsGrid-row-D20180328-131151"));
	}

	protected WebElement waitForPublishedQueriesSuspendAlphaDevSuspendTableToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid"));
	}

	protected WebElement waitForPublishedQueriesSuspendAlphaDevUnSuspendTableToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetContentToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Content-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Content-DLBorderContainer"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetECLToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Source-DLEclContent']/div/div[6]/div[1]/div/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Source-DLEclContent']/div/div[6]/div[1]/div/div"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetDEFToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_DEF-DLEclContent']/div/div[6]/div[1]/div/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_DEF-DLEclContent']/div/div[6]/div[1]/div/div"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetXMLToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_XML-DLEclContent']/div/div[6]/div[1]/div/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_XML-DLEclContent']/div/div[6]/div[1]/div/div"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetFilePartsToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_FileParts-DLFilePartsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_FileParts-DLFilePartsGrid"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetQueriesToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Queries-DL_PublishedQueries")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Queries-DL_PublishedQueries"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetHistoryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_FileHistory-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_FileHistory-DL_Grid"));
	}

	protected WebElement waitForLogicalFilesDFUWuidTargetWorkunitToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_DFUWorkunit-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_DFUWorkunit-DL_Summary"));
	}

	protected WebElement waitForLogicalFilesDFUWUIDTargetSummaryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DL_DestLogicalName-DL_Summary"));
	}

	protected WebElement waitForLogicalFilesDFUWUIDOpenLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Workunits-DL_D20180328x131151-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Workunits-DL_D20180328x131151-DL_Summary"));
	}

	protected WebElement waitForLogicalFilesDFUWUIDXMLToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_XML-DLEclContent']/div/div[6]/div[1]/div/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailD20180404x102850-DL_XML-DLEclContent']/div/div[6]/div[1]/div/divo"));
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement waitForLogicalFilesHistoryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLxxxadvtest-DL_FileHistory-DLGrid-row-ECrash_Prod_0.csv']/table")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLxxxadvtest-DL_FileHistory-DLGrid-row-ECrash_Prod_0.csv']/table"));
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected WebElement waitForLogicalFilesLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForTargetLogsFilterToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Topology-DLDetails_LogsLogGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Topology-DLDetails_LogsLogGrid"));
	}

	protected WebElement waitForMachineInformationRequestInformation() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Topology-DLDetails_RequestInformation_RequestDetails")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Topology-DLDetails_RequestInformation_RequestDetails"));
	}

	protected WebElement waitForLandingZonesDataTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LandingZones-DLTabContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LandingZones-DLTabContainer"));
	}

	protected WebElement waitForSlaveLogsTableToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='hpcc_TableContainer_4']/table")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='hpcc_TableContainer_4']/table"));
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement waitForServicesPreflightResultsTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Topology-DL_10x241x12x201Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Topology-DL_10x241x12x201Grid"));
	}

	protected WebElement waitForFileSearchResultTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DLGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DLGrid"));
	}

	protected WebElement waitForAlphaDevFileSearchResultTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DLGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DLGrid"));
	}

	// /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected WebElement waitForTargetsPreflightResultsTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Topology-DL_hthorxsta02x28x18x03x00x03Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No table found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Topology-DL_hthorxsta02x28x18x03x00x03Grid"));
	}

	protected WebElement waitForMachinesPreflightResultsTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Topology-DL_10x48x72x194Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Topology-DL_10x48x72x194Grid"));
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement waitForTopologyPageLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Topology-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Topology-DLBorderContainer"));
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected WebElement waitForOperationsDiskUsagePageLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_DiskUsage-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_DiskUsage-DLBorderContainer"));
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected WebElement waitForOperationsClusterProcessesPageLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_ClusterProcessesQuery-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_ClusterProcessesQuery-DLBorderContainer"));
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected WebElement waitForOperationsTargetClustersPageLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_TargetClustersQuery-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_TargetClustersQuery-DLBorderContainer"));
	}

	// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected WebElement waitForOperationsDynamicESDLPageLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_DESDL-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_DESDL-DLBorderContainer"));
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	protected WebElement waitForOperationsSystemServersPageLoadAlphaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("sstub_OPS-DL_SystemServersQuery-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_SystemServersQuery-DLBorderContainer"));
	}

	// //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	protected WebElement TargetClustersPageLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stubBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stubBorderContainer"));
	}

	protected WebElement waitForPrimaryMonitoringDataTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_OPS-DL_Monitoring-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_OPS-DL_Monitoring-DLBorderContainer"));
	}

	protected WebElement waitForWUIDSlaveLogsDialogBox() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='dijit_TooltipDialog_1']/div[1]")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='dijit_TooltipDialog_1']/div[1]"));
	}

	protected WebElement waitForPublishedQueriesOptionsDataTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLBorderContainer"));
	}

	protected WebElement waitForPublishedQueriesPageLoadTableAlphaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DL_PublishedQueries")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DL_PublishedQueries"));
	}

	protected WebElement waitForPublishedQueriesOptionDropDownAlphaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DLOptionsForm")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLOptionsForm"));
	}

	protected WebElement waitForPublishedQueriesOptionCheckSingleNodeAlphaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid"));
	}

	protected WebElement waitForPublishedQueriesOptionCheckAllNodesAlphaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid"));
	}

	protected WebElement waitForPublishedQueriesPageLoadTableBocaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DL_PublishedQueries")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DL_PublishedQueries"));
	}

	protected WebElement waitForPublishedQueriesPublishedByMeTableAlphaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_RoxieQueries-DL_Queries-DLQuerySetGrid"));
	}

	protected WebElement waitForLogicalFilesDataTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DLGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DLGrid"));
	}

	protected WebElement waitForHistoryInformationTabLoaded() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_FileHistory-DL_Grid"));
	}

	protected WebElement waitForAlphaDevHistoryInformationTabLoaded() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLthorxxplatformxtestxxtestx13264xxmlxfixedxcompressed-DL_FileHistory-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLthorxxplatformxtestxxtestx13264xxmlxfixedxcompressed-DL_FileHistory-DL_Grid"));

	}

	protected WebElement waitForDFUWorkunitSummaryTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailD20180404x102850-DLBorderContainer"));
	}

	protected WebElement waitForLogicalFiles_File_SummaryTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Summary"));
	}

	protected WebElement waitForLogicalFiles_File_ECLTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Source-DLEclContent']/div/div[6]/div[1]/div/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Source-DLEclContent']/div/div[6]/div[1]/div/div"));
	}

	protected WebElement waitForLogicalFiles_File_DEFTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_DEF-DLEclContent']/div/div[6]/div[1]/div/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_DEF-DLEclContent']/div/div[6]/div[1]/div/div"));
	}

	protected WebElement waitForLogicalFiles_File_XMLTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_XML-DLEclContent']/div/div[6]/div[1]/div/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_XML-DLEclContent']/div/div[6]/div[1]/div/div"));
	}

	protected WebElement waitForLogicalFiles_File_FilePartsTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_FileParts-DLFilePartsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_FileParts-DLFilePartsGrid"));
	}

	protected WebElement waitForLogicalFiles_File_QueriesTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Queries-DLQuerySetGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Queries-DLQuerySetGrid"));
	}

	protected WebElement waitForLogicalFiles_File_ContentTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLBorderContainer"));
	}

	protected WebElement waitForMachineInformationDialogBoxLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("FilterDropDownWidget_0TableContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("FilterDropDownWidget_0TableContainer"));
	}

	protected WebElement waitForXrefTableToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DLGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DLGrid"));
	}

	protected WebElement waitForLogicalFiles_File_ContentFilterTable() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentxxxxxadvtest1-DL_Content-DLGrid"));
	}

	protected WebElement waitForXrefSummaryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_Summary"));
	}

	protected WebElement waitForXrefSummaryToLoadAlphaDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_Summary"));
	}

	protected WebElement waitForLogicalFilesOpenSummaryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentx2xxxxxadvtest-DL_Summary"));
	}

	protected WebElement waitForAlphaDevLogicalFilesOpenSummaryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentx1xxthorxxplatformxtestxxtestx13264xxmlxfixedxcompressed-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DL_hthorxxdevxeclagentx1xxthorxxplatformxtestxxtestx13264xxmlxfixedxcompressed-DL_Summary"));
	}

	protected WebElement waitForXrefFoundFileToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DLTabContainer_tablist_stub_Files-DL_Xref-DL_thor400xdev01-DL_FoundFiles")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DLTabContainer_tablist_stub_Files-DL_Xref-DL_thor400xdev01-DL_FoundFiles"));
	}

	protected WebElement waitForXrefOrphanFilesToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_OrphanFiles-DLGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_OrphanFiles-DLGrid"));
	}

	protected WebElement waitForXrefLostFileToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_LostFiles-DLGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_LostFiles-DL_Grid"));
	}

	protected WebElement waitForLogicalFileFilterNameToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForFiles_LogicalFilesToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFilesFilterClearAndApplyToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFilesViewByScopeToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFileFilterOwnerToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFileFilterIndexToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFileFilterSizesToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFilterFileTypeLogicalFilesOnlyToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFilterFileTypeSuperFilesOnlyToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFilterFileFilterDateToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFilterFileTypeNotInSuperfilesOnlyToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFilterFileTypeSuperFilesAndLogicalFilesToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFileFilteClusterToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_LogicalFiles-DLWorkunitsGrid"));
	}

	protected WebElement waitForLogicalFileFilterBoxToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLFilterTableContainer']/table/tbody/tr[3]/td[2]")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath(".//*[@id='stub_Files-DL_LogicalFiles-DLFilterTableContainer']/table/tbody/tr[3]/td[2]"));
	}

	protected WebElement waitForXreDirectoriesToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_Directories-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_Directories-DL_Grid"));
	}

	protected WebElement waitForXreDirectoriesToLoadAlphDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_Directories-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_Directories-DL_Grid"));
	}

	protected WebElement waitForXreErrorWarningToLoadAlphDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_Errors-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_Errors-DL_Grid"));
	}

	protected WebElement waitForXreFFoundFileToLoadAlphDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_FoundFiles-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_FoundFiles-DL_Grid"));
	}

	protected WebElement waitForXrefLostFileToLoadAlphDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_LostFiles-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_LostFiles-DL_Grid"));
	}

	protected WebElement waitForXrefOrphanFileToLoadAlphDev() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_OrphanFiles-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400x126-DL_OrphanFiles-DL_Grid"));
	}

	protected WebElement waitForXreErrorAndWarningToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_Errors-DL_Grid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Files-DL_Xref-DL_thor400xdev01-DL_Errors-DL_Grid"));
	}

	protected WebElement waitForLogicalFileSummaryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLplatformxxtestxxcompression-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailstubxMainxDLxSearchxDLplatformxxtestxxcompression-DL_Summary"));
	}

	protected WebElement waitForSuperFileSummaryToLoad() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_xxadvxxcompressionxxtest-DL_Summary")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_Main-DL_Search-DL_DetailLogicalxFile-DL_xxadvxxcompressionxxtest-DL_Summary"));
	}

	protected WebElement waitForArchivedWorkunitOnly() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_ECL-DL_Workunits-DLWorkunitsGrid")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_ECL-DL_Workunits-DLWorkunitsGrid"));
	}

	/**
	 * Assert that all the values in a table column match an expected value. If any rows do not match, an assertion will
	 * fail.
	 *
	 * @param table
	 *            The table element to check.
	 * @param columnIndex
	 *            The index of the column to check. 1-based.
	 * @param expectedValue
	 *            The value that all rows of the table should have in the specified column.
	 */
	protected void assertColumnValues(final WebElement table, final int columnIndex, final String expectedValue) {
		// assert that each row matches the search parameter
		final List<WebElement> cells = table.findElements(By.xpath("//tr/td[" + columnIndex + "]"));
		cells.stream().map(cell -> cell.getText()).forEach(value -> {
			if (value != null && value.trim().length() > 1) {
				assertEquals(expectedValue, value);
			}
		});
	}

	/**
	 * Assert that all the values in the result table column(s) match an expected value for a given row, for percent
	 * calculation,
	 *
	 * @param table
	 *            The table element to check.
	 * @param columnIndex1
	 *            The index of the column to check. 1-based.
	 * @param columnIndex2
	 *            The index of the column to check. 1-based.
	 * @param resultColumnIndex
	 *            value at resultColumnIndex = ( value at columnIndex1 ) / ( value at columnIndex2 ) * 100
	 */
	protected void assertColumnValuesPercent(final WebElement table, final String columnIndex1, final String columnIndex2, final String resultColumnIndex) {
		// assert that for the first row, the percentage value matches the
		// calculation of right percentage

		int row = 0; // first row , can be parameterized

		final WebElement cell1 = table.findElement(By.xpath("//*[@id=" + row + "]/td[" + columnIndex1 + "]"));

		final WebElement cell2 = table.findElement(By.xpath("//*[@id=" + row + "]/td[" + columnIndex2 + "]"));

		final WebElement expected = table.findElement(By.xpath("//*[@id=" + row + "]/td[" + resultColumnIndex + "]"));

		if (cell1 != null && cell2 != null && expected != null) {
			double cellone = Double.valueOf(cell1.getText());
			double celltwo = Double.valueOf(cell2.getText());
			Double truncatedDouble = 0.0;
			if (celltwo != 0) {
				// truncatedDouble = BigDecimal.valueOf((cellone / celltwo) *
				// 100).setScale(2,
				// RoundingMode.CEILING).doubleValue();
				truncatedDouble = (double) Math.round((cellone / celltwo) * 100);
			} else {
				System.out.println("Cannot divide by zero.");
			}
			System.out.println("cellone: " + cellone + ", celltwo: " + celltwo + " ,expected : " + Double.valueOf(expected.getText()) + ", actual : " + truncatedDouble);

			assertEquals(truncatedDouble, Double.valueOf(expected.getText()), 0.0);
		} else {
			fail("One of the input param is null.");
		}

	}

	/**
	 * Assert that all the values in a table column fall with in an expected Range. If any rows do not match, an
	 * assertion will fail.
	 *
	 * @param table
	 *            The table element to check.
	 * @param columnIndex
	 *            The index of the column to check. 1-based.
	 * @param expectedValue
	 *            The value that all rows of the table should have in the specified column.
	 * @return
	 */
	protected void assertRangeColumnValues(final WebElement table, final int columnIndex, final String expectedToValue, final String expectedFromValue) {
		Date fromDate = formatDate(expectedFromValue);
		Date toDate = formatDate(expectedToValue);
		final List<WebElement> cells = table.findElements(By.xpath("//tr/td[" + columnIndex + "]"));
		cells.stream().map(cell -> cell.getText()).forEach(value -> {
			if (value != null && value.trim().length() > 1) {
				Date acualDate = formatDate(value.trim());
				System.out.println(acualDate.compareTo(fromDate));
				System.out.println(value);
				assertEquals(true, acualDate.compareTo(fromDate) * toDate.compareTo(acualDate) >= 0);
			}
		});
	}

	private Date formatDate(String str) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		try {
			return sdf.parse(str);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Assert that all the values in a table column "contain" an expected value. If any rows do not match, an assertion
	 * will fail.
	 *
	 * @param table
	 *            The table element to check.
	 * @param columnIndex
	 *            The index of the column to check. 1-based.
	 * @param expectedValue
	 *            The value that all rows of the table should have in the specified column.
	 */

	protected void assertColumnValuesContains(final WebElement table, final int columnIndex, final String expectedValue) {
		// assert that each row column "contains" the search parameter
		final List<WebElement> cells = table.findElements(By.xpath(".//*[@id='stub_OPS-DL_Topology-DL_10x241x30x201Grid-header']/tr/th[" + columnIndex + "]" + "/div"));
		/*
		 * .//*[@id='stub_OPS-DL_Topology-DL_10x241x30x201Grid']
		 * .//*[@id='stub_OPS-DL_Topology-DL_10x241x30x201Grid-header']/tr/th[1]/div
		 * .//*[@id='stub_OPS-DL_Topology-DL_10x241x30x201Grid-header']/tr/th[2]/div
		 */cells.stream().map(cell -> cell.getText()).forEach(value -> {
			if (value != null && value.trim().length() > 1) {
				assertTrue(StringUtils.containsIgnoreCase(value, expectedValue));
			}
		});
	}

	/**
	 * Perform a double click on a cell in a data table.
	 *
	 * @param dataTable
	 *            The data table to click.
	 * @param row
	 *            The row number of the cell to click.
	 * @param columnNo
	 *            The column number of the cell to click, where zero is the row number column, and one is the first data
	 *            column.
	 */

	protected void doubleClickByRowAndColumn(final WebElement dataTable, final int row, final int columnNo) {
		// performs double click action
		final List<WebElement> rows = dataTable.findElements(By.tagName("tr"));
		final List<WebElement> columns = rows.get(row).findElements(By.tagName("td"));
		final Actions action = new Actions(driver);
		action.doubleClick(columns.get(columnNo)).perform();

	}

	/**
	 * Hover over an element on the page. Note: This only works if the browser is in the foreground, not minimized, and
	 * you're not moving your mouse at the same time.
	 *
	 * @param element
	 *            The element to hover over.
	 */
	protected void hover(final WebElement element) {
		final Actions action = new Actions(driver);
		action.moveToElement(element).build().perform();
	}

	/**
	 * Select an item from the entry point menu to navigate to that page.
	 *
	 * @param entryPoint
	 *            The entry point to go to (Use TestConstants.EntryPoints)
	 */
	protected void useEntryPointMenu(final String entryPoint) {

		final WebElement menuIcon = driver.findElement(By.id("iconEntryPoint"));
		hover(menuIcon);

		final WebElement menu = driver.findElement(By.id("entryPoints"));

		// Tricky because these links contain imgs too
		// http://stackoverflow.com/questions/14570857/retrieve-an-xpath-text-contains-using-text
		final WebElement link = menu.findElement(By.xpath("li/a[contains(.,'" + entryPoint + "')]"));

		link.click();

		waitForScreenTitle(entryPoint);
		expectPageTitle(TestConstants.PageTitle.PREFIX, entryPoint);
	}

	protected void waitForTextBySelector(final String element, final String textValue) {
		waitUntil(driver -> {
			try {
				return textValue.equals(driver.findElement(By.cssSelector(element)).getText());

			} catch (final Exception e) {
				return false;
			}
		});

		assertEquals(textValue, driver.findElement(By.cssSelector(element)).getText());
	}

	/**
	 * checks for a particular column to be present on the grid.
	 *
	 * @param table
	 *            The data table to click.
	 * @param expectedHeaderValue
	 *            The column header to be present on grid
	 */

	protected boolean checkColumnHeader(final WebElement table, final String expectedHeaderValue) {

		final WebElement headerRow = table.findElement(By.xpath("//table/thead/tr"));
		final List<WebElement> columns = headerRow.findElements(By.tagName("th"));
		boolean isExists = false;

		for (WebElement column : columns) {
			je.executeScript("arguments[0].scrollIntoView(true);", column);
			String value = column.findElement(By.tagName("div")).getText();
			if (value != null && value.trim().equals(expectedHeaderValue)) {
				isExists = true;
				break;
			}
		}

		System.out.println(isExists);

		return isExists;
	}

	/**
	 * Sorts a column of the grid.
	 *
	 * @param table
	 *            The data table to click.
	 * @param headerValue
	 *            The column header to be present on grid
	 * 
	 */

	protected boolean sortByColumnHeader(final WebElement table, final String columnheader) {

		final WebElement headerRow = table.findElement(By.xpath("//table/thead/tr"));
		final List<WebElement> columns = headerRow.findElements(By.tagName("th"));
		boolean isExists = false;

		for (WebElement column : columns) {
			je.executeScript("arguments[0].scrollIntoView(true);", column);
			String value = column.findElement(By.tagName("div")).getText();
			if (value != null && value.trim().equals(columnheader)) {
				column.click();
				isExists = true;

				break;
			}
		}

		System.out.println(isExists);

		return isExists;
	}

	public boolean process() {
		int time = 0;
		do {
			try {
				driver.findElement(By.id("btnSubmit")).click();
				break;
			} catch (Exception e) {
				time++;
				System.out.println("Element not ready");
			}
		} while (time < 25);
		return false;
	}

	public void mouseOverOnElement(WebElement tagElement) {
		try {
			Actions builder = new Actions(driver);
			Point coordinate = tagElement.getLocation();
			Robot robot = new Robot();
			builder.moveToElement(tagElement, 5, 5).build().perform();
			builder.moveByOffset(1, 1).build().perform();
			robot.mouseMove(coordinate.getX() + 8, coordinate.getY() + 60);

		} catch (Exception e) {
			System.out.println("Error message in mouseOverOnElement method-->" + e.getMessage());
		}
	}

	protected WebElement waitForWorkunitspagetoload() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_ECL-DL_Workunits-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_ECL-DL_Workunits-DLBorderContainer"));
	}

	protected WebElement waitForWorkunittoload() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_ECL-DL_Workunits-DL_W20190313x144456-DLBorderContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_ECL-DL_Workunits-DL_W20190313x144456-DLBorderContainer"));
	}

	protected WebElement waitForWorkunittFilterDropDownload() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"dijit_TooltipDialog_0\"]/div[1]/div")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.xpath("//*[@id=\"dijit_TooltipDialog_0\"]/div[1]/div"));
	}

	protected WebElement waitForWorkunittFilterWUIDpageload() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_ECL-DL_Workunits-DLTabContainer")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_ECL-DL_Workunits-DLTabContainer"));
	}

	protected WebElement waitForMineWorkunitload() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_ECL-DL_Workunits-DL_Workunits")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_ECL-DL_Workunits-DL_Workunits"));
	}

	protected WebElement waitForCSVExportDialogload() {

		waitUntil(driver -> {

			try {
				final WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("stub_ECL-DL_Workunits-DLDownloadToListDialog")));
				return true;
			} catch (TimeoutException exec) {

				assertTrue("No data found", false);
				return true;

			}
		});

		return driver.findElement(By.id("stub_ECL-DL_Workunits-DLDownloadToListDialog"));
	}

}
