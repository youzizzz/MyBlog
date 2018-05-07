package com.youzi.MyBlog.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.youzi.MyBlog.entity.Technical;

@Mapper
public interface TechnicalDao {

	public List<Technical> findAll();
}
