package com.lexisnexis.ins.portal.testharness.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.Stream.Builder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lexisnexis.ins.portal.testharness.reports.HtmlReportGenerator.Report;
import com.lexisnexis.qatools.testharness.Timeout;

public class ReportGenerator implements Runnable {

	private static final Logger log = LoggerFactory.getLogger(ReportGenerator.class);

	private static final String CWD = Paths.get(".").toAbsolutePath().normalize().toString();

	public static class ParsedFile {
		public Stream<TestResult> results;
		public Map<String, String> properties;
	}

	public static void main(final String[] args) throws CmdLineException {

		final ReportGenerator rg = new ReportGenerator();

		final CmdLineParser parser = new CmdLineParser(rg);
		parser.parseArgument(args);

		rg.run();
	}

	@Option(name = "-src", metaVar = "PATH", usage = "Path where junit .xml results are located.  Defaults to working directory /target/surefure-reports/")
	private String sourcePath = CWD + "\\target\\surefire-reports\\";

	@Option(name = "-size", metaVar = "KB", usage = "Maximum size of output file, in KB")
	private Integer maxFileSize;

	public void setSourcePath(final String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public void setMaxFileSize(final Integer maxFileSize) {
		this.maxFileSize = maxFileSize * 1024;
	}

	@Override
	public void run() {

		log.info("Starting...");
		final Map<String, String> properties = new TreeMap<>();

		final Stream<Path> xmlFiles = findXmlFiles();
		final Stream<Document> documents = xmlFiles.map(this::loadDocument);
		Stream<ParsedFile> parsedFiles = documents.map(this::extractTestInfo);
		parsedFiles = parsedFiles.peek(pf -> {
			properties.putAll(pf.properties);
		});

		final Stream<TestResult> results = parsedFiles.flatMap(pf -> pf.results);
		final Map<String, TestResult> resultMap = results.collect(Collectors.toMap(ti -> ti.id, ti -> ti));

		resultMap.values().stream().forEach(r -> {
			matchTimesWithTests(r, properties);
		});

		final Report report = HtmlReportGenerator.generate(resultMap, properties, Optional.ofNullable(maxFileSize));
		writeFile(report.full, "report-full.html");
		writeFile(report.summary, "report-summary.html");

		log.info("Done");
	}

	private void writeFile(final String content, final String fileName) {
		try {

			final byte[] bytes = content.getBytes(StandardCharsets.UTF_8.name());
			final Path filePath = Paths.get(sourcePath, fileName);
			Files.write(filePath, bytes);
			log.info("Wrote {}", filePath);
		} catch (final Exception e) {
			log.error("Unable to write HTML report " + fileName, e);
		}
	}

	private ParsedFile extractTestInfo(final Document doc) {

		final Stream<Element> children = getChildren(doc.getDocumentElement(), "testcase");
		final Element propertiesElement = getFirstChild(doc.getDocumentElement(), "properties");
		final Stream<Element> propElements = getChildren(propertiesElement, "property");

		final ParsedFile file = new ParsedFile();
		file.properties = propElements.collect(Collectors.toMap(e -> e.getAttribute("name"), e -> e.getAttribute("value")));
		file.results = children.map(this::buildTestResult);
		return file;
	}

	private void matchTimesWithTests(final TestResult r, final Map<String, String> properties) {

		long lastSplit = 0;
		int i = 1;

		do {
			final String keyBase = "cmd.test.time.split." + r.id + "." + i;
			final String labelKey = keyBase + ".label";
			final String millisKey = keyBase + ".millis";

			final String label = properties.get(labelKey);

			if (label == null) {
				// no more, exit loop
				break;
			}

			final long millis = Long.parseLong(properties.get(millisKey));
			final long elapsed = millis - lastSplit;
			r.splitTimes.add(new ImmutablePair<String, Long>(label, elapsed));
			lastSplit = millis;

			if (label.startsWith("*")) {
				r.primaryTimeIndex = i;
				r.primarySeconds = (float) elapsed / 1000;
			}

			i++;

		} while (true);

		r.totalSeconds = (float) lastSplit / 1000;

	}

	private TestResult buildTestResult(final Element e) {
		final TestResult tr = new TestResult();

		tr.className = e.getAttribute("classname");
		tr.testMethod = e.getAttribute("name");

		try {
			final Class<?> testClass = Class.forName(tr.className);
			final Method testMethod = testClass.getMethod(tr.testMethod);
			final Timeout timeout = testMethod.getAnnotation(Timeout.class);

			if (timeout != null) {
				tr.timeoutGroup = timeout.group();
			}

		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException ex) {
			log.warn("Error determining timeout group", ex.getMessage());
		}

		tr.id = tr.className + "." + tr.testMethod;

		tr.success = true;
		tr.skipped = false;
		tr.failed = false;
		tr.error = false;

		final Element skipped = getFirstChild(e, "skipped");
		if (skipped != null) {
			tr.success = false;
			tr.skipped = true;
			tr.message = "Test skipped (@Ignore)";
		}

		final Element failure = getFirstChild(e, "failure");
		if (failure != null) {
			tr.success = false;
			tr.failed = true;
			tr.message = failure.getAttribute("message");
			tr.detail = failure.getTextContent();
		}

		final Element error = getFirstChild(e, "error");
		if (error != null) {
			tr.success = false;
			tr.error = true;
			tr.message = error.getAttribute("message");
			tr.detail = error.getTextContent();
		}

		tr.screenShot = checkForScreenshot(tr.id);

		log.info("Loaded testcase {}", tr.id);
		return tr;
	}

	private byte[] checkForScreenshot(final String id) {
		final String screenPath = this.sourcePath + "SCREEN-" + id + ".png";
		final Path screenFile = Paths.get(screenPath);

		if (screenFile.toFile().exists()) {
			try {
				log.info("Including screenshot file {}", screenPath);
				return Files.readAllBytes(screenFile);
			} catch (final IOException e) {
				log.warn("Can't read screenshot file " + screenPath, e);
			}
		}

		log.debug("No screenshots for test {}", id);
		return null;
	}

	private Stream<Element> getChildren(final Node n, final String... elementNames) {
		final NodeList children = n.getChildNodes();

		final Builder<Node> builder = Stream.builder();
		for (int i = 0; i < children.getLength(); i++) {
			builder.accept(children.item(i));
		}

		return builder.build().filter(child -> Stream.of(elementNames).anyMatch(s -> s.equals(child.getNodeName()))).map(child -> (Element) child);
	}

	private Element getFirstChild(final Node n, final String elementName) {
		final NodeList children = n.getChildNodes();

		for (int i = 0; i < children.getLength(); i++) {
			final Node item = children.item(i);
			if (item.getNodeName().equals(elementName)) {
				return (Element) item;
			}
		}

		return null;
	}

	private Stream<Path> findXmlFiles() {
		try {
			log.info("Finding *.xml from {}", sourcePath);
			final Path dir = Paths.get(sourcePath);
			return Files.list(dir).filter(p -> p.getFileName().toString().endsWith(".xml"));
		} catch (final IOException e) {
			log.error("Error reading source path " + this.sourcePath);
			return null;
		}
	}

	private Document loadDocument(final Path filePath) {
		try {
			final DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			final DocumentBuilder db = dbf.newDocumentBuilder();
			final Document document = db.parse(filePath.toFile());
			log.info("Loaded {}", filePath);
			return document;
		} catch (final Exception e) {
			log.error("Error loading file " + filePath, e);
			return null;
		}
	}

}
