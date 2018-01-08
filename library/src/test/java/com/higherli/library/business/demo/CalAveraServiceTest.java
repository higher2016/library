package com.higherli.library.business.demo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.higherli.library.base.BaseSpringAndJunit4Test;

public class CalAveraServiceTest extends BaseSpringAndJunit4Test{

	@Autowired
	private CalAveraService calAveraService;

	@Test
	public void testCalAveraIntInt() {
		assertEquals(3, calAveraService.calAvera(4, 2));
	}

	@Test
	public void testCalAveraDoubleDouble() {
		// junit中没有assertEquals(double,double)的方法。因为double值是允许误差的。所以要实现double的断言要用assertEquals(double,double,double)这个方法。第三个参数是允许误差
		assertEquals(3.2, calAveraService.calAvera(3.3, 3.1), 0.01);
	}

}
