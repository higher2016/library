package com.higherli.library;

import java.io.File;

import com.higherli.library.log.LogInit;
import com.higherli.library.netty.Server;
import com.higherli.library.spring.base.SpringConfig;
import com.higherli.library.spring.base.SpringInit;

public class Main {
	public static void main(String[] args) {
		LogInit.init();
		String xmlPath = "file:" + SpringConfig.springConfigPath0 + File.separator + "applicationContext.xml";
		if (args != null && args.length != 0) {
			xmlPath = args[0];
		}
		SpringInit.init(xmlPath);
		new Server().start();
		System.out.println("start netty server bind port is 12333---------------");
	}
}
