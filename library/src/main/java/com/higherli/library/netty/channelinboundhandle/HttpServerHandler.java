package com.higherli.library.netty.channelinboundhandle;

import com.higherli.library.event.syn.SynEventDispatcher;
import com.higherli.library.event.syn.eventType.SynEventChannelActive;
import com.higherli.library.event.syn.eventType.SynEventChannelInActive;
import com.higherli.library.netty.channelinboundhandle.process.HttpRequestProcess;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;

/**
 * 处理HTTP请求
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		SynEventDispatcher.INSTANCE.dispatchEvent(new SynEventChannelActive());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SynEventDispatcher.INSTANCE.dispatchEvent(new SynEventChannelInActive());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
		HttpRequestProcess.INSTANCE.process(ctx, msg);
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
