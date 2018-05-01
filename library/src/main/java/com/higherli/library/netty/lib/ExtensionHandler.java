package com.higherli.library.netty.lib;

import com.higherli.library.extensions.ExtensionManger;
import com.higherli.library.netty.domain.User;
import com.higherli.library.netty.message.requset.RequestEvent;

public class ExtensionHandler implements IServerEventHandler {

	public ExtensionHandler() {
	}

	public void handleEvent(User user, Object event) {
		RequestEvent  requestEvent = (RequestEvent) event;
		ExtensionManger.INSTANCE.handleRequest(requestEvent.getRequestMessage(), user.getUserChannel());
	}

	public void init() {
	}

}
