package com.youzi.MyBlog.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {

	/**
	 * 创建redis管理器
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.create(connectionFactory);
	}
	
	/**
	 * Redis操作帮助模板
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String,String> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> redisTemplate=new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
}
