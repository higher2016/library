package com.higherli.library.netty.domain;

import io.netty.channel.Channel;

public class User {
	private final int userId;
	private final int eventDistributeKey; // 决定用户被分到哪一个线程，这个值就是用户所在用户线程池的index
	private final Channel userChannel;
	
	public User(int userId, int eventDistributeKey,Channel userChannel) {
		super();
		this.userId = userId;
		this.eventDistributeKey = eventDistributeKey;
		this.userChannel = userChannel;
	}

	public int getUserId() {
		return userId;
	}

	public int getEventDistributeKey() {
		return eventDistributeKey;
	}

	public Channel getUserChannel() {
		return userChannel;
	}
	
}
