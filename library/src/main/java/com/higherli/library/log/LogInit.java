package com.higherli.library.log;

import java.io.File;

import org.apache.log4j.xml.DOMConfigurator;

public class LogInit {
	public static void init() {
		DOMConfigurator.configure(LogConfig.logConfigPath + File.separator + "log.xml");
	}
}
