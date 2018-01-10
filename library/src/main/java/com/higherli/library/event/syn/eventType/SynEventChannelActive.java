package com.higherli.library.event.syn.eventType;

import com.higherli.library.event.syn.SynEvent;
import com.higherli.library.event.syn.SynEventType;

/**
 * 连接进站事件
 */
public class SynEventChannelActive implements SynEvent {

	public SynEventType getEventType() {
		return SynEventType.CHANNEL_ACTIVE;
	}
}
