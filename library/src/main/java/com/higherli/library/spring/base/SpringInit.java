package com.higherli.library.spring.base;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.higherli.library.log.LoggerUtil;

public class SpringInit {
	private static ApplicationContext appContext;

	/**
	 * 初始化spring组件
	 * 
	 * @param xmlPath:spring主配置文件路径
	 */
	public static void init(String xmlPath) {
		appContext = new ClassPathXmlApplicationContext(xmlPath);
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
