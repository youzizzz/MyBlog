package com.youzi.MyBlog.controller;

import java.util.Date;
import java.util.List;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youzi.MyBlog.dao.TechnicalDao;
import com.youzi.MyBlog.entity.Technical;
import com.youzi.MyBlog.entity.User;
import com.youzi.MyBlog.service.ArticleService;
import com.youzi.MyBlog.service.UserService;

@Controller
public class UserController {

	@Autowired
	private TechnicalDao technicalDao;

	@Autowired
	private UserService userService;

	@Autowired
	private ArticleService articleService;
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
			if(e instanceof ExcessiveAttemptsException) {
				return "countError";
			}
			if (e instanceof IncorrectCredentialsException) {
				return "error";
			}
			return "error";
		}
	}

	@GetMapping("/home/{page}")
	public ModelAndView home(ModelAndView model,
			@PathVariable("page") Integer page) {
		List<Technical> list = technicalDao.findAll();
		model.addObject("tcs", null != list ? list : null);
		model.addObject("artcles",
				articleService.findAll(page == null ? 1 : page, 5));
		model.setViewName("index");
		return model;
	}

	@GetMapping("/index")
	public String showPage() {
		return "bdueditor";
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
