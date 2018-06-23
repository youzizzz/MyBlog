package com.youzi.MyBlog.shiro;

import java.util.Date;
import java.util.UUID;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.youzi.MyBlog.entity.LoginLog;
import com.youzi.MyBlog.entity.User;
import com.youzi.MyBlog.service.LoginLogService;
import com.youzi.MyBlog.util.JedisUtils;

@Configuration
public class CredentialsMatcher extends SimpleCredentialsMatcher {
	
	@Autowired
	private LoginLogService loginLogService;
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		String count=JedisUtils.get(username);
		int counts=0;
		if(null==count||"".equals(count)) {
			JedisUtils.set(username,"0",1000*1000);
		}else {
			counts=Integer.parseInt(count);
		}
		
		UsernamePasswordToken utoken = (UsernamePasswordToken) token;
		// 获得用户输入的密码:(可以采用加盐(salt)的方式去检验)
		String inPassword = new String(utoken.getPassword());
		// inPassword = MD5Encoder.encode(inPassword.getBytes());
		// 获得数据库中的密码
		String dbPassword = (String) info.getCredentials();
		Boolean bool=super.doCredentialsMatch(utoken, info);
		if(counts>=5) {
			  throw new ExcessiveAttemptsException();  
		}
		if(bool) {
			JedisUtils.del(username);
			//记录登录次数
			LoginLog log=new LoginLog();
			log.setId(UUID.randomUUID().toString());
			log.setLoginDate(new Date());
			log.setUserId(JedisUtils.get("loginUserId"));
			log.setUserName(username);
			loginLogService.addLog(log);
			
		}else {
			counts++;
			JedisUtils.set(username, counts+"",1000*1000);
		}
		JedisUtils.del("loginUserId");
		// 进行密码的比对
		return bool;
	}

	public CredentialsMatcher() {
		super();
		// TODO Auto-generated constructor stub
	}

}
