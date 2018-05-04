package com.youzi.MyBlog.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

import com.youzi.MyBlog.entity.RedisCache;

@Configuration
public class YouZiCacheManager implements CacheManager {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	public <K, V> Cache<K, V> getCache(String arg0) throws CacheException {
		return new RedisCache<>(120, redisTemplate);
	}

	
}
