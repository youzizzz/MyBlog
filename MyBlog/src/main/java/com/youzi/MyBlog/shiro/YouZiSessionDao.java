package com.youzi.MyBlog.shiro;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.youzi.MyBlog.util.Servlets;


@Configuration
public class YouZiSessionDao extends CachingSessionDAO {

	@Autowired
	private RedisTemplate redisTemplate;
	
	@Override
	protected void doUpdate(Session session) {
		ServletRequestAttributes seRequestAttributes=(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();;
		HttpServletRequest request=seRequestAttributes.getRequest();
		System.out.println("------requestPath------"+request.getServletPath());
		//静态文件放过
		if(Servlets.isStaticFile(request.getServletPath())) {
				System.out.println(">>>>this is StaticFile"+request.getServletPath());
			return;
		}
		//视图文件放过
		if(request.getServletPath().startsWith("classpath:templates/")) {
			return ;
		}
		//手动更新放过
		String updateSession=request.getParameter("updateSession");
		if("true".equals(updateSession)) {
			return;
		}
		super.update(session);
	}

	@Override
	protected void doDelete(Session session) {
		System.out.println("DeleteSession"+session);
	}

	@Override
	protected Serializable doCreate(Session session) {
		// TODO Auto-generated method stub
		System.out.println("doCreate--->>>>"+session);
		Serializable sessionId=this.generateSessionId(session);
		assignSessionId(session, sessionId);
		return sessionId;
	}

	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session s=readSession(sessionId);
		System.out.println("readSession----->"+s+"\r\r this is SessionId-->"+sessionId);
		return s;
	}

}
