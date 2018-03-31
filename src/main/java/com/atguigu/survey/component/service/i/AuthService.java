package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.manager.Auth;

public interface AuthService {
	Auth getEntity(Integer entityId);

	void updateEntity(Auth t);

	void removeEntityById(Integer entityId);

	void saveEntity(Auth t);
	
	//-----------------------------
	List<Auth> queryAuthList();

	void batchDelete(List<Integer> authIdList);

	List<Integer> getCurrentAuthResList(Integer authId);

	void doDispatcherAuth(List<Integer> resIdList, Integer authId);


}
