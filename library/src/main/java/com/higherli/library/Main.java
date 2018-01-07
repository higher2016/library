package com.higherli.library;

import com.higherli.library.log.LogInit;
import com.higherli.library.netty.Server;
import com.higherli.library.spring.base.SpringInit;

public class Main {
	public static void main(String[] args) {
		LogInit.init();
		SpringInit.init();
		new Server().start();
		System.out.println("start netty server bind port is 12333---------------");
	}
}
