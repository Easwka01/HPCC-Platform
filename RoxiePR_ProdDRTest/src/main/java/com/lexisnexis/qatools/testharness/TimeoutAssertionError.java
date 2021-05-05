package com.lexisnexis.qatools.testharness;

@SuppressWarnings("serial")
public class TimeoutAssertionError extends AssertionError {

	public TimeoutAssertionError(final String message) {
		super(message);
	}

}
