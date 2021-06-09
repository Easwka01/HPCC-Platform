package com.lexisnexis.platform.eclwatch.testharness;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Table;
import com.google.common.collect.Tables;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

public class TestHarness {
	private static final Logger log = LoggerFactory.getLogger(TestHarness.class);

	private static ZonedDateTime globalStart;
	private static ZonedDateTime globalEnd;

	protected WebDriver driver;
	protected StopWatch stopWatch;
	private static final Table<Class<? extends TestHarness>, String, List<Pair<String, Long>>> splits = Tables.newCustomTable(new LinkedHashMap<>(), LinkedHashMap::new);

	protected String appUrl;

	private String driverUrl;
	private Browser browser;

	protected String username;
	protected String password;

	protected Properties properties;
	protected Actions action;
	protected WebDriverWait wait;
	public String reportType;
	public String categoryGroup;
	protected JavascriptExecutor je;

	// private final Map<String, Object> testProperties = new TreeMap<>();

	protected final Map<Timeout.Group, Integer> timeoutGroups = new HashMap<>();

	private final String cwd = Paths.get(".").toAbsolutePath().normalize().toString();
	private final String sep = System.getProperty("file.separator", "\\");
	private final String sourcePath = cwd + sep + "target" + sep + "surefire-reports" + sep;

	@Rule
	public TimeoutRule timeoutRule = new TimeoutRule();

	@Rule
	public final TestName currentTest = new TestName();

	private boolean setupComplete = false;

	public void setUp() throws Exception {

		final Properties props = System.getProperties();

		final String override = System.getProperty("prop.override");
		if (override != null) {
			final FileInputStream fis = new FileInputStream(override);
			props.load(fis);
			fis.close();
		}

		this.driverUrl = props.getProperty("driver.url", "http://localhost:9515");
		this.browser = Browser.valueOf(props.getProperty("driver.browser", "CHROME"));
		this.properties = props;
		this.appUrl = props.getProperty("app.url");
		this.username = props.getProperty("app.user");
		this.password = props.getProperty("app.pass");
		this.reportType = props.getProperty("app.reportType");

		for (final Group g : Timeout.Group.values()) {
			final String property = "timeout." + g.getSuffix();
			final String value = props.getProperty(property);

			assertNotNull("Missing property: " + property + " -- required by " + g.getClass(), value);

			final Integer timeout = Integer.parseInt(value);
			this.timeoutGroups.put(g, timeout);
		}

		assertNotNull(this.driverUrl);
		assertNotNull(this.browser);
		assertNotNull(this.appUrl);
		assertNotNull(this.username);
		assertNotNull(this.password);
		assertNotNull(this.cwd);

		if (globalStart == null || globalStart.isAfter(ZonedDateTime.now())) {
			globalStart = ZonedDateTime.now();
		}

		this.driver = new RemoteWebDriver(new URL(driverUrl), browser.getCapabilities());
		driver.manage().window().maximize();
		this.je = (JavascriptExecutor) driver;
		this.wait = new WebDriverWait(driver, 60);
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			if (driver != null) {
				log.info("Closing driver on JVM shutdown");
				driver.quit();
			}
		}));

		setupComplete = true;

	}

	public void saveProperties() throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException, UnknownHostException {

		final Map<String, Object> props = new TreeMap<>();

		if (globalEnd == null || globalEnd.isBefore(ZonedDateTime.now())) {
			globalEnd = ZonedDateTime.now();
		}

		final Duration duration = Duration.between(globalStart, globalEnd);
		final String timeSpan = DurationFormatUtils.formatDurationWords(duration.toMillis(), true, true);

		props.put("cmd.test.time.start", DateTimeFormatter.RFC_1123_DATE_TIME.format(globalStart));
		props.put("cmd.test.time.end", DateTimeFormatter.RFC_1123_DATE_TIME.format(globalEnd));
		props.put("cmd.test.time.span", timeSpan);
		props.put("cmd.test.source.machine", InetAddress.getLocalHost());
		props.put("cmd.test.source.directory", this.sourcePath);
		props.put("cmd.test.driver.url", this.driverUrl);
		props.put("cmd.test.driver.browser", this.browser);
		props.put("cmd.test.app.url", this.appUrl);
		props.put("cmd.test.report.name", this.reportType);

		for (final Group g : Timeout.Group.values()) {
			props.put("cmd.test.timeout." + g.getSuffix(), timeoutGroups.get(g));
		}

		for (final Class<? extends TestHarness> testClass : splits.rowKeySet()) {
			if (this.getClass().equals(testClass)) {
				final Map<String, List<Pair<String, Long>>> row = splits.row(testClass);

				for (final Entry<String, List<Pair<String, Long>>> e : row.entrySet()) {
					final String testMethod = e.getKey();
					final List<Pair<String, Long>> splits = e.getValue();

					int i = 1;
					for (final Pair<String, Long> split : splits) {
						final String keyBase = "cmd.test.time.split." + testClass.getName() + "." + testMethod + "." + i + ".";
						props.put(keyBase + "label", split.getLeft());
						props.put(keyBase + "millis", split.getRight());
						i++;
					}
				}
			}
		}

		final Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();

		final Element testSuite = doc.createElement("testsuite");
		testSuite.setAttribute("name", TestHarness.class.getName());
		testSuite.setAttribute("time", "0");
		testSuite.setAttribute("tests", "0");
		testSuite.setAttribute("errors", "0");
		testSuite.setAttribute("skipped", "0");
		testSuite.setAttribute("failures", "0");
		doc.appendChild(testSuite);

		final Element properties = doc.createElement("properties");
		testSuite.appendChild(properties);

		for (final Entry<String, Object> e : props.entrySet()) {
			final Element prop = doc.createElement("property");
			prop.setAttribute("name", e.getKey());
			prop.setAttribute("value", e.getValue().toString());
			properties.appendChild(prop);
		}

		final File directory = new File(sourcePath);
		directory.mkdirs();

		final String className = this.getClass().getName();
		final String fullPath = sourcePath + "TEST-" + className + ".properties.xml";
		final File output = new File(fullPath);

		final Transformer tf = TransformerFactory.newInstance().newTransformer();
		tf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		tf.setOutputProperty(OutputKeys.INDENT, "yes");
		tf.transform(new DOMSource(doc), new StreamResult(output));
	}

	public void setTimeoutGroup(final Group timeoutGroup) {
		setTimeoutSeconds(this.timeoutGroups.get(timeoutGroup));
	}

	public void setCategory(final String categoryGroup) {
		this.categoryGroup = categoryGroup;
	}

	public void setTimeoutSeconds(final int timeoutSeconds) {
		log.debug("Setting timeout to {} seconds", timeoutSeconds);
		this.driver.manage().timeouts().implicitlyWait(timeoutSeconds, TimeUnit.SECONDS);
	}

	public void tearDown() throws Exception {

		if (driver != null) {
			driver.quit();
		}

		// saveProperties();
	}

	public void saveScreenshot(final String name) throws FileNotFoundException, IOException {
		driver.manage().window().maximize();
		final File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		final String fullPath = sourcePath + name + ".png";
		log.info("Saving screenshot to {}", fullPath);
		Files.copy(scrFile.toPath(), new FileOutputStream(fullPath));
	}

	public String getTestId() {
		return this.getClass().getName() + "." + this.currentTest.getMethodName();
	}

	public Integer getGroupSeconds(final Group group) {
		return this.timeoutGroups.get(group);
	}

	public boolean setupSuccessful() {
		return this.setupComplete;
	}

	@Deprecated
	public void timeStart() {

		if (stopWatch == null) {
			this.stopWatch = new StopWatch();
		}

		if (stopWatch.isStarted()) {
			// ignore if already started
			// and should be in all cases -- harness should start time timer
			return;
		}
		this.stopWatch.start();
	}

	public void timeSplit(final String label) {
		stopWatch.split();

		final String testMethod = this.currentTest.getMethodName();
		final ImmutablePair<String, Long> split = new ImmutablePair<String, Long>(label, stopWatch.getSplitTime());
		List<Pair<String, Long>> splitList = splits.get(getClass(), testMethod);

		if (splitList == null) {
			splitList = new ArrayList<>();
			splits.put(getClass(), testMethod, splitList);
		}

		splitList.add(split);
	}

	public void timeStop(final String label) {

		// this prevents the default stopper from causing a new entry
		if (stopWatch.isStarted()) {
			stopWatch.stop();

			final String testMethod = this.currentTest.getMethodName();
			final ImmutablePair<String, Long> split = new ImmutablePair<String, Long>(label, stopWatch.getTime());
			List<Pair<String, Long>> splitList = splits.get(getClass(), testMethod);

			if (splitList == null) {
				splitList = new ArrayList<>();
				splits.put(getClass(), testMethod, splitList);
			}

			splitList.add(split);
		}

	}

	public long getPrimaryMilliseconds(final String testMethod) {
		final List<Pair<String, Long>> splitList = splits.get(getClass(), testMethod);

		if (splitList == null) {
			log.warn("No splits present for class {}", this.getClass());
			return 0;
		}

		long last = 0;
		for (final Pair<String, Long> split : splitList) {
			final long elapsed = split.getRight() - last;

			if (split.getLeft().startsWith("")) {
				return elapsed;
			}

			last = split.getRight();
		}

		log.warn("No primary split found for test {} {}", this.getClass(), testMethod);
		return 0;
	}

	public String getMethodName() {
		return this.currentTest.getMethodName();
	}

}
