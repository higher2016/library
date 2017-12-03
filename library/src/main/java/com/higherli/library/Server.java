package com.higherli.library;

import com.higherli.library.config.Config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

public class Server extends Thread {

	private final EventLoopGroup bossGroup;
	private final EventLoopGroup workerGroup;

	public Server() {
		bossGroup = new NioEventLoopGroup(Config.NETTY_BOOS_THREAD);
		workerGroup = new NioEventLoopGroup(Config.NETTY_WORKER_THREAD);
	}

	@Override
	public void run() {
		ServerBootstrap b = new ServerBootstrap();
		setChannelOption(b);
		b.group(bossGroup, workerGroup);
		b.channel(NioServerSocketChannel.class);
		b.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast("idleStateCheck",
						new IdleStateHandler(Config.CHECK_UNLOGIN_INTERVAL, Integer.MAX_VALUE, Integer.MAX_VALUE));
				pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Config.MAX_MSG_BYTE_LEN, 0, 4, 0, 0));
			}
		});
		try {
			b.bind(Config.inetHost, Config.inetPort).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void setChannelOption(ServerBootstrap bootstrap) {
		bootstrap.option(ChannelOption.SO_REUSEADDR, true);
		// bootstrap.option(ChannelOption.SO_BACKLOG, 128);
		bootstrap.childOption(ChannelOption.TCP_NODELAY, true);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		// bootstrap.childOption(ChannelOption.ALLOCATOR,
		// PooledByteBufAllocator.DEFAULT);
	}
}