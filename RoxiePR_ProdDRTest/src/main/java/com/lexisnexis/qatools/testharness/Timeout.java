package com.lexisnexis.qatools.testharness;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Timeout {

	Group group() default Group.AVG;

	public static enum Group {
		FAST("fast"),
		AVG("avg"),
		SLOW("slow"),
		MAX("max");

		private final String suffix;

		private Group(final String suffix) {
			this.suffix = suffix;
		}

		public String getSuffix() {
			return suffix;
		}
	}

}
