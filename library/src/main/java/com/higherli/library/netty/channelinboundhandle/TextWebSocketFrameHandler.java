package com.higherli.library.netty.channelinboundhandle;

import org.apache.commons.lang3.StringUtils;

import com.higherli.library.log.LoggerUtil;
import com.higherli.library.netty.channelinboundhandle.process.TextWebSocketProcess;

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
			LoggerUtil.error("The request json can not be null!");
			return;
		}
		TextWebSocketProcess.INSTANCE.process(ctx, requestStr);
	}
}
