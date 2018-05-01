package com.higherli.library.spring.base;

import java.io.File;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.higherli.library.log.LoggerUtil;

public class SpringInit {
	private static ApplicationContext appContext;

	public static void init() {
		appContext = new ClassPathXmlApplicationContext(
				"file:" + SpringConfig.springConfigPath0 + File.separator + "applicationContext.xml");
		for (String name : appContext.getBeanDefinitionNames()) {
			LoggerUtil.debug(name);
		}

	}

	public static <T> T getBean(Class<T> type) {
		return appContext.getBean(type);
	}

	public static Object getBean(String name) {
		return appContext.getBean(name);
	}

	public static <T> T getBean(String name, Class<T> type) {
		return appContext.getBean(name, type);
	}
}
