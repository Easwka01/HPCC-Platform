package com.lexisnexis.qatools.testharness;

import org.junit.experimental.categories.Category;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lexisnexis.qatools.testharness.Timeout.Group;

public class TimeoutRule implements MethodRule {

	private static final Logger log = LoggerFactory.getLogger(TimeoutRule.class);

	private Group group;

	private String category;

	@Override
	public Statement apply(final Statement base, final FrameworkMethod method, final Object target) {

		if (!TestHarness.class.isAssignableFrom(target.getClass())) {
			throw new RuntimeException("TimeoutRule can only be used in subclasses of TestHarness");
		}

		final TestHarness test = (TestHarness) target;

		final Timeout annotation = method.getAnnotation(Timeout.class);
		final Category cat = method.getAnnotation(Category.class);

		if (cat != null) {
			this.category = cat.toString();
		}

		if (annotation != null) {
			this.group = annotation.group();
			log.debug("Applying timeout group " + group + " to " + method.getName());
		} else {
			this.group = Timeout.Group.MAX;
			log.warn("Test {} is missing a @Timeout annotation;  Please assign an appropriate group.", test.getTestId());
		}

		return new TimeoutStatement(base, group, test, category);

	}

	public Group getGroup() {
		return group;
	}

}
