package com.youzi.MyBlog.shiro;

import java.util.LinkedHashMap;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.DelegatingFilterProxy;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

/**
 * shiro配置类
 * 
 * @author Administrator
 *
 */
@Configuration
public class shiroConfiguration {

	/**
	 * shiro拦截地址
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
		System.out.println("shiroFilter----->");
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/login");
		bean.setSuccessUrl("/index");
		LinkedHashMap<String, String> liMap = new LinkedHashMap<>();
		liMap.put("/login", "anon");
		liMap.put("/logout", "anon");
		liMap.put("/static/js/*", "anon");
		liMap.put("/static/images/*", "anon");
		liMap.put("/user/**", "authc");
		bean.setFilterChainDefinitionMap(liMap);
		return bean;
	}

	@Bean(name = "sessionManager")
	public DefaultWebSessionManager defaultWebSessionManager() {
		System.out.println("---init---");
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setCacheManager(cacheManager());
		/*
		 * SimpleCookie simple=new SimpleCookie("youziCookie");
		 * sessionManager.setSessionIdCookie(simple);
		 */
		sessionManager.setGlobalSessionTimeout(1800000);// 超时时间
		sessionManager.setSessionValidationSchedulerEnabled(true);// 定时清除无效的session
		sessionManager.setDeleteInvalidSessions(true);// 删除无效的session
		sessionManager.setSessionDAO(sessionDao());
		return sessionManager;
	}

	@Bean  
	public ShiroDialect shiroDialect() {  
	    return new ShiroDialect();  
	}  
	
	@Bean(name = "sessionDao")
	public SessionDAO sessionDao() {
		System.out.println("sessionDao----->");
		YouZiSessionDao sessionDao = new YouZiSessionDao();
		sessionDao.setCacheManager(cacheManager());
		return sessionDao;
	}

	@Bean
	public YouZiCacheManager cacheManager() {
		System.out.println("cacheManager----->");
		return new YouZiCacheManager();
	}

	/**
	 * 
	 * @param authRealm
	 * @return
	 */
	@Bean
	public SecurityManager securityManager(MyRealm authRealm) {
		System.out.println("securityManager----->");
		DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
		manager.setRealm(authRealm);
		DefaultWebSessionManager defaultWebSessionManager = new DefaultWebSessionManager();
		manager.setSessionManager(defaultWebSessionManager());
		return manager;
	}

	// 配置自定义的权限登录器
	@Bean(name = "authRealm")
	public MyRealm authRealm(
			@Qualifier("credentialsMatcher") CredentialsMatcher matcher) {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx1");
		MyRealm authRealm = new MyRealm();
		authRealm.setCredentialsMatcher(matcher);
		return authRealm;
	}
	// 配置自定义的密码比较器
	@Bean(name = "credentialsMatcher")
	public CredentialsMatcher credentialsMatcher() {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx2");
		return new CredentialsMatcher();
	}
	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx3");
		return new LifecycleBeanPostProcessor();
	}
	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx4");
		DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
		creator.setProxyTargetClass(true);
		return creator;
	}
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager manager) {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx5");
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(manager);
		return advisor;
	}
	/**
	 * 执行顺序 初始化Bean的时候没找到shirofilter
	 * @return
	 */
	@Bean
	public FilterRegistrationBean delegatingFilterProxy() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
		DelegatingFilterProxy proxy = new DelegatingFilterProxy();
		proxy.setTargetFilterLifecycle(true);
		proxy.setTargetBeanName("shiroFilter");
		filterRegistrationBean.setFilter(proxy);
		return filterRegistrationBean;
	}
}
