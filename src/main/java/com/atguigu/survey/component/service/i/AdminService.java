package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.manager.Admin;

public interface AdminService{
	
	Admin getEntity(Integer entityId);

	void updateEntity(Admin t);

	void removeEntityById(Integer entityId);

	//-----------------------------
	Admin loginForAdmin(Admin admin);

	List<Admin> queryAllList();

	void updateAdminNameByAdminId(Admin admin);

	void saveAdmin(Admin admin);

	void batchDelete(List<Integer> adminIdList);

	List<Integer> getCurrentRoleList(Integer adminId);

	void doDispatcherRole(List<Integer> roleIdList, Integer adminId);

}
