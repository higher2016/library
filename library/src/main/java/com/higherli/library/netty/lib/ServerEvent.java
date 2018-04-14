package com.higherli.library.netty.lib;

import com.higherli.library.netty.domain.User;
import com.higherli.library.netty.handler.InMsgHandleFacade;

//会在系统和扩展队列出现的事件，不仅仅限于从客户端发起的事件RequestEvent。
//扩展队列准备开发。
public class ServerEvent {
	private final Object eventData;
	// 在ServerEvent里加入User是为了当用户断线时，排队的请求仍然能被处理
	// 如果不加User，用SocketChannel再去查User，如果用户断线，可能查不到，
	// 查不到User请求是无法继续处理的。
	// user只有在登录请求时是null
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
			return Math.max(0, this.userId % InMsgHandleFacade.INSTANCE.extensionHandlerThreadCount());// 默认这样吧
		}
		return this.user.getEventDistributeKey();
	}
}

