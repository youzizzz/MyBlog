package com.youzi.MyBlog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.youzi.MyBlog.service.ArticleService;

@Configuration
@EnableCaching
public class RedisConfig {

	@Autowired
	private ArticleService articleService;
	/**
	 * 创建redis管理器
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RedisCacheManager redisCacheManager(
			RedisConnectionFactory connectionFactory) {
		return RedisCacheManager.create(connectionFactory);
	}
	
	/** 接收消息的方法 */
	public void receiveMessage(String message) {
		articleService.updateLookNumById(message.split(",")[1]);
	}
	

	@Bean
public	RedisMessageListenerContainer container(
			RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		// 订阅了一个叫chat 的通道
		container.addMessageListener(listenerAdapter, new PatternTopic("chat"));
		// 这个container 可以添加多个 messageListener
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(RedisConfig receiver) {
	//这个地方 是给messageListenerAdapter 传入一个消息接受的处理器，利用反射的方法调用“receiveMessage”
	//也有好几个重载方法，这边默认调用处理器的方法 叫handleMessage 可以自己到源码里面看
	return new MessageListenerAdapter(receiver, "receiveMessage");
	}
	
	/**
	 * Redis操作帮助模板
	 * 
	 * @param connectionFactory
	 * @return
	 */
	@Bean
	public RedisTemplate<String, String> redisTemplate(
			RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
	
	@Bean
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory connectionFactory) {
		StringRedisTemplate redisTemplate = new StringRedisTemplate();
		redisTemplate.setConnectionFactory(connectionFactory);
		return redisTemplate;
	}
}
