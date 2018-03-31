package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.guest.User;

public interface UserService {
	User getEntity(Integer entityId);

	void updateEntity(User t);

	void removeEntityById(Integer entityId);

	//-----------------------------
	void regist(User user);

	User login(String userName, String userPwd);

}
