package com.youzi.MyBlog.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import io.netty.util.internal.StringUtil;

public class RedisCache<K, V> implements Cache<K, V>,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2363434868004466954L;

	private long expireTime = 120;// 缓存的超时时间，单位为s

	private RedisTemplate<K, V> redisTemplate;// 通过构造方法注入该对象

	@Override
	public void clear() throws CacheException {
	}

	@Override
	public V get(K arg0) throws CacheException {
		return redisTemplate.opsForValue().get(arg0);
	}

	@Override
	public Set<K> keys() {
		
		return null;
	}

	@Override
	public V put(K arg0, V arg1) throws CacheException {
		redisTemplate.opsForValue().set(arg0, arg1);
		return arg1;
	}

	@Override
	public V remove(K arg0) throws CacheException {
		  V v = redisTemplate.opsForValue().get(arg0);  
	      redisTemplate.opsForValue().getOperations().delete(arg0);  
	      return v;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<V> values() {
		// TODO Auto-generated method stub
		return null;
	}

	public RedisCache(long expireTime, RedisTemplate<K, V> redisTemplate) {
		super();
		this.expireTime = expireTime;
		this.redisTemplate = redisTemplate;
	}

}
