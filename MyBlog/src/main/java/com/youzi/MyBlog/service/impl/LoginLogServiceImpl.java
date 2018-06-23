package com.youzi.MyBlog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.youzi.MyBlog.dao.LoginLogDao;
import com.youzi.MyBlog.entity.LoginLog;
import com.youzi.MyBlog.service.LoginLogService;

@Component
public class LoginLogServiceImpl implements LoginLogService {

	@Autowired
	private LoginLogDao loginlogDao;
	
	@Transactional(rollbackFor= {Exception.class})
	@Override
	public int addLog(LoginLog loginLog) {
		return loginlogDao.addLog(loginLog);
	}

}
