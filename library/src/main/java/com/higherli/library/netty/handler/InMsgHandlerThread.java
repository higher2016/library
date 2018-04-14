package com.higherli.library.netty.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.higherli.library.log.LoggerUtil;
import com.higherli.library.netty.config.Config;
import com.higherli.library.netty.lib.ServerEvent;

/**
 * 用户线程
 */
public class InMsgHandlerThread extends Thread {
	private BlockingQueue<ServerEvent> eventQueue;
	private volatile boolean running = true;

	public InMsgHandlerThread(String name) {
		super(name);
		this.eventQueue = new ArrayBlockingQueue<ServerEvent>(Config.MAX_INCOMING_QUEUE);

		this.setUncaughtExceptionHandler(ThreadUncaughtExceptionHandler.getHandler());
		this.setDaemon(true);
	}

	@Override
	public void run() {
		while (running) {
			try {
				ServerEvent se = eventQueue.take();
				se.handleEvent();
			} catch (InterruptedException e) {
				if (running) {
					LoggerUtil.errorf("%s unexpected InterruptedException:", e, this.getName());
				}
			} catch (Throwable t) {
				LoggerUtil.errorf("%s error : ", t, this.getName());
			}
		}
	}

	public final boolean acceptEvent(ServerEvent serverEvent) {
		if (!eventQueue.offer(serverEvent)) {
			LoggerUtil.error("in msg handle queue full");
			return false;
		} else {
			return true;
		}
	}

	public int getEventQueueSize() {
		return eventQueue.size();
	}

	public void shutdown() {
		running = false;
		this.interrupt();
	}
}
