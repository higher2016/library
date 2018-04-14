package com.higherli.library.netty.message.requset;

import io.netty.channel.Channel;

public class RequestEvent {
	private short action; // 处理消息方式：byte流处理；对象处理
	private Channel senderChannel;
	private byte type; // 事件类型： 用户事件类型；系统事件类型

	public RequestEvent(short action, Channel senderChannel, byte type) {
		this.action = action;
		this.senderChannel = senderChannel;
		this.type = type;
	}

	public short getAction() {
		return action;
	}

	public Channel getSenderChannel() {
		return senderChannel;
	}

	public byte getType() {
		return type;
	}
}
