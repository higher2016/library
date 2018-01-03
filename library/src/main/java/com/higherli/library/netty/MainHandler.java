package com.higherli.library.netty;

import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;

@Sharable // 类被Shareable标记，将会导致所有new的对象都是同一个实例
public class MainHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//		if (msg instanceof HttpRequest) {
//			processHttp(ctx, (HttpRequest) msg);
//		}
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) {
	}

	private void processHttp(ChannelHandlerContext ctx, HttpRequest req) {
		// 在使用curl做POST的时候, 当要POST的数据大于1024字节的时候, curl并不会直接就发起POST请求, 而是会分为俩步:
		// 1. 发送一个请求, 包含一个Expect:100-continue, 询问Server使用愿意接受数据
		// 2. 接收到Server返回的100-continue应答以后, 才把数据POST给Server
		if (HttpHeaders.is100ContinueExpected(req)) {
			// 这里马上回复客户端，表示愿意
			ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
		}
	}
}
