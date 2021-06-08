package com.lexisnexis.ins.portal.testharness.reports;

import java.util.ArrayList;

import org.apache.commons.lang3.tuple.Pair;

import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

public class TestResult {

	public String id;

	public String className;
	public String testMethod;
	public Group timeoutGroup;

	public ArrayList<Pair<String, Long>> splitTimes = new ArrayList<>();
	public int primaryTimeIndex;
	public float totalSeconds;
	public float primarySeconds;

	public boolean success;
	public boolean skipped;
	public boolean failed;
	public boolean error;
	public String message;
	public String detail;
	public byte[] screenShot;

}
