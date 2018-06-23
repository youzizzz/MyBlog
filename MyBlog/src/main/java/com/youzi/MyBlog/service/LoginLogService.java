package com.youzi.MyBlog.service;

import org.springframework.stereotype.Service;

import com.youzi.MyBlog.entity.LoginLog;

@Service
public interface LoginLogService {
	public int addLog(LoginLog loginLog);
}
