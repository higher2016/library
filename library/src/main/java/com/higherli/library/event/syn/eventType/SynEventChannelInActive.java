package com.higherli.library.event.syn.eventType;

import com.higherli.library.event.syn.SynEvent;
import com.higherli.library.event.syn.SynEventType;

/**
 * 连接断开事件
 */
public class SynEventChannelInActive implements SynEvent {
	public SynEventType getEventType() {
		return SynEventType.CHANNEL_IN_ACTIVE;
	}
}
