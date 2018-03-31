package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.manager.Role;

public interface RoleService{
	Role getEntity(Integer entityId);

	void removeEntityById(Integer entityId);

	void saveEntity(Role t);
	
	//-----------------------------
	List<Role> queryRoleList();

	void batchDelete(List<Integer> roleIdList);

	void updateRole(Role role);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void updateRelationship(Integer roleId, List<Integer> authIdList);

}
