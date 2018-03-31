package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.model.Page;

public interface LogService{

	void createTable(String tableName);

	void saveLog(Log log);

	Page<Log> queryLogList(String pageNoStr);

}
