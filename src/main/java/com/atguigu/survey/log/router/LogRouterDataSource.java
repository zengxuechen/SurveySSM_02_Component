package com.atguigu.survey.log.router;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 自定义路由数据源
 * @author zhangyu
 */
public class LogRouterDataSource extends AbstractRoutingDataSource {

	
	public static String DATASOURCE_MAIN = "DATASOURCE_MAIN" ;
	public static String DATASOURCE_LOG = "DATASOURCE_LOG" ;
	
	/**
	 * ①作用：返回的值决定路由器数据源采用哪一个数据源连接数据库
	 * ②返回值：如果选择主数据源返回字符串"DATASOURCE_MAIN" ; 如果选择日志数据源返回字符串"DATASOURCE_LOG"
	 * 		根据返回值来获取数据源来连接数据库
			<bean id="logRouterDataSource" class="com.atguigu.survey.log.router.LogRouterDataSource">	
				<property name="targetDataSources">
					<map>
						<entry key="DATASOURCE_MAIN" value-ref="dataSourceMain"/>
						<entry key="DATASOURCE_LOG" value-ref="dataSourceLog"/>
					</map>
				</property>
				<property name="defaultTargetDataSource" ref="dataSourceMain"/>
			</bean>
			
		③什么时候解除线程绑定？
			
	 */
	@Override
	protected Object determineCurrentLookupKey() {
		
		String key = RouterKeyBinder.getKey();
		
		//利用完key后，立马删除！
		RouterKeyBinder.removeKey();
		
		return key;
	}

}
