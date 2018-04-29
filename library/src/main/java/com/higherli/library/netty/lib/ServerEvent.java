package com.higherli.library.netty.lib;

import com.higherli.library.netty.domain.User;
import com.higherli.library.netty.handler.InMsgHandleFacade;

public class ServerEvent {
	private final Object eventData;
	private final int userId;
	private final User user;
	private final IServerEventHandler handler;


	public ServerEvent(Object eventData, int userId, User user, IServerEventHandler handler) {
		this.eventData = eventData;
		this.userId = userId;
		this.user = user;
		this.handler = handler;
	}

	public final void handleEvent() {
		this.handler.handleEvent(this.user, this.eventData);
	}

	public final int getDistributeKey() {
		if (this.user == null) {
			return Math.max(0, this.userId % InMsgHandleFacade.INSTANCE.extensionHandlerThreadCount());
		}
		return this.user.getEventDistributeKey();
	}
}

