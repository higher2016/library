package com.higherli.library.netty;

import com.higherli.library.netty.channelinboundhandle.TextWebSocketFrameHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * 暂时不理会http请求，只对websocket协议进行处理
 */
public class ServerInitializer extends ChannelInitializer<Channel> {
	public ServerInitializer() {
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(64 * 1024));
		// p.addLast(new HttpServerHandler());
		p.addLast(new WebSocketServerProtocolHandler("/ws"));
		p.addLast(new TextWebSocketFrameHandler());
	}

}
