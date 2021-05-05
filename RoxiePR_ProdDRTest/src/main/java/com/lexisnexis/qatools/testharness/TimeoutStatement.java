package com.lexisnexis.qatools.testharness;

import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lexisnexis.qatools.testharness.Timeout.Group;

public class TimeoutStatement extends Statement {

	private static final Logger log = LoggerFactory.getLogger(TimeoutStatement.class);

	private final TestHarness test;
	private final Statement base;
	private final Group timeoutGroup;
	private final String category;

	public TimeoutStatement(final Statement base, final Group timeoutGroup, final TestHarness test, String category) {
		this.base = base;
		this.timeoutGroup = timeoutGroup;
		this.test = test;
		this.category = category;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void evaluate() throws Throwable {

		try {
			test.timeStart();
			test.setUp();
			test.setTimeoutGroup(timeoutGroup);
			test.setCategory(category);

			// default-start watch, for tests which do not start their own
			test.timeSplit("Test setup complete");

			// run the actual test
			base.evaluate();

			// default-stop, for tests which to not stop their own
			test.timeStop("*Total time");
		} catch (final Throwable e) {
			final String id = test.getTestId();
			log.error("Test " + id + " failed", e);
			if (test.setupSuccessful()) {
				test.saveScreenshot("SCREEN-" + id);
			}

			throw e;
		} finally {
			test.tearDown();

			// if startTime is null, that means setup failed, and the test didn't run anyway
			// if endTime is null, then the test didn't complete normally
			final long primaryMillis = test.getPrimaryMilliseconds(test.getMethodName());

			if (primaryMillis > 0) {
				final long seconds = primaryMillis / 1000;
				final int groupSeconds = test.getGroupSeconds(timeoutGroup);

				if (seconds > groupSeconds) {

					final String message = String.format("Test took %d seconds, exceeding it's annotated group time %s of %d ", seconds, this.timeoutGroup, groupSeconds);
					log.warn(test.getTestId() + ": " + message);
					throw new TimeoutAssertionError(message);
				}
			}
		}

	}

}
