package com.higherli.library.log;

import org.junit.BeforeClass;
import org.junit.Test;

public class LoggerUtilTest {

	@BeforeClass
	public static void setUp() throws Exception {
		LogInit.init();
	}

	@Test
	public void testError() {
		LoggerUtil.error("1");
		LoggerUtil.error(1);
	}

	@Test
	public void testErrorThrowable() {
		LoggerUtil.error("1", new RuntimeException("Logger Test Exception"));
	}

	@Test
	public void info() {
		LoggerUtil.info(1);
	}

	@Test
	public void testErrorf() {
		LoggerUtil.errorf("%s,%s,%d", 1, "s", 1);
	}

	@Test
	public void testDebug() {
		LoggerUtil.debug("s");;
	}

}
