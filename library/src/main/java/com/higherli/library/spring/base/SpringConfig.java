package com.higherli.library.spring.base;

import java.io.File;

public class SpringConfig {

	/**
	 * System.getProperty("user.dir")表示项目的绝对路径 <br/>
	 */
	public static String springConfigPath0 = new StringBuilder(System.getProperty("user.dir")).append(File.separator)
			.append("src").append(File.separator).append("main").append(File.separator).append("resources")
			.append(File.separator).append("spring").toString();
}
