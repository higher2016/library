package com.higherli.library.extensions.util;

/**
 * 执行结果抽象接口
 */
public interface Result<T> {
	public boolean isSuccess();

	public boolean isFailure();

	public T getValue();

	public void setValue(T value);
}
