package com.youzi.MyBlog.config;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;

@Configuration
public class MyExceptionHandler implements HandlerExceptionResolver {  
	
	@Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception ex) {  
        ModelAndView mv = new ModelAndView();  
        FastJsonJsonView view = new FastJsonJsonView();  
        Map<String, Object> attributes = new HashMap<String, Object>();  
        if (ex instanceof UnauthenticatedException) {  
            attributes.put("code", "1000001");  
            attributes.put("msg", "token错误");  
        } else if (ex instanceof UnauthorizedException) {  
            attributes.put("code", "1000002");  
            attributes.put("msg", "用户无权限");  
            mv.setViewName("/nopre");
        } else if(ex instanceof IncorrectCredentialsException){
        	attributes.put("code", "1000003");  
            attributes.put("msg", "密码错误");  
        }else {
            attributes.put("code", "1000004");  
            attributes.put("msg", ex.getMessage());  
        }
        view.setAttributesMap(attributes);  
        mv.setView(view);  
        return mv;  
    }  
    
} 
