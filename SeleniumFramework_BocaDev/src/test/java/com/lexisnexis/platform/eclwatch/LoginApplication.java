package com.lexisnexis.platform.eclwatch;

//import autoitx4java.AutoItX; 
//import autoit-0.1.13;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import com.lexisnexis.platform.eclwatch.testharness.Timeout;
import com.lexisnexis.platform.eclwatch.testharness.Timeout.Group;

@RunWith(BlockJUnit4ClassRunner.class)
public class LoginApplication extends TestBaseBocaDev {

	@Test
	@Timeout(group = Group.FAST)
	public void testLoginTest() throws Exception {
		doLogin();
		Thread.sleep(5000);
	}
}
