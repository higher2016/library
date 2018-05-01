package com.higherli.library.netty.message.requset;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.higherli.library.base.BaseSpringAndJunit4Test;

public class RequestMessageTest extends BaseSpringAndJunit4Test{

	@Test(expected = IllegalArgumentException.class)
	public void testParseExpected() {
		String jsonStr = "{\"serviceId\":10086,\"message\":{\"test\":\"1234\",\"test2\":\"456789\"}}";
		RequestMessage.parse(jsonStr);
	}
	
	@Test
	public void testParse(){
		String jsonStr = getTemplateRequestMessageStr();
		RequestMessage message = RequestMessage.parse(jsonStr);
		assertEquals(1, message.getMsgType());
		assertEquals(12, message.getAction());
		assertEquals(6, message.getCmdIndex());
	}
	
	public static String getTemplateRequestMessageStr(){
		return "{\"msgType\":1,\"action\":1,\"cmdIndex\":1,\"message\":{\"test\":\"1234\",\"test2\":\"456789\"}}";
	}

}
