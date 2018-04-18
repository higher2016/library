package com.higherli.library.netty.channelinboundhandle.process;

import io.netty.channel.ChannelHandlerContext;

public interface IProcess<T> {
	public void process(ChannelHandlerContext ctx, T msg);
}
