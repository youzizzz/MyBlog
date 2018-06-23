package com.youzi.MyBlog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import com.youzi.MyBlog.dao.TechnicalDao;
import com.youzi.MyBlog.entity.Article;
import com.youzi.MyBlog.entity.Technical;
import com.youzi.MyBlog.service.ArticleService;
import com.youzi.MyBlog.util.JedisUtils;

@SuppressWarnings("all")
@Controller
public class HomeController {
	@Autowired
	private TechnicalDao technicalDao;
	@Autowired
	private ArticleService articleService;

	@GetMapping("/home/{page}")
	public ModelAndView home(ModelAndView model,
			@PathVariable(name = "page", required = false) Integer page) {
		publicContext(model);
		model.addObject("artcles",
				articleService.findAll(page == null ? 1 : page, 5));
		model.setViewName("index");
		return model;
	}
	
	@GetMapping("")
	public ModelAndView index(ModelAndView model) {
		publicContext(model);
		model.addObject("artcles",
				articleService.findAll(1, 5));
		model.setViewName("index");
		return model;
	}
	
	
	@GetMapping("/about")
	public ModelAndView about(ModelAndView model) {
		publicContext(model);
		model.setViewName("about");
		return model;
	}
	@GetMapping("/message")
	public ModelAndView message(ModelAndView model) {
		publicContext(model);
		model.setViewName("message");
		return model;
	}
	
	public ModelAndView publicContext(ModelAndView model) {
		List<Technical> list = (List<Technical>) JedisUtils.getObject("tcs");
		if (list == null) {
			list = technicalDao.findAll();
			JedisUtils.setObject("tcs", list, 1000 * 60);
		}

		List<Article> hotArt = (List<Article>) JedisUtils
				.getObject("hotArticle");
		if (hotArt == null) {
			hotArt = articleService.findHotArticle();
			JedisUtils.setObject("hotArt", hotArt, 1000 * 60);
		}
		model.addObject("tcs", null != list ? list : null);
		model.addObject("hotArt", hotArt != null ? hotArt : null);
		return model;
	}
}
