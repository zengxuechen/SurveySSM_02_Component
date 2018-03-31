package com.atguigu.survey.log.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.log.router.LogRouterDataSource;
import com.atguigu.survey.log.router.RouterKeyBinder;
import com.atguigu.survey.utils.DataProcessUtils;

/**
 * 监听IOC容器创建时，创建数据库的日志表
 * @author zhangyu
 */
public class LogListener implements ApplicationListener<ContextRefreshedEvent> {

	@Autowired
	private LogService logService ;
	
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		  
		ApplicationContext ioc = event.getApplicationContext();
		
		ApplicationContext parent = ioc.getParent();
		
		if(parent==null){
			//没有父容器的容器为Spring IOC容器
			String tableName = DataProcessUtils.generateTableName(0);
			
			//设置路由器数据源需要的key
			RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
			logService.createTable(tableName);
			
			//设置路由器数据源需要的key
			RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
			tableName = DataProcessUtils.generateTableName(1);
			logService.createTable(tableName);
			
			//设置路由器数据源需要的key
			RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
			tableName = DataProcessUtils.generateTableName(2);
			logService.createTable(tableName);
			
			//设置路由器数据源需要的key
			RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
			tableName = DataProcessUtils.generateTableName(3);
			logService.createTable(tableName);
			
		}else{
			//有父容器的容器为SpringMVC IOC容器
		}
		
	}

}
