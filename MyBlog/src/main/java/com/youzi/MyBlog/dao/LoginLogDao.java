package com.youzi.MyBlog.dao;

import org.apache.ibatis.annotations.Mapper;

import com.youzi.MyBlog.entity.LoginLog;

@Mapper
public interface LoginLogDao {

	public int addLog(LoginLog loginLog);
}
