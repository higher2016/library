package com.higherli.library.netty.config;

public class Config {
	public static final int NETTY_BOOS_THREAD = 1; //
	public static final int NETTY_WORKER_THREAD = 10; //
	public static final int CHECK_UNLOGIN_INTERVAL = 10; //
	public static final int MAX_MSG_BYTE_LEN = 4096; // 消息内容最大长度
	public static final int INET_PORT = 12333; // 这个端口号是非常有意义的——服务器端口号
	public static final String INET_IP = "127.0.0.1";
}
