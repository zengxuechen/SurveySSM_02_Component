package com.atguigu.survey.component.dao.i;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;

public interface AdminMapper {

	int deleteByPrimaryKey(Integer adminId);

	int insert(Admin record);

	Admin selectByPrimaryKey(Integer adminId);

	List<Admin> selectAll();

	int updateByPrimaryKey(Admin record);

	// -----------------------------
	Admin loginForAdmin(Admin admin);

	int checkAdminNameExists(String adminName);

	void batchDelete(List<Integer> adminIdList);

	Set<Role> getDeeplyRoleSet(Integer adminId);// 深度加载roleSet集合

	List<Integer> getCurrentRoleList(Integer adminId);

	void removeOldRelationship(Integer adminId);

	void saveNewRelationship(@Param("roleIdList") List<Integer> roleIdList,
			@Param("adminId") Integer adminId);

	void updateAdminNameByAdminId(Admin admin);

	void updateCodeArrStrByAdminId(@Param("adminId") Integer adminId,
			@Param("codeArrStr") String codeArrStr);
}