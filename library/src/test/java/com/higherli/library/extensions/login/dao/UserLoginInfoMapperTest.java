package com.higherli.library.extensions.login.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.higherli.library.base.BaseSpringAndJunit4Test;
import com.higherli.library.base.Constanct;

public class UserLoginInfoMapperTest extends BaseSpringAndJunit4Test {
	@Autowired
	private UserLoginInfoMapper mapper;

	@Test
	public void testInsert() {
		UserLoginInfo info = new UserLoginInfo(Constanct.DEFALUT_USERID, "Name", "1234");
		mapper.insert(info);
	}

	@After
	public void after() {
		deleteByUserId(Constanct.DEFALUT_USERID);
	}

	@Before
	public void before() {
		deleteByUserId(Constanct.DEFALUT_USERID);
	}

	private void deleteByUserId(int userId) {
		mapper.deleteByUserId(userId);
	}

}
