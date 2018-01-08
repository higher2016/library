package com.higherli.library.event.asyn;

import com.higherli.library.event.EventDispatcher;
import com.higherli.library.thread.MultiThreadQueueWorker;
import com.higherli.library.thread.MultiThreadQueueWorker.IProcessor;

/**
 * 通用异步事件分发器，事件处理异步进行，同一事件必定是同一线程处理。<br>
 * 暂不提供移除监听操作<br>
 * 
 * @see EventDispatcher
 */
public class AsynEventDispatcher extends EventDispatcher<AsynEventType, AsynEvent, AsynEventProcessPriority> implements IProcessor<AsynEventType, AsynEvent> {

	public static final AsynEventDispatcher INSTANCE = new AsynEventDispatcher();
	private final MultiThreadQueueWorker<AsynEventType, AsynEvent> worker;

	private AsynEventDispatcher() {
		super(AsynEventType.class, AsynEvent.class, ListenAsynEvent.class);
		worker = new MultiThreadQueueWorker<AsynEventType, AsynEvent>("AsynEventDispatcher", 1, 4, this);
	}
	
	public void start(){
		worker.start();
	}

	public void shutdown() {
		worker.shutdown();
	}

	/**
	 * 这里返回true表示将任务加载到队列中，并不表示任务完成
	 */
	@Override
	public boolean dispatchEvent(AsynEvent event) {
		AsynEventType eventType = event.getEventType();
		if (eventType == null) {
			throw new RuntimeException("<AsynEventDispatcher> event type not exist!");
		}
		return worker.accept(eventType, event);
	}

	public void process(AsynEventType eventType, AsynEvent event) {
		doEvent(eventType, event);
	}
}
