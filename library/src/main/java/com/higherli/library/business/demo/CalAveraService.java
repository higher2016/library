package com.higherli.library.business.demo;

import org.springframework.stereotype.Component;

/**
 * Calculate the average
 */
@Component
public class CalAveraService {
	public int calAvera(int a, int b) {
		int av = (a + b) / 2;
		return av;
	}

	public double calAvera(double a, double b) {
		double av = (a + b) / 2;
		return av;
	}
}
