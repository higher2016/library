package com.higherli.library.extensions;

import java.lang.reflect.Constructor;

import org.junit.Before;
import org.junit.Test;

import com.higherli.library.base.BaseSpringAndJunit4Test;
import com.higherli.library.log.LoggerUtil;
import com.higherli.library.netty.message.requset.RequestMessage;
import com.higherli.library.netty.message.requset.RequestMessageTest;

import io.netty.channel.ChannelHandlerContext;

public class ExtensionMangerTest extends BaseSpringAndJunit4Test {

	private ExtensionManger manger;

	@Before
	public void before() throws Exception {
		manger = createExtensionMangerByReflect();
	}

	public static ExtensionManger createExtensionMangerByReflect() throws Exception {
		Constructor<?> c = ExtensionManger.class.getDeclaredConstructors()[0];
		c.setAccessible(true);
		return (ExtensionManger) c.newInstance();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testRegisteredExtensionException() {
		manger.registeredExtension(1, new TestExtension());
		manger.registeredExtension(1, new TestExtension());
	}

	@Test
	public void testHandleRequest() {
		manger.registeredExtension(2, new TestExtension());
		manger.handleRequest(RequestMessage.parse(RequestMessageTest.getTemplateRequestMessageStr()), null);
	}

	static class TestExtension implements IExtension {
		@CmdCommand(cmdIndex = 1)
		public void test(RequestMessage message, ChannelHandlerContext ctx) {
			LoggerUtil.infof("Message msgType[%s] action[%s] cmdIndex[%s]", message.getMsgType(), message.getAction(),
					message.getCmdIndex());
		}
	}

}
