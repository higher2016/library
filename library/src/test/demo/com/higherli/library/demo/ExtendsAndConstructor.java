package com.higherli.library.demo;

import java.util.Date;

/**
 * 本例用来阐述规则：“构造器决不能调用可被覆盖的方法” 如果违反上面的规则极有可能会导致程序失败。<br>
 * 这是因为：超类的构造器在子类的构造器之前运行， <strong>所以子类覆盖版本的方法将会在子类的构造器运行之前就先被调用。
 * 如果该覆盖版本的方法依赖于子类构造器所执行的任何初始化工作，该方法将不会如预期般执行</strong>
 */
public class ExtendsAndConstructor {
	public static void main(String[] args) {
		Sub sub = new Sub();
		sub.overrideMe();
	}
}

abstract class Super {
	public Super() {
		overrideMe(); // 父类在调用该方法时会调用子类的实现
	}

	public abstract void overrideMe();
}

class Sub extends Super {
	public Date date;

	public Sub() { // 默认调用父类无参构造函数
		this.date = new Date();
	}

	@Override
	public void overrideMe() {
		Date now = new Date();
		if (now.before(date)) {
			System.out.println("Date Error!");
		}
	}

}
