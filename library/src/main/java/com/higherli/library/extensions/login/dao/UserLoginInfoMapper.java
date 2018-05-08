package com.higherli.library.extensions.login.dao;

public interface UserLoginInfoMapper {
	public void insert();

	public void selectByUserId(int userId);
}
