package com.higherli.library.base;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.higherli.library.log.LogInit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/spring/app*.xml" })
public abstract class BaseSpringAndJunit4Test {

	@BeforeClass
	public static void beforeClassAll() {
		LogInit.init();
	}
}
