package com.youzi.MyBlog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.youzi.MyBlog.entity.Article;

@Mapper
public interface ArticleDao {
	
	public List<Article> findAll();
	public Article findById(String id);
	public List<Article> findByName(String name);
	public List<Article> findByTechnical(String Technical);
	public List<Article> findByCtyName(String ctyName);
	public int updateLookNumById(String id);
	public List<Article> findHotArticle();
	
}
