package com.lexisnexis.platform.eclwatch.testharness;

@SuppressWarnings("serial")
public class TimeoutAssertionError extends AssertionError {

	public TimeoutAssertionError(final String message) {
		super(message);
	}

}
