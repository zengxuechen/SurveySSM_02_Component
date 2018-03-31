package com.atguigu.survey.component.dao.i;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.manager.Auth;

public interface AuthMapper {
	
	int deleteByPrimaryKey(Integer authId);

	int insert(Auth record);

	Auth selectByPrimaryKey(Integer authId);

	List<Auth> selectAll();

	int updateByPrimaryKey(Auth record);

	List<Auth> getAuthListByRoleId(Integer authId);

	// ---------------------------------
	void batchDeleteAuthRes(List<Integer> authIdList);

	void batchDelete(List<Integer> authIdList);

	List<Integer> getCurrentAuthResList(Integer authId);

	void removeOldRelationShip(Integer authId);

	void saveNewRelationShip(@Param("resIdList") List<Integer> resIdList,
			@Param("authId") Integer authId);

}