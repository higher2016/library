package com.higherli.library.netty.channelinboundhandle;

import com.higherli.library.event.syn.SynEventDispatcher;
import com.higherli.library.event.syn.eventType.SynEventChannelActive;
import com.higherli.library.event.syn.eventType.SynEventChannelInActive;
import com.higherli.library.log.LoggerUtil;
import com.higherli.library.netty.channelinboundhandle.process.CloseWebSocketFrameProcess;
import com.higherli.library.netty.channelinboundhandle.process.HttpRequestProcess;
import com.higherli.library.netty.channelinboundhandle.process.TextWebSocketFrameProcess;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		SynEventDispatcher.INSTANCE.dispatchEvent(new SynEventChannelActive());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SynEventDispatcher.INSTANCE.dispatchEvent(new SynEventChannelInActive());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			HttpRequestProcess.INSTANCE.process(ctx, (HttpRequest) msg);
		} else {
			if (msg instanceof CloseWebSocketFrame) {
				CloseWebSocketFrameProcess.INSTANCE.process(ctx, (CloseWebSocketFrame) msg);
			} else if (msg instanceof TextWebSocketFrame) {
				TextWebSocketFrameProcess.INSTANCE.process(ctx, (TextWebSocketFrame) msg);
			} else if (msg instanceof BinaryWebSocketFrame) {
				LoggerUtil.error("request:" + msg);
			} else {
				LoggerUtil.error("request:" + msg);
			}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
