package com.higherli.library.netty.channelinboundhandle.process;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.higherli.library.spring.base.SpringInit;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
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
import io.netty.util.AsciiString;
import io.netty.util.CharsetUtil;

public class HttpRequestProcess implements IProcess<FullHttpRequest> {
	public static final HttpRequestProcess INSTANCE = new HttpRequestProcess();
	private static final byte[] EMPTY = {};
	private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);
	private static final AsciiString CONTENT_TYPE = new AsciiString("Content-Type");
	private static final AsciiString CONTENT_LENGTH = new AsciiString("Content-Length");
	private static final AsciiString CONNECTION = new AsciiString("Connection");
	private static final AsciiString KEEP_ALIVE = new AsciiString("keep-alive");

	private HttpRequestProcess() {
	}

	public void process(ChannelHandlerContext ctx, FullHttpRequest req) {
		if (HttpUtil.is100ContinueExpected(req)) {
			ctx.write(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE));
		}
		if (req.method() == HttpMethod.GET) {
			handleGetMethod(req, ctx);
		} else if (req.method() == HttpMethod.POST) {
			handlePostMethod(req, ctx);
		}
	}

	private void handleGetMethod(FullHttpRequest req, ChannelHandlerContext ctx) {
		if (isHttpRequestIsWebSocketHandshake(req)) {
			ctx.fireChannelRead(req.retain());
		} else {
			Map<String, String> params = getParamsFromRequest(req);
			handleRequest(req, ctx, params);
		}
	}

	private boolean isHttpRequestIsWebSocketHandshake(HttpRequest request) {
		return "websocket".equalsIgnoreCase(request.headers().get("Upgrade"));
	}

	private void handlePostMethod(HttpRequest req, ChannelHandlerContext ctx) {
		Map<String, String> params = new HashMap<String, String>();
		HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(factory, req, CharsetUtil.UTF_8);
		if (decoder.hasNext()) {
			params = getParamsFromRequest(req);
			List<InterfaceHttpData> dataList = decoder.getBodyHttpDatas();
			for (InterfaceHttpData interfaceHttpData : dataList) {
				Attribute attribute = (Attribute) interfaceHttpData;
				try {
					params.put(attribute.getName(), attribute.getValue());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		handleRequest(req, ctx, params);
	}

	private Map<String, String> getParamsFromRequest(HttpRequest req) {
		Map<String, String> resultParams = new HashMap<String, String>();
		QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri(), CharsetUtil.UTF_8);
		Map<String, List<String>> paramsTmp = queryStringDecoder.parameters();
		for (Entry<String, List<String>> entry : paramsTmp.entrySet()) {
			for (String v : entry.getValue()) {
				resultParams.put(entry.getKey(), v);
				break;
			}
		}
		return resultParams;
	}

	private void handleRequest(HttpRequest req, ChannelHandlerContext ctx, Map<String, String> params) {
		byte[] content = EMPTY;
		try {
			params.put("ip", String.valueOf(ctx.channel().remoteAddress()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		content = handlerRequest(req, params);
		FullHttpResponse response = handleResult2Response(content);
		if (HttpUtil.isKeepAlive(req)) {
			response.headers().set(CONNECTION, KEEP_ALIVE);
			ctx.write(response);
		} else {
			ctx.write(response).addListener(ChannelFutureListener.CLOSE);
		}
	}

	private FullHttpResponse handleResult2Response(byte[] content) {
		FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK,
				Unpooled.wrappedBuffer(content));
		response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");
		response.headers().set("Access-Control-Allow-Origin", "*");
		response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
		return response;
	}

	/**
	 * 处理命令执行请求
	 * 
	 * @param params
	 * @return
	 */
	private byte[] handlerRequest(HttpRequest req, Map<String, String> params) {
		Object target = null;
		try {
			String[] arr = null;
			if (params.containsKey("action")) {
				// http://localhost:9081/login?action=login.test
				String action = params.get("action");
				if (action == null) {
					return EMPTY;
				}
				arr = action.split("\\.");
				if (arr.length != 2) {
					return EMPTY;
				}
			} else {
				// http://localhost:9801/login/test.mine 支持这种格式访问
				if (req.uri() == null || req.uri().length() < 1) {
					return EMPTY;
				}
				String uri = req.uri();
				int endIndex = uri.indexOf('?');
				if (endIndex > 1) {
					uri = uri.substring(1, endIndex);
				} else {
					uri = uri.substring(1);
				}
				if (!uri.endsWith(".mine"))
					return EMPTY;
				else {
					uri = uri.substring(0, uri.length() - 5);
				}
				arr = uri.split("/");
			}
			// class 和 action 都不为空
			if (null != arr && arr.length > 1 && StringUtils.isNotBlank(arr[0]) && StringUtils.isNotBlank(arr[1])) {
				target = SpringInit.getBean("HH" + firstToUpperCase(arr[0]));
				Method method = target.getClass().getMethod(arr[1], Map.class);
				Object ret = method.invoke(target, params);
				if (ret == null) {
					return EMPTY;
				}
				if (params.containsKey("file")) {
					return (byte[]) ret;
				} else {
					// return JSONObject.toJSONString(ret).getBytes();
					return String.valueOf(ret).getBytes();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EMPTY;
	}

	/**
	 * 首字母大写
	 */
	private String firstToUpperCase(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1);
	}

}
