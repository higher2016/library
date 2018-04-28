package com.higherli.library.netty.channelinboundhandle;

import org.apache.commons.lang3.StringUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	public TextWebSocketFrameHandler() {
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		String requestStr = msg.text();
		if (StringUtils.isBlank(requestStr)) {
			ctx.channel().writeAndFlush("{\"error\":\"the request json can not be null!\"}");
			return;
		}
		System.out.println(requestStr);
		ctx.writeAndFlush(msg.retain());
//		ctx.fireChannelRead(msg);
	}
}
