package com.higherli.library.extensions.login.dao;

import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginInfoMapper {
	public void insert(UserLoginInfo userLoginInfo);

	public UserLoginInfo selectByUserId(int userId);
	
	public void deleteByUserId(int userId);
}
