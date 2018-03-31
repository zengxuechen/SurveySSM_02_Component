package com.atguigu.survey.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.DataProcessUtils;

/**
 * 用于捕获每一个请求的 servletPath 以保持资源对象
 * 开发过程中临时使用的拦截器，在项目不增加新的功能时将其关闭。
 * @author zhangyu
 *
 */
public class ResInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private ResService resService ;
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		//1.静态资源直接放行
		if(handler instanceof DefaultServletHttpRequestHandler){
			return true ;
		}
		
		//2.获取请求路径的servletPath
		String servletPath = request.getServletPath();
		
		//3.检查这个servletPath是否被保存过	
		//有一首歌来形容这个方法“一剪梅”
		servletPath = DataProcessUtils.cutPath(servletPath);
		
		//4.检查servletPath在数据库中是否存在
		//拦截器应该依赖于业务层，而不能是DAO层。真正干活的是DAO,但是，露脸的是Service
		boolean flag = resService.checkResExist(servletPath);
		
		//5.如果存在就放行,不需要再保存资源了
		if(flag){
			return true ;
		}	
		
		int systemMaxCode = 1073741824 ;
		
		//6.如果没有保存过
		//①声明两个变量，初始值是权限码和权限位的默认值
		Integer resCode = 1 ;
		Integer resPos = 0 ;
		
		//②获取系统中最大的权限位的值
		Integer maxPos = resService.getMaxPos() ;
		
		//③获取系统中最大的权限位的权限码的值
		Integer maxCode = (maxPos!=null)? resService.getMaxCode(maxPos) : null;
		
		//④检查maxCode是否达到了最大的权限码（1<<30 = 1073741824）
		if(maxCode!=null){
			if(maxCode == systemMaxCode){
				//如果已经达到了最大，那么，权限位+1，权限码为1
				resPos = maxPos + 1; 
				resCode =  1 ;
			}else{
				//⑤如果maxCode没有达到最大值
				
				//resPos设置为maxPos
				resPos = maxPos ;
				
				//resCode设置为maxCode左移一位			
				resCode = maxCode << 1 ;
			}
		}
		//⑤创建资源对象
		Res res = new Res(null, servletPath, false, resCode, resPos);
		
		//⑥保存资源对象
		resService.saveEntity(res);
		
		return true;		
	}
	
}
