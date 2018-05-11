package com.youzi.MyBlog.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youzi.MyBlog.dao.TechnicalDao;
import com.youzi.MyBlog.entity.Article;
import com.youzi.MyBlog.entity.Technical;
import com.youzi.MyBlog.service.ArticleService;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private TechnicalDao technicalDao;

	@Autowired
	private RedisTemplate redisTemplate;

	@PostMapping("/blog/createBlog")
	@ResponseBody
	public String saveArticle(Article article) {
		article.setContent(article.getContent().replace("E:/", "").trim());
		article.setCreateTime(new Date());
		article.setId(UUID.randomUUID().toString());
		article.setLooknum(0);
		article.setDescription(article.getDescription().length() < 59
				? article.getDescription()
				: article.getDescription().substring(0, 60));
		articleService.insert(article);
		return "success";
	}

	/**
	 * 根据博客ID显示博客
	 * 
	 * @param model
	 *            传参MODEL
	 * @param id
	 *            ID
	 * @return
	 */
	@GetMapping("/artcleinfo/{id}")
	public ModelAndView findArticleById(ModelAndView model,
			@PathVariable("id") String id) {
		Article art = articleService.findById(id);
		if (null == art) {
			model.setViewName("404");
			return model;
		}
		art.motifyContent();
		art.setContent(art.getContent().replace(art.getTitle(), "").trim());
		redisTemplate.convertAndSend("chat", "," + id);
		List<Technical> list = technicalDao.findAll();
		model.addObject("tcs", null != list ? list : null);
		model.addObject("article", art);
		model.addObject("pageTitle", art.getTitle());
		model.setViewName("ArticleInfo");
		return model;
	}

}
