package com.youzi.MyBlog.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.youzi.MyBlog.dao.ArticleDao;
import com.youzi.MyBlog.entity.Article;
import com.youzi.MyBlog.service.ArticleService;

@SuppressWarnings("check")
@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao ariticleDao;
	@Override
	public PageInfo findAll(Integer pageNum, Integer pagesize) {
		PageHelper.startPage(pageNum, pagesize);

		return new PageInfo(ariticleDao.findAll());
	}

	@Override
	public PageInfo findByTitle(String name,Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		return new PageInfo(ariticleDao.findByName(name));
	}

	@Override
	public Article findById(String id) {
		return ariticleDao.findById(id);
	}

	@Transactional(rollbackFor = {Exception.class})
	@Override
	public int updateLookNumById(String id) {
		return ariticleDao.updateLookNumById(id);
	}

	@Override
	public PageInfo findByTechnical(String Technical, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		return new PageInfo(ariticleDao.findByTechnical(Technical));
	}

	@Override
	public PageInfo findByCtyName(String ctyName, Integer pageNum,
			Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);

		return new PageInfo(ariticleDao.findByCtyName(ctyName));
	}

	@Override
	public List<Article> findHotArticle() {
		return ariticleDao.findHotArticle();
	}

}
