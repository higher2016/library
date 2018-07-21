package com.higherli.library.extensions.util;

/**
 * 标记处理前端请求的错误码和描述
 */
public enum ResultCodeEnums {
	SUCCESS(1, "执行成功"), PARM_ERROR(2, "参数错误");
	public final int resultCode;

	private ResultCodeEnums(int resultCode, String desc) {
		this.resultCode = resultCode;
	}
}
