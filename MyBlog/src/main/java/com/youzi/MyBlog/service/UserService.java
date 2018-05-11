package com.youzi.MyBlog.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.youzi.MyBlog.entity.User;

@Service
public interface UserService {

	/**
	 * 新增一个用户
	 * @param user
	 * @return
	 */
	public int SaveUser(User user);
	
	/**
	 * 根据用户名查询用户
	 * @param username
	 * @return
	 */
	public int findUserByName(@Param("username")String username);
}
