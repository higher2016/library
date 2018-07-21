package com.higherli.library.base;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import com.higherli.library.Main;
import com.higherli.library.spring.base.SpringConfig;


public class BaseSpringAndJunit4Test {
	static {
		String xmlPath = "file:" + SpringConfig.testSpringConfigPath0 + File.separator + "applicationContext.xml";
		String[] arg0 = new String[] { xmlPath };
		try {
			Main.main(arg0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean isLoclePortUsing(int port) {
		boolean flag = true;
		try {
			flag = isPortUsing("127.0.0.1", port);
		} catch (Exception e) {
		}
		return flag;
	}

	@SuppressWarnings({ "unused", "resource" })
	public static boolean isPortUsing(String host, int port) throws UnknownHostException {
		boolean flag = false;
		InetAddress theAddress = InetAddress.getByName(host);
		try {
			Socket socket = new Socket(theAddress, port);
			flag = true;
		} catch (IOException e) {

		}
		return flag;
	}
}
