package com.higherli.library.netty.channelinboundhandle.process;

import org.apache.commons.lang3.StringUtils;

import com.higherli.library.log.LoggerUtil;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketFrameProcess implements IProcess<TextWebSocketFrame>{
	public static final TextWebSocketFrameProcess INSTANCE = new TextWebSocketFrameProcess();

	private TextWebSocketFrameProcess() {
	}

	public void process(ChannelHandlerContext ctx, TextWebSocketFrame msg) {
		if (msg == null) {
			ctx.channel().writeAndFlush("{\"error\":\"the request json can not be null!\"}");
			LoggerUtil.error("Request josn is null");
			return;
		}
		String requestStr = msg.text();
		if (StringUtils.isBlank(requestStr)) {
			ctx.channel().writeAndFlush("{\"error\":\"the request json can not be null!\"}");
			LoggerUtil.error("Request josn is empty");
			return;
		}
		// TODO 将处理逻辑写完
		// TODO 确定参数数据结构、如何处理相关的请求（百田的异步处理方式还是唯思同步处理方式）
	}
}
