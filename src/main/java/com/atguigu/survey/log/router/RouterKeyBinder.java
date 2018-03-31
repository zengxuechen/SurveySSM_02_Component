package com.atguigu.survey.log.router;

/**
 * 管理路由器数据源所使用的 key
 * @author zhangyu
 *
 */
public class RouterKeyBinder {

	private static ThreadLocal<String> local = new ThreadLocal<String>();
	
	public static void bindKey(String key){
		local.set(key);		
	}

	public static void removeKey(){
		local.remove();
	}
	
	public static String getKey(){
		return local.get();
	}
	
}
