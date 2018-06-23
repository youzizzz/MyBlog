package com.youzi.MyBlog.shiro;

import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import com.youzi.MyBlog.dao.UserDao;
import com.youzi.MyBlog.entity.LoginLog;
import com.youzi.MyBlog.entity.Permission;
import com.youzi.MyBlog.entity.Role;
import com.youzi.MyBlog.entity.User;
import com.youzi.MyBlog.service.LoginLogService;
import com.youzi.MyBlog.util.JedisUtils;

@Configuration
public class MyRealm extends AuthorizingRealm {

	@Autowired
	private UserDao userDao;
	

	// 授权
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		User user=(User)principals.fromRealm(this.getClass().getName()).iterator().next();
		
		
		SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
		Set<String> roles=user.getRoles().parallelStream().map(Role::getRname).collect(Collectors.toSet());
		info.setRoles(roles);
		Set<String> permissions=null;
		if(null!=roles&&roles.size()>0) {
			user.getRoles().parallelStream().forEach(role->{
				permissions.add(role.getModules().parallelStream().map(Permission::getPname).toString());
			});
		}
		info.addStringPermissions(permissions);
		return info;
	}

	// 登录
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken upToke = (UsernamePasswordToken) token;
		String username = upToke.getUsername();
		User user = userDao.login(username);
		JedisUtils.set("loginUserId", user!=null?user.getId():"", 1000*60);
		return new SimpleAuthenticationInfo(username==null?"":username,
				(user==null?"":user.getPassword() == "" ? "" : user.getPassword()),
				this.getClass().getName());
	}

}
