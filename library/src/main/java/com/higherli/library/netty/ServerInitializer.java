package com.higherli.library.netty;

import com.higherli.library.netty.http_process.HttpRequestHandler;
import com.higherli.library.netty.http_process.TextWebSocketFrameHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ServerInitializer extends ChannelInitializer<Channel> {
	private final ChannelGroup group;

	public ServerInitializer(ChannelGroup group) {
		this.group = group;
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new ChunkedWriteHandler());
		p.addLast(new HttpObjectAggregator(64 * 1024));
		p.addLast(new HttpRequestHandler("/ws"));
		p.addLast(new WebSocketServerProtocolHandler("/ws"));
		p.addLast(new TextWebSocketFrameHandler(group));
	}

}
