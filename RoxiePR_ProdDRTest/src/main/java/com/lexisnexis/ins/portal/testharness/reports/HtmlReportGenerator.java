package com.lexisnexis.ins.portal.testharness.reports;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlReportGenerator {

	private static final Logger log = LoggerFactory.getLogger(HtmlReportGenerator.class);

	private final static String timeFormat = "%10.2f\n";
	private final static NumberFormat percentFormatter = new DecimalFormat("#0.0%");

	public static class Report {
		public String summary;
		public String full;
	}

	private static class Stats {
		public int totalTests;
		public int success;
		public int skipped;
		public int failure;
		public int error;

		public float totalTime;
		public float avgTime;
		public float stdDevTime;

		public float primaryTotalTime;
		public float primaryAvgTime;
		public float primaryStdDevTime;

		public float successPct;
		public float skipPct;
		public float failurePct;
		public float errorPct;

		public static Stats compute(final Map<String, TestResult> resultMap) {
			final Stats s = new Stats();
			final StandardDeviation stdDev = new StandardDeviation();
			final StandardDeviation primaryStdDev = new StandardDeviation();

			for (final TestResult r : resultMap.values()) {
				s.totalTests++;
				s.totalTime += r.totalSeconds;
				s.primaryTotalTime += r.primarySeconds;

				if (r.skipped) {
					s.skipped++;
				} else {
					if (r.totalSeconds > 0) {
						stdDev.increment(r.totalSeconds);
					}

					if (r.primarySeconds > 0) {
						primaryStdDev.increment(r.primarySeconds);
					}
				}

				if (r.success) {
					s.success++;
				}

				if (r.error) {
					s.error++;
				}

				if (r.failed) {
					s.failure++;
				}
			}

			s.successPct = (float) s.success / s.totalTests;
			s.failurePct = (float) s.failure / s.totalTests;
			s.errorPct = (float) s.error / s.totalTests;
			s.skipPct = (float) s.skipped / s.totalTests;

			s.avgTime = s.totalTime / (resultMap.size() - s.skipped);
			s.stdDevTime = (float) stdDev.getResult();

			s.primaryAvgTime = s.primaryTotalTime / (resultMap.size() - s.skipped);
			s.primaryStdDevTime = (float) primaryStdDev.getResult();

			return s;
		}
	}

	private static final String HEX_SUCCESS = "#9CCC65";
	private static final String HEX_FAILURE = "#E57373";
	private static final String HEX_ERROR = "#FFD54F";
	private static final String HEX_SKIPPED = "#BDBDBD";

	public static Report generate(final Map<String, TestResult> resultMap, final Map<String, String> propertyMap, final Optional<Integer> maxFileSize) {

		final Report r = new Report();
		final Stats stats = Stats.compute(resultMap);

		final StringBuilder sb = new StringBuilder();

		sb.append("<!DOCTYPE html><html style='background-color: black;'><head><meta charset='ISO-8859-1'><title>THOR ECLWATCH Test Results</title></head><style>");
		sb.append("body {font: 12pt Calibri; padding: 20px; background-color: #fdfdfd; margin: 0 40px 0 40px;}");
		sb.append("h2 {border-bottom: 1px solid #ddd; clear: both;}");
		sb.append("table tbody td {font-size: 10pt; padding: 4px;}");
		sb.append("table tbody th {font-size: 10pt; text-align: right}");
		sb.append("table thead th {border-bottom: 2px solid #444; font-size: 12pt; font-weight: bold; min-width: 4em;}");
		sb.append("table.individual tr:nth-child(even) {background-color: #eeeeed;}");
		sb.append("table.data tbody td {border-bottom: 1px solid #ddd; padding: 4px; text-align: right;}");
		sb.append("table.overview tbody td {border-bottom: 1px solid #ddd; padding: 4px; text-align: left;}");
		sb.append("div.miniTable {display: table; font-size: 8pt; color: #777}");
		sb.append("div.row {display: table-row;} div.row > div {display: table-cell;}");
		sb.append("div.primary {font-size: 10pt; color: black; font-weight: bold;}");
		sb.append(".successBack {background-color: " + HEX_SUCCESS + "; font-size: 14pt; font-weight: bold;}");
		sb.append(".failureBack {background-color: " + HEX_FAILURE + "; font-size: 14pt; font-weight: bold;}");
		sb.append(".errorBack {background-color: " + HEX_ERROR + "; font-size: 14pt; font-weight: bold;}");
		sb.append(".skippedBack {background-color: " + HEX_SKIPPED + "; font-size: 14pt; font-weight: bold;}");
		sb.append(".big {font-size:24pt; font-weight: bold;}");
		sb.append("");
		sb.append("");
		sb.append("</style><body><h1 style='width: 100%; text-align: center;'>THOR ECLWATCH Test Results</h1>");

		if (stats.success == stats.totalTests) {
			sb.append("<h3 class='big' style='color: #558B2F; width: 100%; text-align: center;'>&#x2714; SUCCESS</h3>");
		} else {
			sb.append("<h3 class='big' style='color: #c62828; width: 100%; text-align: center;'>&#x2718; NOT SUCCESSFUL</h3>");
		}

		appendSummary(sb, stats, propertyMap);
		appendStatistics(sb, stats);

		final StringBuilder sbSummary = new StringBuilder(sb);
		appendAdditional(sbSummary);
		sbSummary.append("</body></html>");
		r.summary = sbSummary.toString();

		appendResults(sb, resultMap, stats, propertyMap, maxFileSize);
		appendProperties(sb, propertyMap);

		sb.append("</body></html>");
		r.full = sb.toString();
		return r;
	}

	private static void appendAdditional(final StringBuilder sb) {
		sb.append("<h2>Additional Information</h2>");
		sb.append("For a complete report, please see the attached report-full.html");

	}

	private static void appendProperties(final StringBuilder sb, final Map<String, String> propertyMap) {
		sb.append("<h2>Environment Properties</h2><details><summary>Hidden by default. Click to expand</summary>");
		sb.append("<table class='overview'><thead><tr><th>Name</th><th>Value</th></tr></thead><tbody>");

		for (final Entry<String, String> e : propertyMap.entrySet()) {
			sb.append("<tr><th>");
			sb.append(e.getKey());
			sb.append("</th><td>");
			sb.append(e.getValue());
			sb.append("</td></tr>");
		}

		sb.append("</table></details>");

	}

	private static void appendLongTextCell(final StringBuilder sb, final String content) {
		sb.append("<td>");

		if (content != null) {

			String escaped = content.replace("<", "&lt");
			escaped = escaped.replace(">", "&gt");

			if (escaped.length() > 80) {
				sb.append("<details><summary>Expand</summary>");
			}

			sb.append("<span style='white-space: pre-line; font: 8pt Consolas; color: #888'>");
			sb.append(escaped);
			sb.append("</span>");

			if (escaped.length() > 80) {
				sb.append("</details>");
			}
		}

		sb.append("</td>");
	}

	private static void appendResults(final StringBuilder sb, final Map<String, TestResult> resultMap, final Stats stats, final Map<String, String> propertyMap, final Optional<Integer> maxFileSize) {
		sb.append("<h2>Individual Results</h2>");

		final String[] strs = resultMap.values().stream().map(r -> r.className).toArray(String[]::new);

		final String classPrefix = StringUtils.getCommonPrefix(strs);

		sb.append("<table class='individual' style='width: 100%;'><thead><tr>");
		sb.append("<th style='text-align: left;'>Test Class</th>");
		sb.append("<th style='text-align: left;'>Test Method</th>");
		sb.append("<th style='text-align: center;'>Status</th>");
		sb.append("<th style='text-align: right;'>Time (s)</th>");
		sb.append("<th style='text-align: left;'>Message</th>");
		sb.append("<th style='text-align: left;'>Details</th>");
		sb.append("<th style='text-align: left;'>Screenshot</th>");
		sb.append("</tr></thead><tbody>");

		final Collection<TestResult> values = resultMap.values();
		final SortedSet<TestResult> sorted = new TreeSet<>((o1, o2) -> o1.id.compareTo(o2.id));
		sorted.addAll(values);

		for (final TestResult tr : sorted) {
			sb.append("<tr id='");
			sb.append(tr.id);
			sb.append("'><td title='");
			sb.append(tr.className);
			sb.append("'>...");
			sb.append(tr.className.substring(classPrefix.length()));

			sb.append("</td><td>");
			sb.append(tr.testMethod);

			if (tr.skipped) {
				sb.append("<td class='skippedBack' title='Skipped' style='text-align: center;'>&#x2014;</td>");
			}

			if (tr.success) {
				sb.append("<td class='successBack' title='Success' style='text-align: center;'>&#x2714;</td>");
			}

			if (tr.failed) {
				sb.append("<td class='failureBack' title='Failure' style='text-align: center;'>&#x2718;</td>");
			}

			if (tr.error) {
				sb.append("<td class='errorBack' title='Error' style='text-align: center;'>&#x2757;</td>");
			}

			sb.append("</td><td style='clear: both;'>");

			if (!tr.skipped && tr.primarySeconds > 0) {
				if (tr.primarySeconds < stats.primaryAvgTime - (stats.primaryStdDevTime * 2)) {
					sb.append("<span style='float: left; color: #3949AB;' title='2 Standard deviations below average'>&#x2193;&#x2193;</span>");
				} else {
					if (tr.primarySeconds < stats.primaryAvgTime - (stats.primaryStdDevTime)) {
						sb.append("<span style='float: left; color: #5C6BC0' title='1 Standard deviation below average'>&#x2193;</span>");
					} else {
						if (tr.primarySeconds > stats.primaryAvgTime + (stats.primaryStdDevTime * 2)) {
							sb.append("<span style='float: left; color: #EC407A;' title='2 Standard deviations above average'>&#x2191;&#x2191;</span>");
						} else {
							if (tr.primarySeconds > stats.primaryAvgTime + (stats.primaryStdDevTime)) {
								sb.append("<span style='float: left; color: #F48FB1' title='1 Standard deviation above average'>&#x2191;</span>");
							}
						}
					}
				}
			}

			appendTimeDetails(sb, tr);
			sb.append("</td>");

			appendLongTextCell(sb, tr.message);
			appendLongTextCell(sb, tr.detail);

			sb.append("<td>");
			if (tr.screenShot != null) {

				final String encodedScreenshot = Base64.getEncoder().encodeToString(tr.screenShot);

				if (maxFileSize.isPresent() && sb.length() + encodedScreenshot.length() > (maxFileSize.get() * 1024)) {
					log.info("Max file size of {}KB would be exceeed by attaching another screen shot; image for test {} will not be included", maxFileSize.get(), tr.id);
					sb.append("<details><summary title='Screen shot generated, but not attached'>&#x20E0;</summary>Fize size limit reached -- No further screehshot attachments which cause this file to exceed ");
					sb.append(maxFileSize.get());
					sb.append("KB will be included.  Please fix preceeding errors and failures to see additional screen shots, or locate the generated file ");
					sb.append(propertyMap.get("cmd.test.source.directory"));
					sb.append("SCREEN-");
					sb.append(tr.id);
					sb.append(".png on ");
					sb.append(propertyMap.get("cmd.test.source.machine"));

					sb.append("</details>");
				} else {
					sb.append("<details><summary title='Screenshot attached'>&#x1f4f7;</summary><img alt='screenshot' src='data:image/png;base64,");
					sb.append(encodedScreenshot);
					sb.append("'></details>");
				}
			}

			sb.append("</td></tr>");

		}

		sb.append("</tbody></table>");

	}

	private static void appendTimeDetails(final StringBuilder sb, final TestResult tr) {
		sb.append("<span style='float: right;'><details><summary>");
		sb.append(String.format(timeFormat, tr.primarySeconds));
		sb.append("</summary><div class='miniTable'>");

		int i = 1;
		for (final Pair<String, Long> split : tr.splitTimes) {
			if (i == tr.primaryTimeIndex) {
				sb.append("<div class='primary row'><div>");
			} else {
				sb.append("<div class='row'><div>");
			}

			sb.append(split.getLeft());
			sb.append("</div><div>");
			final Long millis = split.getRight();
			final float seconds = (float) millis / 1000;
			sb.append(String.format(timeFormat, seconds));
			sb.append("</div></div>");
			i++;
		}

		sb.append("<div class='primary row' style='text-decoration: underline'><div>Test Total</div><div>");
		sb.append(String.format(timeFormat, tr.totalSeconds));
		sb.append("</div></div>");

		sb.append("</div></details></span>");
	}

	private static void appendRoundedTime(final StringBuilder sb, final float time) {
		sb.append("<span style='float: right;'>");
		sb.append(String.format(timeFormat, time));
		sb.append("</span>");
	}

	private static void appendStatistics(final StringBuilder sb, final Stats stats) {
		sb.append("<div style='clear: both;'>");
		sb.append("<h2>Statistics</h2><table class='data' style='float: left;'><thead><tr><td></td>");
		sb.append("<th>Total</th><th>Successful</th><th>Failed</th><th>Error</th><th>Skipped</th></tr></thead><tbody><tr><th>Number of Tests</th><td>");
		sb.append(stats.totalTests);
		sb.append("</td><td class='successBack'><span style='float: left;'>&#x2714;</span> ");
		sb.append(stats.success);
		sb.append("</td><td class='failureBack'><span style='float: left;'>&#x2718;</span> ");
		sb.append(stats.failure);
		sb.append("</td><td class='errorBack'><span style='float: left;'>&#x2757;</span> ");
		sb.append(stats.error);
		sb.append("</td><td class='skippedBack'><span style='float: left;'>&#x2014;</span> ");
		sb.append(stats.skipped);
		sb.append("</td></tr><tr><th>Percent of Tests</th><td>100%</td><td>");
		sb.append(percentFormatter.format(stats.successPct));
		sb.append("</td><td>");
		sb.append(percentFormatter.format(stats.failurePct));
		sb.append("</td><td>");
		sb.append(percentFormatter.format(stats.errorPct));
		sb.append("</td><td>");
		sb.append(percentFormatter.format(stats.skipPct));
		sb.append("</td></tr></tbody></table>");

		sb.append("<table class='data' style='float: left; margin: 0 20px 0 20px;'><thead><tr><td></td><th>Total Time</th><th>Average Time</th><th>Std. Dev.</th></tr></thead>");
		sb.append("<tbody><tr><th>Primary</th><td>");
		sb.append(String.format(timeFormat, stats.primaryTotalTime));
		sb.append("</td><td>");
		sb.append(String.format(timeFormat, stats.primaryAvgTime));
		sb.append("</td><td> &#xb1; ");
		sb.append(String.format(timeFormat, stats.primaryStdDevTime));
		sb.append("</td></tr><tr><th>Overall</th><td>");
		sb.append(String.format(timeFormat, stats.totalTime));
		sb.append("</td><td>");
		sb.append(String.format(timeFormat, stats.avgTime));
		sb.append("</td><td> &#xb1; ");
		sb.append(String.format(timeFormat, stats.stdDevTime));
		sb.append("</td></tr></tbody></table>");

		sb.append("<div style='float: left'>");
		sb.append(renderPie(stats));
		sb.append("</div>");

		sb.append("</div>");

	}

	private static String renderPie(final Stats stats) {
		final String lableSuccess = "Success: " + stats.success;
		final String lableFailure = "Failure: " + stats.failure;
		final String lableError = "Error: " + stats.error;
		final String lableSkipped = "Skipped: " + stats.skipped;

		final Map<String, Number> slices = new LinkedHashMap<>();
		slices.put(lableSuccess, stats.success);
		slices.put(lableFailure, stats.failure);
		slices.put(lableError, stats.error);
		slices.put(lableSkipped, stats.skipped);

		final Map<String, String> colors = new HashMap<>();
		colors.put(lableSuccess, HEX_SUCCESS);
		colors.put(lableFailure, HEX_FAILURE);
		colors.put(lableError, HEX_ERROR);
		colors.put(lableSkipped, HEX_SKIPPED);

		final SVGPie pie = new SVGPie(slices);

		final String pieChart = pie.render(80, -90, colors, "white", true);
		return pieChart;
	}

	private static void appendSummary(final StringBuilder sb, final Stats stats, final Map<String, String> propertyMap) {

		sb.append("<h2>Summary</h2><table class='overview'><tbody>");
		sb.append("<tr><th>Test Source &#x27f6; Target</th><td>");
		sb.append(propertyMap.get("ecl.test.source.machine"));
		sb.append("</td><td>&#x27f6;</td><td><a href='");
		sb.append(propertyMap.get("ecl.test.app.url"));
		sb.append("'>");
		sb.append("</a> (");
		sb.append(propertyMap.get("ecl.test.driver.browser"));
		sb.append(")</td></tr><tr><th>Start &#x27f6; End time</th><td>");
		sb.append(propertyMap.get("ecl.test.time.start"));
		sb.append("</td><td>&#x27f6;</td><td>");
		sb.append(propertyMap.get("ecl.test.time.end"));
		sb.append(" (");
		sb.append(propertyMap.get("ecl.test.time.span"));
		sb.append(")</td></tr><tr><th>User</th><td>");
		sb.append(propertyMap.get("ecl.test.app.user"));
		sb.append("</td><td></td><td>");
		sb.append(propertyMap.get("ecl.test.dataset.type"));
		sb.append("/");
		sb.append(propertyMap.get("ecl.test.dataset.name"));
		sb.append(")</td></tr><tr><th>Report-Type</th><td>");
		sb.append("</td><td></td><td>");
		sb.append(propertyMap.get("ecl.test.report.name"));
		sb.append("</td></tr></tbody></table>");
	}

}
