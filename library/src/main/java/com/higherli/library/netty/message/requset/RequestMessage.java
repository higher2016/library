package com.higherli.library.netty.message.requset;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.higherli.library.log.LoggerUtil;

/**
 * 请求消息体<br>
 */
public class RequestMessage {
	private final JSONObject jsonObject;

	public static final RequestMessage parse(String jsonStr) {
		JSONObject jsonObject;
		try {
			jsonObject = JSONObject.parseObject(jsonStr);
			verifyRequestMessage(jsonObject);
		} catch (JSONException | IllegalArgumentException e) {
			LoggerUtil.error("<<RequestMessage>> json object parse error!", e);
			throw e;
		}
		RequestMessage message = new RequestMessage(jsonObject);
		return message;
	}

	private static void verifyRequestMessage(JSONObject jsonObject) {
		if (jsonObject.getInteger("action") == null) {
			throw new IllegalArgumentException("<<RequestMessage>> message does not exist action value");
		}
		if (jsonObject.getInteger("msgType") == null) {
			throw new IllegalArgumentException("<<RequestMessage>> message does not exist msgType value");
		}
		if (jsonObject.getInteger("cmdIndex") == null) {
			throw new IllegalArgumentException("<<RequestMessage>> message does not exist cmdIndex value");
		}
	}

	private RequestMessage(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * 消息类型：用户请求消息——1; 系统请求消息——2
	 */
	public int getMsgType() {
		return jsonObject.getIntValue("msgType");
	}

	/**
	 * 请求的Extension
	 */
	public int getAction() {
		return jsonObject.getIntValue("action");
	}

	/**
	 * 请求的具体方法
	 */
	public int getCmdIndex() {
		return jsonObject.getIntValue("cmdIndex");
	}

	public Object getObject(String key) {
		return jsonObject.get(key);
	}

	public String getString(String key) {
		return (String) jsonObject.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T getObject(String key, T clazz) {
		Object o = jsonObject.get(key);
		if (o == null || o.getClass() != clazz) {
			return null;
		}
		return (T) o;
	}
}
