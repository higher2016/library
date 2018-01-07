package com.higherli.library.log;

import java.io.File;

public class LogConfig {
	public static String logConfigPath = new StringBuilder(System.getProperty("user.dir")).append(File.separator)
			.append("src").append(File.separator).append("main").append(File.separator).append("resources")
			.append(File.separator).append("log").toString();
}
