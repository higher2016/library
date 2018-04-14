package com.higherli.library.netty.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.higherli.library.netty.handler.InMsgHandlerThreadPool;

//Server运行状况统计
//避免过多侵入正常代码，统计相关代码尽量集中在这里。
/**
 * TODO 
 */
public class ServerState implements Runnable{
	
	private List<InMsgHandlerThreadPool> handlers = new ArrayList<InMsgHandlerThreadPool>();
	private volatile Counters counters = new Counters();
	
	// 单主线程register
	public int registerInMsgHandler(InMsgHandlerThreadPool handler) {
		handlers.add(handler);
		if (handlers.size() > this.counters.inMsgHandlerMsgs.length) {
			throw new IllegalArgumentException("too many in message handler thread pool.");
		}

		// 返回用于increaseInMsgs方法
		int msgHandlerCounterIndex = handlers.size() - 1;
		return msgHandlerCounterIndex;
	}
	
	@SuppressWarnings("unused")
	private static class Counters {
		private AtomicInteger outMsgs = new AtomicInteger(0);
		private AtomicInteger inBytes = new AtomicInteger(0);
		private AtomicInteger outBytes = new AtomicInteger(0);
		private AtomicInteger inDropMsgs = new AtomicInteger(0);
		private AtomicInteger outDropMsgs = new AtomicInteger(0);
		// 分处理线程池分别统计上行消息
		// 不会超过这么多线程池吧？
		// 目前就1到2个，为以后暴露定制线程池功能，多定义一点，即使不够了启动时registerInMsgHandler会报错。
		private final static int MAX_HANDLER = 100;
		private AtomicInteger[] inMsgHandlerMsgs = new AtomicInteger[MAX_HANDLER];

		Counters() {
			for (int i = 0; i < MAX_HANDLER; i++) {
				inMsgHandlerMsgs[i] = new AtomicInteger(0);
			}
		}
	}

	public void run() {
		// TODO Auto-generated method stub
		
	}
}
