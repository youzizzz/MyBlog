package com.youzi.MyBlog.controller;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youzi.MyBlog.dao.TechnicalDao;
import com.youzi.MyBlog.entity.Article;
import com.youzi.MyBlog.entity.Technical;
import com.youzi.MyBlog.service.ArticleService;
import com.youzi.MyBlog.util.JedisUtils;

@Controller
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@Autowired
	private TechnicalDao technicalDao;

	@Autowired
	private RedisTemplate redisTemplate;

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
		JedisUtils.publish("chat",","+art.getId());
		getPublicContext(model);
		model.addObject("article", art);
		model.addObject("pageTitle", art.getTitle());
		model.setViewName("ArticleInfo");
		return model;
	}

	/**
	 * 根据标签查询
	 * 
	 * @param model
	 * @param page
	 * @param ctyName
	 * @return
	 */
	@GetMapping("/tag/{thName}/{page}")
	public ModelAndView searchAtrByTh(ModelAndView model,
			@PathVariable(name = "page", required = false) Integer page,
			@PathVariable("thName") String thName) {
		getPublicContext(model);
		model.addObject("tag", thName);
		model.addObject("artcles", articleService.findByTechnical(thName,
				page == null ? 1 : page, 5));
		model.setViewName("index");
		return model;
	}

	/**
	 * 根据顶部菜单查询
	 * 
	 * @param model
	 * @param page
	 * @param ctyName
	 * @return
	 */
	@GetMapping("/sort/{ctyName}/{page}")
	public ModelAndView searchcty(ModelAndView model,
			@PathVariable(name = "page", required = false) Integer page,
			@PathVariable("ctyName") String ctyName) {
		getPublicContext(model);
		model.addObject("cty", ctyName);
		model.addObject("artcles", articleService.findByCtyName(ctyName,
				page == null ? 1 : page, 5));
		model.setViewName("index");
		return model;
	}

	/**
	 * 根据关键字查询
	 * 
	 * @param model
	 * @param page
	 * @param ctyName
	 * @return
	 */
	@GetMapping("/serachTitle/{Name}/{page}")
	public ModelAndView searchByTitle(ModelAndView model,
			@PathVariable(name = "page", required = false) Integer page,
			@PathVariable("Name") String Name) {
		getPublicContext(model);
		model.addObject("title",Name);
		model.addObject("artcles",
				articleService.findByTitle(Name, page == null ? 1 : page, 5));
		model.setViewName("index");
		return model;
	}
	
	
	public ModelAndView getPublicContext(ModelAndView model) {
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
