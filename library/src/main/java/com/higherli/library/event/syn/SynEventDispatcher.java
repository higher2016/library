package com.higherli.library.event.syn;

import com.higherli.library.event.EventDispatcher;

/**
 * 同步事件分发器的实现
 */
public class SynEventDispatcher extends EventDispatcher<SynEventType, SynEvent, SynEventProcessPriority> {

	public static final SynEventDispatcher INSTANCE = new SynEventDispatcher();

	private SynEventDispatcher() {
		super(SynEventType.class, SynEvent.class, ListenSynEvent.class);
	}

	@Override
	public boolean dispatchEvent(SynEvent event) {
		SynEventType eventType = event.getEventType();
		if (eventType == null) {
			throw new RuntimeException("<SynEventDispatcher> event type not exist!");
		}
		return doEvent(eventType, event);
	}
}
