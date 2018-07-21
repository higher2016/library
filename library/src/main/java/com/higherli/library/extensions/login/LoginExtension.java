package com.higherli.library.extensions.login;

import org.springframework.beans.factory.annotation.Autowired;

import com.higherli.library.extensions.CmdCommand;
import com.higherli.library.extensions.IExtension;
import com.higherli.library.netty.message.requset.RequestMessage;

import io.netty.channel.Channel;

public class LoginExtension implements IExtension {
	@Autowired
	private LoginService loginService;

	@CmdCommand(cmdIndex = 1)
	public void registered(RequestMessage message, Channel channel) {
		String userName = message.getString("userName");
		String password = message.getString("password");
		loginService.registered(userName, password);
	}
}
