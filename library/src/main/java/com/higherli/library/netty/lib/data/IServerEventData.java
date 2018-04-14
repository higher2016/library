package com.higherli.library.netty.lib.data;

import com.higherli.library.netty.lib.executor.IServerEventExecutor;

public interface IServerEventData {
	IServerEventExecutor<? extends IServerEventData> getMyExecutor();
}
