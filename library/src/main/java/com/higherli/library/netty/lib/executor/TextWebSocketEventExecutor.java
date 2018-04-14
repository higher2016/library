package com.higherli.library.netty.lib.executor;

import com.higherli.library.netty.domain.User;

import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class TextWebSocketEventExecutor implements IServerEventExecutor<TextWebSocketFrame> {

	public void executeEvent(User user, TextWebSocketFrame event) {

	}

}