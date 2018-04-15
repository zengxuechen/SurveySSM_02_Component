package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;

public interface UserService {
	User getEntity(Integer entityId);

	void updateEntity(User t);

	void removeEntityById(Integer entityId);

	//-----------------------------
	void regist(User user);

	User login(String userName, String userPwd);

    Integer saveUserAndReturnId(User user);
    
    List<User> queryAllList();
}
