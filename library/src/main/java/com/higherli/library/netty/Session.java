package com.higherli.library.netty;

import com.higherli.library.netty.domain.User;

import io.netty.channel.Channel;

public class Session {
	public User user;
	// 该值每次session都不同，仅用于校验消息的序列号 - msgSeqNo
	private int sessionHash;
	// 供序列号生成用，不想每次都user != null, user.getUserId()
	private int userId = 0;
	public int sysDistributeKey;
	public int extDistributeKey;
	public Channel channel;
}
