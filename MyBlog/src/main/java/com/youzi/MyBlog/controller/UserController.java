package com.youzi.MyBlog.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youzi.MyBlog.config.MyExceptionHandler;
import com.youzi.MyBlog.dao.TechnicalDao;
import com.youzi.MyBlog.entity.User;

@Controller
public class UserController {

	@Autowired
	private TechnicalDao technicalDao;
	@RequestMapping("/login")
	@ResponseBody
	public String login(User user, HttpServletRequest request,
			HttpServletResponse response) {
		UsernamePasswordToken upToken = new UsernamePasswordToken(
				user.getUserName(), user.getPassword());
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(upToken);
			return "success";
		} catch (Exception e) {
			if(e instanceof IncorrectCredentialsException) {
				return "error";
			}
			e.printStackTrace();
			return "error";
		}
	}

	@GetMapping("/home")
	public ModelAndView home(ModelAndView model) {
		model.addObject("tcs",null!=technicalDao.findAll()?technicalDao.findAll():null);
		model.setViewName("index");
		return model;
	}
	
	@GetMapping("/index")
	public String showPage() {
		return "bdueditor";
	}

}
