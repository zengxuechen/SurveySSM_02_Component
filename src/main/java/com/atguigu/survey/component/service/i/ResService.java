package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.manager.Res;

public interface ResService {

	void saveEntity(Res t);
	
	//-----------------------------
	boolean checkResExist(String servletPath);

	Integer getMaxPos();

	Integer getMaxCode(Integer maxPos);

	List<Res> queryAllRes();

	void batchDelete(List<Integer> resList);

	boolean updateStatus(Integer resId);

	Res getResByServletpath(String servletPath);

}
