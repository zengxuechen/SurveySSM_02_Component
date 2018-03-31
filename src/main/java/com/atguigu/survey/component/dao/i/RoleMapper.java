package com.atguigu.survey.component.dao.i;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.manager.Role;

public interface RoleMapper {
	
	int deleteByPrimaryKey(Integer roleId);

	int insert(Role record);

	Role selectByPrimaryKey(Integer roleId);

	List<Role> selectAll();

	int updateByPrimaryKey(Role record);

	List<Role> getRoleListByUserId(Integer userId); // User和角色多对多关系-辅助

	List<Role> getRoleListByAdminId(Integer adminId);// Admin和角色多对多关系-辅助
	// ---------------------------------

	void batchDelete(List<Integer> roleIdList);

	List<Integer> getCurrentAuthIdList(Integer roleId);

	void removeOldRelationship(Integer roleId);

	void saveNewRelationship(@Param("authIdList") List<Integer> authIdList,
			@Param("roleId") Integer roleId);

	Role getRoleByCompany(String company);

	Role getRoleByName(String roleName);

}