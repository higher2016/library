package com.higherli.library.netty.handler;

import com.higherli.library.log.LoggerUtil;

public class ThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
	private static Thread.UncaughtExceptionHandler instance = new ThreadUncaughtExceptionHandler();

	public void uncaughtException(Thread t, Throwable e) {
		String msg = String.format("an uncaught exception of %s/%s", t.getId(), t.getName());
		LoggerUtil.error(msg, e);
	}

	public static Thread.UncaughtExceptionHandler getHandler() {
		return instance;
	}
}
