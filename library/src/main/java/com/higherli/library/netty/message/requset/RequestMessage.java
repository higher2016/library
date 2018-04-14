package com.higherli.library.netty.message.requset;

/**
 * 请求消息体<br>
 */
public class RequestMessage<T> {
	private final int msgType;
	private final T data;

	public RequestMessage(int msgType, T data) {
		super();
		this.msgType = msgType;
		this.data = data;
	}

	public int getMsgType() {
		return msgType;
	}

	public T getData() {
		return data;
	}

}
