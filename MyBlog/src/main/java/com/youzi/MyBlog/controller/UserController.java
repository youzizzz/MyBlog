package com.youzi.MyBlog.controller;

import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youzi.MyBlog.entity.User;
import com.youzi.MyBlog.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	@ResponseBody
	public String login(User user, HttpServletRequest request,
			HttpServletResponse response) {
		UsernamePasswordToken upToken = new UsernamePasswordToken(
				user.getUserName(), user.getPassword());
		Subject subject = SecurityUtils.getSubject();
		try {
			if (null == subject.getPrincipal()) {
				subject.login(upToken);
			}
			return "success";
		} catch (Exception e) {
			if (e instanceof ExcessiveAttemptsException) {
				return "countError";
			}
			if (e instanceof IncorrectCredentialsException) {
				return "error";
			}
			return "error";
		}
	}

	@GetMapping("/register/validate")
	@ResponseBody
	public String validateUsername(String username) {
		int result = userService.findUserByName(username);
		return result != 0 ? "success" : "error";
	}

	@PostMapping("/register/save")
	@ResponseBody
	public String register(User user) {
		user.setId(UUID.randomUUID().toString());
		user.setCreateDate(new Date());
		user.setLastUpdateDate(new Date());
		int result = userService.SaveUser(user);
		return result != 0 ? "success" : "error";
	}

}
