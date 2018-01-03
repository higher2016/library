package com.higherli.library.netty.http_process;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;

public class ProcessGET implements IProcessHttpRequest {

	public Map<String, String> getParam(HttpRequest request, ChannelHandlerContext ctx) {
		HttpHeaders headers = request.headers();
		Map<String, String> params = new HashMap<String, String>();
		if ("websocket".equalsIgnoreCase(headers.get("Upgrade"))) {
			WebSocketServerHandshakerFactory wsShakerFactory = new WebSocketServerHandshakerFactory(
					"ws://" + request.headers().get(HttpHeaders.Names.HOST), null, false);
			WebSocketServerHandshaker wsShakerHandler = wsShakerFactory.newHandshaker(request);
			if (null == wsShakerHandler) {
				// 无法处理的websocket版本
				WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			} else {
				// 向客户端发送websocket握手,完成握手
				// 客户端收到的状态是101 sitching protocol
				wsShakerHandler.handshake(ctx.channel(), (FullHttpRequest) request);
			}
		} else {
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(request.getUri(), CharsetUtil.UTF_8);
			Map<String, List<String>> paramsTmp = queryStringDecoder.parameters();
			for (Entry<String, List<String>> entry : paramsTmp.entrySet()) {
				for (String v : entry.getValue()) {
					params.put(entry.getKey(), v);
					break;
				}
			}
		}
		return params;
	}

}
