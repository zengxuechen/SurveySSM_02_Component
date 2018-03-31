package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.LogMapper;
import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.model.Page;

@Service
public class LogServiceImpl implements LogService {

	@Autowired
	private LogMapper logMapper ;

	public void createTable(String tableName) {
		logMapper.createTable(tableName);
	}

	public void saveLog(Log log) {
		logMapper.insert(log);
	}

	public Page<Log> queryLogList(String pageNoStr) {
		
		List<String> allLogTableNames = logMapper.getAllLogTableNames();
		
		int totalCountLog = logMapper.getCountLog(allLogTableNames);
		
		Page<Log> page = new Page(pageNoStr,totalCountLog);
		
		int pageSize = page.getPageSize();
		int pageNo = page.getPageNo();
		
		List<Log> limitLogPage = logMapper.getLimitLogPage(allLogTableNames,(pageNo-1)*pageSize, pageSize);
		
		page.setList(limitLogPage);
				
		return page;
	}
}
