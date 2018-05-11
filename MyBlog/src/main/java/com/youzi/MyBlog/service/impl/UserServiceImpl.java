package com.youzi.MyBlog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.youzi.MyBlog.dao.UserDao;
import com.youzi.MyBlog.entity.User;
import com.youzi.MyBlog.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public int SaveUser(User user) {
		return userDao.saveUser(user);
	}

	@Override
	public int findUserByName(String username) {
		return userDao.findUserByName(username);
	}

}
