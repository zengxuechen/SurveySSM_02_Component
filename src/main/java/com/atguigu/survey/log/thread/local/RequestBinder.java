package com.atguigu.survey.log.thread.local;

import javax.servlet.http.HttpServletRequest;

public class RequestBinder {
	
	private static ThreadLocal<HttpServletRequest> local = new ThreadLocal<HttpServletRequest>();
	
	public static void bindRequset(HttpServletRequest request){
		local.set(request);		
	}
	
	public static HttpServletRequest getRequset(){
		return local.get();
	}
	
	public static void removeRequest(){
		local.remove();
	}
	
}
