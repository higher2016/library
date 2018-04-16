package com.higherli.library.netty;

import java.net.InetSocketAddress;

import com.higherli.library.netty.config.Config;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;

public class Server extends Thread {
	private final EventLoopGroup bossGroup = new NioEventLoopGroup(2, new DefaultThreadFactory("bossGroup"));
	private final EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 3,
			new DefaultThreadFactory("workerGroup"));
	private Channel channel;

	private ChannelFuture start(InetSocketAddress address) {
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(createInitializer());
		ChannelFuture future = bootstrap.bind(address);
		future.syncUninterruptibly();
		channel = future.channel();
		return future;
	}

	protected ChannelInitializer<Channel> createInitializer() {
		return new ServerInitializer();
	}

	public void destroy() {
		if (channel != null) {
			channel.close();
		}
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}

	@Override
	public void run() {
		final Server endpoint = new Server();
		ChannelFuture future = endpoint.start(new InetSocketAddress(Config.INET_PORT));
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				endpoint.destroy();
			}
		});
		future.channel().closeFuture().syncUninterruptibly();
	}
}
