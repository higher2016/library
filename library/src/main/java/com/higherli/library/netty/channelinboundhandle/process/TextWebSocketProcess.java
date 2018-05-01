package com.higherli.library.netty.channelinboundhandle.process;

import com.higherli.library.netty.domain.User;
import com.higherli.library.netty.handler.InMsgHandleFacade;
import com.higherli.library.netty.message.requset.RequestEvent;
import com.higherli.library.netty.message.requset.RequestMessage;

import io.netty.channel.ChannelHandlerContext;

public class TextWebSocketProcess implements IProcess<String> {

	public static final TextWebSocketProcess INSTANCE = new TextWebSocketProcess();

	private TextWebSocketProcess() {
	}

	@Override
	public void process(ChannelHandlerContext ctx, String msg) {
		RequestEvent event = new RequestEvent(ctx.channel(), RequestMessage.parse(msg));
		User u = new User(1, 1, ctx.channel());
		InMsgHandleFacade.INSTANCE.acceptEvent(event, u);
	}

}
