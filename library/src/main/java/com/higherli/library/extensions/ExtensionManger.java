package com.higherli.library.extensions;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.higherli.library.log.LoggerUtil;
import com.higherli.library.netty.message.requset.RequestMessage;

import io.netty.channel.Channel;

public class ExtensionManger {

	public static final ExtensionManger INSTANCE = new ExtensionManger();

	private ExtensionManger() {
		registeredExtension(1, new BaseDemoExtension());
	}

	/**
	 * key:action
	 */
	private Map<Integer, IExtension> allExtension = new HashMap<>();

	/**
	 * key1:action; key2:cmdIndex value:handleMethod
	 */
	private Map<Integer, Map<Integer, Method>> allMethods = new HashMap<>();

	public void registeredExtension(int extensionAction, IExtension extension) {
		if (allExtension.get(extensionAction) != null) {
			LoggerUtil.errorf("<<ExtensionManger>> action[%s] is repeated", extensionAction);
			throw new IllegalArgumentException("Extension's action is repeated");
		}
		allExtension.put(extensionAction, extension);
		Map<Integer, Method> extensionCmdCommandMethods = new HashMap<>();
		allMethods.put(extensionAction, extensionCmdCommandMethods);
		Method[] allMethods = extension.getClass().getDeclaredMethods();
		for (Method method : allMethods) {
			CmdCommand cmdCommand = method.getAnnotation(CmdCommand.class);
			if (cmdCommand == null)
				continue;
			extensionCmdCommandMethods.put(cmdCommand.cmdIndex(), method);
		}
	}

	public void handleRequest(RequestMessage message, Channel channel) {
		Map<Integer, Method> methods = allMethods.get(message.getAction());
		if (methods == null) {
			LoggerUtil.errorf("<<ExtensionManger>> don't exist the action[%s]", message.getAction());
			return;
		}
		Method methd = methods.get(message.getCmdIndex());
		if (methd == null) {
			LoggerUtil.errorf("<<ExtensionManger>> can't find the cmd[%s] in action[%s]", message.getCmdIndex(),
					message.getAction());
			return;
		}
		try {
			methd.invoke(allExtension.get(message.getAction()), message, channel);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
