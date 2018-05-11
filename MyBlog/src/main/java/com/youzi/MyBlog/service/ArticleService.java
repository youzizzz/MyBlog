package com.youzi.MyBlog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.youzi.MyBlog.entity.Article;

@Service
public interface ArticleService {
	public PageInfo findAll(Integer pageNum,Integer pageSize);
	public Article findById(String id);
	public List<Article> findByName(String name);
	public List<Article> findByTechnical(String Technical);
	public int insert(Article article);
	public int updateLookNumById(String id);
}
