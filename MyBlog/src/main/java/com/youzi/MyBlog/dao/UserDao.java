package com.youzi.MyBlog.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.youzi.MyBlog.entity.User;

@Mapper
public interface UserDao {

	public User login(@Param("username")String username);
	
	public int findUserByName(@Param("username")String username);
	
	public int saveUser(User user);
}
