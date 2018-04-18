package com.higherli.library.netty.channelinboundhandle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
	private final ChannelGroup group;

	public TextWebSocketFrameHandler(ChannelGroup group) {
		this.group = group;
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
			handshakeComplete(ctx);
		} else {
			super.userEventTriggered(ctx, evt);
		}
	}

	/**
	 * 客户端与服务端握手成功
	 * @param ctx
	 */
	private void handshakeComplete(ChannelHandlerContext ctx) {
		ctx.pipeline().remove(HttpRequestHandler.class);
		group.writeAndFlush(new TextWebSocketFrame("Client " + ctx.channel() + " joined"));
		group.add(ctx.channel());
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		group.writeAndFlush(msg.retain());
	}
}
