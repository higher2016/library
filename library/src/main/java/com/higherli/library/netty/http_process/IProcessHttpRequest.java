package com.higherli.library.netty.http_process;

import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public interface IProcessHttpRequest {
	/**
	 * 获取请求所带的参数
	 */
	Map<String, String> getParam(HttpRequest request, ChannelHandlerContext ctx);
}
