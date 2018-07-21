package com.higherli.library.extensions.login;

import org.springframework.stereotype.Service;

import com.higherli.library.extensions.util.SimpleResult;

@Service
public class LoginService {
	private LoginService() {
	}
	
	public void userLogin(){
		
	}

	public SimpleResult<Object> registered(String userName, String password) {
		// TODO
		return null;
	}
	
	@SuppressWarnings("unused")
	private int createNewUserId() {
		// TODO
		return 1;
	}

	public SimpleResult<Object> login(String userName, String password) {
		// TODO
		return null;
	}

}
