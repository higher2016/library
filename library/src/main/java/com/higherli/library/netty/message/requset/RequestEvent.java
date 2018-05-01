package com.higherli.library.netty.message.requset;

import io.netty.channel.Channel;

public class RequestEvent {
	private Channel senderChannel;
	private RequestMessage requestMessage;

	public RequestEvent(Channel senderChannel, RequestMessage requestMessage) {
		this.senderChannel = senderChannel;
		this.requestMessage = requestMessage;
	}

	public Channel getSenderChannel() {
		return senderChannel;
	}

	public RequestMessage getRequestMessage() {
		return requestMessage;
	}
}
