package com.lexisnexis.ins.portal.testharness.reports;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class SVGPie {

	private final Map<String, Double> angles = new LinkedHashMap<>();

	public SVGPie(final Map<String, ? extends Number> slices) {

		int total = 0;
		for (final String label : slices.keySet()) {
			final Double portion = slices.get(label).doubleValue();
			total += portion;
		}

		for (final String label : slices.keySet()) {
			final Double portion = slices.get(label).doubleValue();
			final double percent = portion / total;
			final double degrees = Math.ceil(360 * percent);
			angles.put(label, degrees);
		}
	}

	public static String drawArcPath(final int edgeRadius, final double pieRadius, final double startAngle, final double endAngle) {

		final int x1 = (int) (edgeRadius + (pieRadius * Math.cos(Math.PI * startAngle / 180)));
		final int y1 = (int) (edgeRadius + (pieRadius * Math.sin(Math.PI * startAngle / 180)));
		final int x2 = (int) (edgeRadius + (pieRadius * Math.cos(Math.PI * endAngle / 180)));
		final int y2 = (int) (edgeRadius + (pieRadius * Math.sin(Math.PI * endAngle / 180)));

		final int largeArcFlag = endAngle - startAngle >= 180 ? 1 : 0;

		return "M" + pieRadius + "," + pieRadius + " L" + x1 + "," + y1 + "  A" + pieRadius + "," + pieRadius + " 0 " + largeArcFlag + " 1 " + x2 + "," + y2 + " z";
	}

	public Map<String, String> getSlicePaths(final int size, final int startAngle) {

		double currentAngle = startAngle;
		final double radius = 0.95 * size;

		final Map<String, String> paths = new LinkedHashMap<>();

		for (final Entry<String, Double> e : angles.entrySet()) {
			final String label = e.getKey();
			final Double angle = e.getValue();
			final String path = drawArcPath(size, radius, currentAngle, currentAngle + angle);
			paths.put(label, path);
			currentAngle += angle;
		}

		return paths;
	}

	public Map<String, String> getPathElements(final int radius, final int startAngle, final Map<String, String> colors, final String stroke, final boolean useLabels) {

		final Map<String, String> elements = new LinkedHashMap<>();

		final Map<String, String> slicePaths = getSlicePaths(radius, startAngle);
		for (final Entry<String, String> e : slicePaths.entrySet()) {
			final String label = e.getKey();
			final String color = colors.get(label);
			final StringBuilder path = new StringBuilder("<path d='");
			path.append(e.getValue());
			path.append("' fill='");
			path.append(color);
			path.append("'");
			if (stroke != null) {
				path.append(" stroke='");
				path.append(stroke);
				path.append("' style='stroke-antialiasing: true;'");
			}
			path.append(">");

			if (useLabels) {
				path.append("<title>");
				path.append(label);
				path.append("</title>");
			}

			path.append("</path>");

			elements.put(label, path.toString());
		}

		return elements;
	}

	public String render(final int radius, final int startAngle, final Map<String, String> colors, final String stroke, final boolean useLabels) {

		final StringBuilder svg = new StringBuilder("<svg width='");
		svg.append(radius * 2);
		svg.append("' height='");
		svg.append(radius * 2);
		svg.append("'>\n");

		final Map<String, String> elements = getPathElements(radius, startAngle, colors, stroke, useLabels);
		for (final String pathElement : elements.values()) {
			svg.append(pathElement);
			svg.append("\n");
		}

		svg.append("</svg>");

		return svg.toString();

	}

	public static void main(final String[] args) {

		final Map<String, Integer> slices = new LinkedHashMap<>();
		slices.put("a", 1);
		slices.put("b", 2);
		slices.put("c", 4);
		slices.put("d", 8);
		slices.put("e", 16);

		final SVGPie pie = new SVGPie(slices);

		final Map<String, String> colors = new HashMap<>();
		colors.put("a", "red");
		colors.put("b", "green");
		colors.put("c", "blue");
		colors.put("d", "yellow");
		colors.put("e", "orange");

		System.out.println(pie.render(200, -90, colors, "white", true));

	}
}
