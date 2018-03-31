package com.atguigu.survey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.e.AdminOperationForbiddenException;
import com.atguigu.survey.e.HasNoAuthorityException;
import com.atguigu.survey.e.UserOperationForbiddenException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.DataProcessUtils;
import com.atguigu.survey.utils.GlobalMessage;
import com.atguigu.survey.utils.GlobalNames;

/**
 * 权限拦截器[取代原有的登录拦截器]
 * @author zhangyu
 */
public class AuthorityInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ResService resService;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		// 1.将静态资源放过
		if(handler instanceof DefaultServletHttpRequestHandler){
			return true ;
		}		
		
		// 2.获取Session对象
		HttpSession session = request.getSession();
		
		// 3.获取ServletPath
		String servletPath = request.getServletPath();
		
		// 4.将ServletPath中的多余部分剪掉
		servletPath = DataProcessUtils.cutPath(servletPath);
		
		// 5.根据ServletPath获取对应的Res对象
		Res res = resService.getResByServletpath(servletPath);
		
		// 6.检查是否是公共资源
		if(res.getPublicRes()){
			// 7.如果是公共资源则放行
			return true ;
		}
		
		// 8.如果当前请求的目标地址是User部分的
		if(servletPath.startsWith("/guest")){
			// 9.检查User是否登录
			User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
			
			// 10.如果没有登录则抛出自定义异常：UserLoginNeededException
			if(user==null){
				throw new UserOperationForbiddenException(GlobalMessage.USER_LOGIN_OPERATION_FORBIDDEN);
			}
			
			// 11.已登录则检查权限
			String codeArrStr = user.getCodeArrStr();
			
			boolean checkedAuthority = DataProcessUtils.checkedAuthority(codeArrStr, res);
			
			// 12.有权限则放行
			if(checkedAuthority){
				return true ;
			}else{
				// 13.没有权限则抛出自定义异常：HasNoRightException
				throw new HasNoAuthorityException(GlobalMessage.HAS_NO_AUTHORITY);
			}
		}
		
		// 14.如果当前请求的目标地址是Admin部分的
		if(servletPath.startsWith("/manager")){
			
			// 15.检查Admin是否登录
			Admin admin = (Admin)session.getAttribute(GlobalNames.LOGIN_Admin);
			if(admin == null ){
				// 16.如果没有登录则抛出自定义异常：AdminLoginNeededException
				throw new AdminOperationForbiddenException(GlobalMessage.ADMIN_LOGIN_OPERATION_FORBIDDEN);
			}
			
			// 17.如果已登录则检查是否是超级管理员
			if("SuperAdmin".endsWith(admin.getAdminName())){
				return true ;
			}
			// 18.如果不是超级管理员，则检查是否具备访问目标资源的权限
			boolean checkedAuthority = DataProcessUtils.checkedAuthority(admin.getCodeArrStr(), res);
			
			// 19.有权限则放行
			if(checkedAuthority){
				return true ;
			}else{
				// 20.没有权限则抛出自定义异常：HasNoRightException
				throw new HasNoAuthorityException(GlobalMessage.HAS_NO_AUTHORITY);
			}
		}
		return false;
	}
	
}
