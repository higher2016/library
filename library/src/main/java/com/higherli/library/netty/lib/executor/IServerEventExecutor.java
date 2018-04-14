package com.higherli.library.netty.lib.executor;

import com.higherli.library.netty.domain.User;

public interface IServerEventExecutor<T> {
	void executeEvent(User user, T eventData);
}
