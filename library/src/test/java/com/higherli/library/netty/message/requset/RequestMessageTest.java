package com.higherli.library.netty.message.requset;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RequestMessageTest {

	@Test(expected = IllegalArgumentException.class)
	public void testParseExpected() {
		String jsonStr = "{\"serviceId\":10086,\"message\":{\"test\":\"1234\",\"test2\":\"456789\"}}";
		RequestMessage.parse(jsonStr);
	}
	
	@Test
	public void testParse(){
		String jsonStr = "{\"serviceId\":10086,\"msgType\":1,\"action\":12,\"cmdIndex\":6,\"message\":{\"test\":\"1234\",\"test2\":\"456789\"}}";
		RequestMessage message = RequestMessage.parse(jsonStr);
		assertEquals(1, message.getMsgType());
		assertEquals(12, message.getAction());
		assertEquals(6, message.getCmdIndex());
	}

}
