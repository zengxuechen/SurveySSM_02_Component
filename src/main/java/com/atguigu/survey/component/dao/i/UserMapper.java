package com.atguigu.survey.component.dao.i;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Role;

@SuppressWarnings("rawtypes")
public interface UserMapper {
	
	int deleteByPrimaryKey(Integer userId);

	int insert(User record);

	User selectByPrimaryKey(Integer userId);

	List<User> selectAll();

	int updateByPrimaryKey(User record);

	// -------------------------------
	int checkUserName(String userName);

	User login(Map params);

	void insertUserRoleRelationship(Map map);

	Set<Role> getRoleSetDeeply(Integer userId);

	void updateCodeArrStrByUserId(@Param("userId") Integer userId,
			@Param("codeArrStr") String codeArrStr);

	void saveRelationship(@Param("userId") Integer userId,
			@Param("roleId") Integer roleId);

	void updateUserCodeArr(@Param("userId") Integer userId,
			@Param("codeArr") String codeArr);
}