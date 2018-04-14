package com.higherli.library.log;

import org.apache.log4j.Logger;

public class LoggerUtil {
	private static Logger LOG = null;

	public static void error(Object msg) {
		LOG.warn(msg, null);
	}

	public static void error(Object msg, Throwable t) {
		LOG.warn(msg, t);
	}

	public static void info(Object msg) {
		LOG.info(msg);
	}

	public static void errorf(String format, Object... objs) {
		LOG.warn(String.format(format, objs));
	}
}
