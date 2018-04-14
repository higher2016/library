package com.higherli.library.netty.lib;

import com.higherli.library.netty.domain.User;

public interface IServerEventHandler {
	void handleEvent(User user, Object event);
}
