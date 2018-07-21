package com.higherli.library.extensions.util;

public class SimpleResult<T> implements Result<T> {
	private int resultCode;
	private T value;

	public SimpleResult() {
	}

	public SimpleResult(ResultCodeEnums resultCodeEnum, T value) {
		this.resultCode = resultCodeEnum.resultCode;
		this.value = value;
	}

	@Override
	public boolean isSuccess() {
		return ResultCodeEnums.SUCCESS.resultCode == resultCode;
	}

	@Override
	public boolean isFailure() {
		return ResultCodeEnums.SUCCESS.resultCode != resultCode;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	public void setValue(T value) {
		this.value = value;
	}

	public int getResultCode() {
		return resultCode;
	}

	public void setResultCode(int resultCode) {
		this.resultCode = resultCode;
	}

}
