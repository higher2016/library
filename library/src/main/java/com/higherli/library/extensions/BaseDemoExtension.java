package com.higherli.library.extensions;

import com.higherli.library.netty.message.requset.RequestMessage;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class BaseDemoExtension implements IExtension {

	@CmdCommand(cmdIndex = 1)
	public void test(RequestMessage message, Channel channel) {
		channel.writeAndFlush(new TextWebSocketFrame("OK!!"));
	}
}
