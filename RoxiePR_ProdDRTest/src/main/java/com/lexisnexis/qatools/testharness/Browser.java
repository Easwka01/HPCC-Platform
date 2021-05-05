package com.lexisnexis.qatools.testharness;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

public enum Browser {

	/**
	 * Google Chrome
	 */
	CHROME(DesiredCapabilities.chrome()),
	/**
	 * Mozilla Firefox
	 */
	FIREFOX(DesiredCapabilities.firefox()),
	/**
	 * Microsoft Internet Explorer
	 */
	IE(DesiredCapabilities.internetExplorer());

	private final Capabilities capabilities;

	private Browser(final Capabilities capabilities) {
		this.capabilities = capabilities;
	}

	public Capabilities getCapabilities() {
		return capabilities;
	}

}
