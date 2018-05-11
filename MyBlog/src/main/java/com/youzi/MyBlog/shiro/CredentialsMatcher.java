package com.youzi.MyBlog.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class CredentialsMatcher extends SimpleCredentialsMatcher {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		String count=redisTemplate.opsForValue().get(username);
		int counts=0;
		if(null==count||"".equals(count)) {
			redisTemplate.opsForValue().set(username,"0");
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
			redisTemplate.delete(username);
		}else {
			counts++;
			redisTemplate.opsForValue().set(username, counts+"");
		}
		// 进行密码的比对
		return this.equals(inPassword, dbPassword);
	}

	public CredentialsMatcher() {
		super();
		// TODO Auto-generated constructor stub
	}

}
