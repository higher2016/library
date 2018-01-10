package com.higherli.library.netty.http_process;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.higherli.library.event.syn.SynEventDispatcher;
import com.higherli.library.event.syn.eventType.SynEventChannelActive;
import com.higherli.library.event.syn.eventType.SynEventChannelInActive;
import com.higherli.library.log.LoggerUtil;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.Attribute;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
	private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
	private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
	private static final AsciiString CONNECTION = new AsciiString("Connection");
	private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

	private static final byte[] EMPTY = {};
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE); // Disk

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		SynEventDispatcher.INSTANCE.dispatchEvent(new SynEventChannelActive());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		SynEventDispatcher.INSTANCE.dispatchEvent(new SynEventChannelInActive());
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (msg instanceof HttpRequest) {
			processHttp(ctx, msg);
		} else {
			if (msg instanceof CloseWebSocketFrame) {
				ctx.channel().close();
			} else if (msg instanceof TextWebSocketFrame) {
				processWs(ctx, msg);
			} else if (msg instanceof BinaryWebSocketFrame) {
				// TODO 暂不支持
			} else {
				LoggerUtil.error("request:" + msg);
			}
		}
	}

	private void processWs(ChannelHandlerContext ctx, Object msg) {
		if (msg == null) {
			ctx.channel().writeAndFlush("{\"error\":\"the request json can not be null!\"}");
			LoggerUtil.error("Request josn is null");
			return;
		}
		String requestStr = ((TextWebSocketFrame) msg).text();
		if(StringUtils.isBlank(requestStr)){
			ctx.channel().writeAndFlush("{\"error\":\"the request json can not be null!\"}");
			LoggerUtil.error("Request josn is empty");
			return;
		}
		// TODO 将处理逻辑写完
		// TODO 确定参数数据结构、如何处理相关的请求（百田的异步处理方式还是唯思同步处理方式）
	}

	private void processHttp(ChannelHandlerContext ctx, Object msg) {
		HttpRequest req = (HttpRequest) msg;
		if (HttpUtil.is100ContinueExpected(req)) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
		}
		Map<String, String> params = new HashMap<String, String>();
		if (req.method() == HttpMethod.GET) {
			requestGetMethod(req, ctx, params);
		} else if (req.method() == HttpMethod.POST) {
			requestPostMethod(req, ctx, params);
		}
	}

	private void requestGetMethod(HttpRequest req, ChannelHandlerContext ctx, Map<String, String> params) {
		if ("websocket".equalsIgnoreCase(req.headers().get("Upgrade"))) {
			WebSocketServerHandshakerFactory wsShakerFactory = new WebSocketServerHandshakerFactory(
					"ws://" + req.headers().get(HttpHeaderNames.HOST), null, false);
			WebSocketServerHandshaker wsShakerHandler = wsShakerFactory.newHandshaker(req);
			if (null == wsShakerHandler) {
				WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
			} else {
				wsShakerHandler.handshake(ctx.channel(), req);
			}
			return;
		} else {
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri(), CharsetUtil.UTF_8);
			Map<String, List<String>> paramsTmp = queryStringDecoder.parameters();
			for (Entry<String, List<String>> entry : paramsTmp.entrySet()) {
				for (String v : entry.getValue()) {
					params.put(entry.getKey(), v);
					break;
				}
			}
		}

		byte[] content = EMPTY;
		try {
			params.put("ip", String.valueOf(ctx.channel().remoteAddress()));
			content = handlerRequest(req, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(content));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.headers().set("Access-Control-Allow-Origin", "*");
		response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
		if (HttpUtil.isKeepAlive(req)) {
			response.headers().set(CONNECTION, KEEP_ALIVE);
			ctx.write(response);
		} else {
			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		}
	}

	/**
	 * 处理命令执行请求
	 * 
	 * @param params
	 * @return
	 */
	private byte[] handlerRequest(HttpRequest req, Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	private void requestPostMethod(HttpRequest req, ChannelHandlerContext ctx, Map<String, String> params) {
		HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, req, CharsetUtil.UTF_8);
		if (decoder.hasNext()) {
			List<InterfaceHttpData> dataList = decoder.getBodyHttpDatas();
			QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri(), CharsetUtil.UTF_8);
			Map<String, List<String>> paramsTmp = queryStringDecoder.parameters();
			for (Entry<String, List<String>> entry : paramsTmp.entrySet()) {
				for (String v : entry.getValue()) {
					params.put(entry.getKey(), v);
					break;
				}
			}
			for (InterfaceHttpData interfaceHttpData : dataList) {
				Attribute attribute = (Attribute) interfaceHttpData;
				try {
					params.put(attribute.getName(), attribute.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

}
