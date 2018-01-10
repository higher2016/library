package com.higherli.library.event.syn;

/**
 * 同步事件标记,用于表示抛出的同步事件是哪个
 */
public enum SynEventType {
	UNKNOWN, 
	CHANNEL_ACTIVE, // 有连接进站
	CHANNEL_IN_ACTIVE, // 有连接断开
	;
}
