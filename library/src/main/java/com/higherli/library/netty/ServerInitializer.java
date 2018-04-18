package com.higherli.library.netty;

import com.higherli.library.netty.channelinboundhandle.HttpServerHandler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class ServerInitializer extends ChannelInitializer<Channel> {
	public ServerInitializer() {
	}

	@Override
	protected void initChannel(Channel ch) throws Exception {
		ChannelPipeline p = ch.pipeline();
		p.addLast(new HttpServerCodec());
		p.addLast(new HttpObjectAggregator(64 * 1024));
		// p.addLast(new HttpRequestHandler("/ws"));
		// p.addLast(new WebSocketServerProtocolHandler("/ws"));
		// p.addLast(new TextWebSocketFrameHandler(group));
		p.addLast("deflater", new HttpContentCompressor());
		p.addLast(new HttpServerHandler());
	}

}
