package com.atguigu.survey.component.dao.i;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.manager.Res;

public interface ResMapper {
	
	int deleteByPrimaryKey(Integer resId);

	int insert(Res record);

	Res selectByPrimaryKey(Integer resId);

	List<Res> selectAll();

	int updateByPrimaryKey(Res record);

	List<Res> getResListByAuthId(Integer authId);

	// ---------------------------------
	int checkResExist(@Param("servletPath") String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(Integer maxPos);

	void batchDelete(List<Integer> resList);

	void updateStatus(Integer resId);

	boolean getStatus(Integer resId);

	Res getResByServletpath(String servletPath);
}