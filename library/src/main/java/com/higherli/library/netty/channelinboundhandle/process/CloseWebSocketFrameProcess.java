package com.higherli.library.netty.channelinboundhandle.process;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;

public class CloseWebSocketFrameProcess implements IProcess<CloseWebSocketFrame> {
	public static final CloseWebSocketFrameProcess INSTANCE = new CloseWebSocketFrameProcess();

	private CloseWebSocketFrameProcess() {
	}

	public void process(ChannelHandlerContext ctx, CloseWebSocketFrame msg) {
		ctx.channel().close();
	}
}
