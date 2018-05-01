package com.higherli.library.log;

import org.apache.log4j.Logger;

public class LoggerUtil {
	private static Logger LOG = Logger.getLogger("serverLogger");

	public static void error(Object msg) {
		LOG.warn(msg, null);
	}

	public static void error(Object msg, Throwable t) {
		LOG.warn(msg, t);
	}

	public static void info(Object msg) {
		LOG.info(msg);
	}

	public static void debug(Object msg) {
		LOG.debug(msg);
	}

	public static void infof(String format, Object... objs) {
		LOG.info(String.format(format, objs));
	}

	public static void debugf(String format, Object... objs) {
		LOG.debug(String.format(format, objs));
	}

	public static void errorf(String format, Object... objs) {
		LOG.warn(String.format(format, objs));
	}
}
