package com.higherli.library.netty;

import com.higherli.library.netty.domain.User;

import io.netty.channel.Channel;

public class Session {
	public User user;
	// 供序列号生成用，不想每次都user != null, user.getUserId()
	@SuppressWarnings("unused")
	private int sessionHash;
	// 供序列号生成用，不想每次都user != null, user.getUserId()
	@SuppressWarnings("unused")
	private int userId = 0;
	public int sysDistributeKey;
	public int extDistributeKey;
	public Channel channel;
}
