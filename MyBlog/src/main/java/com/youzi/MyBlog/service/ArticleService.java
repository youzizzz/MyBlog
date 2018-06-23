package com.youzi.MyBlog.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.youzi.MyBlog.entity.Article;

@Service
public interface ArticleService {
	public PageInfo findAll(Integer pageNum,Integer pageSize);
	public Article findById(String id);
	public PageInfo findByTitle(String name,Integer pageNum,Integer pageSize);
	public PageInfo findByTechnical(String Technical,Integer pageNum,Integer pageSize);
	public PageInfo findByCtyName(String ctyName,Integer pageNum,Integer pageSize);
	public int updateLookNumById(String id);
	public List<Article> findHotArticle();
}
