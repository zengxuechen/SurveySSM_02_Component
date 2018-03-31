package com.atguigu.survey.log.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.log.router.LogRouterDataSource;
import com.atguigu.survey.log.router.RouterKeyBinder;
import com.atguigu.survey.utils.DataProcessUtils;

/**
 * 根据定时时间，每月15日创建后三个月的表结构。
 * @author zhangyu
 */
public class LogQuartzJobBean extends QuartzJobBean {

	private LogService logService ;//不能直接使用依赖注入，特殊设置。
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		String tableName = DataProcessUtils.generateTableName(1);
		
		//设置路由器数据源需要的key
		RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
		logService.createTable(tableName);
		
		tableName = DataProcessUtils.generateTableName(2);
		
		//设置路由器数据源需要的key
		RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
		logService.createTable(tableName);
		
		tableName = DataProcessUtils.generateTableName(3);
		
		//设置路由器数据源需要的key
		RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
		logService.createTable(tableName);
		
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
