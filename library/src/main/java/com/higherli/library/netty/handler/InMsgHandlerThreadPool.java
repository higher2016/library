package com.higherli.library.netty.handler;

import com.higherli.library.netty.lib.ServerEvent;

/**
 * 用户请求线程池
 */
public class InMsgHandlerThreadPool {
	private final String name;
	public final int threadNum;
	private InMsgHandlerThread[] threads;

	public InMsgHandlerThreadPool(String name, int threadNum) {
		this.name = name;
		this.threadNum = threadNum;
		initThreads();
	}

	public void shutdown() throws InterruptedException {
		for (InMsgHandlerThread w : this.threads) {
			w.shutdown();
		}
		for (Thread t : this.threads) {
			t.join();
		}
	}

	public final boolean acceptEvent(ServerEvent serverEvent, int distributeKey) {
		InMsgHandlerThread thread = this.threads[distributeKey];
		// TODO 这里可以做统计（总请求量）成功和不成功的
		return thread.acceptEvent(serverEvent);
	}

	public String getName() {
		return this.name;
	}

	public int getEventQueueSize() {
		int total = 0;
		for (InMsgHandlerThread t : this.threads) {
			total += t.getEventQueueSize();
		}
		return total;
	}

	private void initThreads() {
		this.threads = new InMsgHandlerThread[threadNum];
		for (int i = 0; i < threadNum; i++) {
			threads[i] = new InMsgHandlerThread(String.format("%s-%s", this.name, i));
			threads[i].start();
		}
	}
}
