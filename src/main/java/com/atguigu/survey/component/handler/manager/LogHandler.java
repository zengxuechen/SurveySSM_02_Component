package com.atguigu.survey.component.handler.manager;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.log.router.LogRouterDataSource;
import com.atguigu.survey.log.router.RouterKeyBinder;
import com.atguigu.survey.model.Page;

@SuppressWarnings("all")
@Controller
public class LogHandler {

	@Autowired
	private LogService logService ;
	
	@RequestMapping("/manager/log/showLogList")
	public String showLogList(
				@RequestParam(value="pageNoStr",required=false) String pageNoStr,
				Map map){
		
		RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);
		
		Page<Log> page = logService.queryLogList(pageNoStr);
		map.put("page", page);
		
		return "manager/log_showList";
	}
	
	
}
