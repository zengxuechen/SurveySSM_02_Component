package com.atguigu.survey.interceptors;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.e.AdminOperationForbiddenException;
import com.atguigu.survey.e.UserOperationForbiddenException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.utils.GlobalMessage;
import com.atguigu.survey.utils.GlobalNames;

/**
 * 项目中有很多操作必须登录后才能访问。登录拦截器就是为了将这样的资源保护起来。
 * @author zhangyu
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

	// 升级：2016年11月4日
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//如果是静态资源访问，则放行
		if(handler instanceof DefaultServletHttpRequestHandler){
			return true ;
		}
		
		//公共资源，不登录是可以被访问的
		Set<String> publicRes = new HashSet<String>();
		publicRes.add("/guest/user/toLoginUI");
		publicRes.add("/guest/user/toRegist");
		publicRes.add("/guest/user/login");
		publicRes.add("/guest/user/regist");
		publicRes.add("/guest/user/logout");
		
		publicRes.add("/manager/admin/toMainUI");
		publicRes.add("/manager/admin/toLoginUI");
		publicRes.add("/manager/admin/login");
		publicRes.add("/manager/admin/logout");
		
		String servletPath = request.getServletPath();
		//判断请求路径是否是属于公共资源，如果是则放行。
		if(publicRes.contains(servletPath)){
			return true ;
		}

		//获取session域，判断用户是否登录，如果不登录则抛出异常，如果登录则放行
		HttpSession session = request.getSession();
		
		if(servletPath.startsWith("/guest")){
			User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
	
			if(user==null){
				throw new UserOperationForbiddenException(GlobalMessage.USER_LOGIN_OPERATION_FORBIDDEN);
			}
		}
		
		if(servletPath.startsWith("/manager")){
			Admin admin = (Admin)session.getAttribute(GlobalNames.LOGIN_Admin);
	
			if(admin==null){
				throw new AdminOperationForbiddenException(GlobalMessage.ADMIN_LOGIN_OPERATION_FORBIDDEN);
			}
		}
		
		
		return true;
	}	
	
	/* 旧版：
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//如果是静态资源访问，则放行
		if(handler instanceof DefaultServletHttpRequestHandler){
			return true ;
		}
		
		//公共资源，不登录是可以被访问的
		Set<String> publicRes = new HashSet<>();
		publicRes.add("/guest/user/toLoginUI");
		publicRes.add("/guest/user/toRegist");
		publicRes.add("/guest/user/login");
		publicRes.add("/guest/user/regist");
		publicRes.add("/guest/user/logout");
		
		publicRes.add("/manager/admin/toMainUI");
		publicRes.add("/manager/admin/toLoginUI");
		publicRes.add("/manager/admin/login");
		publicRes.add("/manager/admin/logout");
		
		String servletPath = request.getServletPath();
		//判断请求路径是否是属于公共资源，如果是则放行。
		if(publicRes.contains(servletPath)){
			return true ;
		}

		//获取session域，判断用户是否登录，如果不登录则抛出异常，如果登录则放行
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);

		if(user==null){
			throw new LoginOperationForbiddenException(GlobalMessage.LOGIN_OPERATION_FORBIDDEN);
		}
		
		return true;
	}*/
	
}
