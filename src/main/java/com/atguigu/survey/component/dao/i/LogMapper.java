package com.atguigu.survey.component.dao.i;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.manager.Log;

public interface LogMapper {
	int deleteByPrimaryKey(Integer logId);

	int insert(Log record);

	Log selectByPrimaryKey(Integer logId);

	List<Log> selectAll();

	int updateByPrimaryKey(Log record);

	void createTable(@Param("tableName") String tableName);

	List<String> getAllLogTableNames();

	int getCountLog(List<String> allLogTableNames);

	List<Log> getLimitLogPage(@Param("list") List<String> allLogTableNames,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);
}