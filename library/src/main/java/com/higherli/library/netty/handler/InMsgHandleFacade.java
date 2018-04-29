package com.higherli.library.netty.handler;

import com.higherli.library.netty.Session;
import com.higherli.library.netty.config.Config;
import com.higherli.library.netty.domain.User;
import com.higherli.library.netty.lib.ExtensionHandler;
import com.higherli.library.netty.lib.IServerEventHandler;
import com.higherli.library.netty.lib.ServerEvent;
import com.higherli.library.netty.message.requset.RequestEvent;

/**
 * 派发消息给处理线程的总入口(facade).主要目的是封装是否区分系统线程和扩展线程的判断逻辑. 封装所有的线程池
 */
public class InMsgHandleFacade {

	public static final InMsgHandleFacade INSTANCE = new InMsgHandleFacade();
	private InMsgHandlerThreadPool extWorkerPool;
	private IServerEventHandler exthandler;

	private InMsgHandleFacade() {
		init();
	}

	private void init() {
		extWorkerPool = new InMsgHandlerThreadPool("ExHandler", Config.EXT_HANDLER_THREADS_NUM);
		exthandler = new ExtensionHandler();
	}

	public int extensionHandlerThreadCount() {
		return extWorkerPool.threadNum;
	}

	public void acceptEvent(RequestEvent reqEvent, Session session, User user) {
		int userId = user == null ? 0 : user.getUserId();
		extWorkerPool.acceptEvent(new ServerEvent(reqEvent, userId, user, exthandler), session.extDistributeKey);
	}

	public void shutdown() throws InterruptedException {
		extWorkerPool.shutdown();
	}
}
