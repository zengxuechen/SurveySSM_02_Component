package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.AdminMapper;
import com.atguigu.survey.component.dao.i.ResMapper;
import com.atguigu.survey.component.dao.i.RoleMapper;
import com.atguigu.survey.component.dao.i.UserMapper;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataProcessUtils;

@Service
public class RoleServiceImpl implements
		RoleService {

	@Autowired
	private RoleMapper roleMapper ;
	
	@Autowired
	private AdminMapper adminMapper ;
	
	@Autowired
	private ResMapper resMapper ;
	
	@Autowired
	private UserMapper userMapper ;
	
	
	public List<Role> queryRoleList() {
		return roleMapper.selectAll();
	}

	public void batchDelete(List<Integer> roleIdList) {
		roleMapper.batchDelete(roleIdList);
	}

	public void updateRole(Role role) {
		roleMapper.updateByPrimaryKey(role);
	}

	public List<Integer> getCurrentAuthIdList(Integer roleId) {
		return roleMapper.getCurrentAuthIdList(roleId);
	}

	public void updateRelationship(Integer roleId, List<Integer> authIdList) {
		roleMapper.removeOldRelationship(roleId);
		
		if(authIdList != null) {
			roleMapper.saveNewRelationship(authIdList,roleId);
		}
		
		//鈥婚噸鏂拌绠楁墍鏈夌敤鎴风殑鏉冮檺鐮�
		Integer maxPos = resMapper.getMaxPos();
		
		//===================================adminMapper.reCalculateCode(maxPos);
		
		//涓�銆侀噸鏂拌绠桝dmin鐢ㄦ埛鐨勬潈闄愮爜
		//1.鏌ヨ鎵�鏈堿dmin鐢ㄦ埛
		List<Admin> adminList = adminMapper.selectAll();
		
		//2.閬嶅巻Admin鐢ㄦ埛闆嗗悎
		for (Admin admin : adminList) {
			//3.閲嶆柊璁＄畻鏉冮檺鐮�
			Set<Role> roleSet = adminMapper.getDeeplyRoleSet(admin.getAdminId());
			String codeArrStr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
			adminMapper.updateCodeArrStrByAdminId(admin.getAdminId(), codeArrStr);
		}

		//浜屻�侀噸鏂拌绠桿ser鐢ㄦ埛鐨勬潈闄愮爜
		//1.鏌ヨ鎵�鏈塙ser鐢ㄦ埛
		List<User> userList= userMapper.selectAll();
		
		//2.閬嶅巻User鐢ㄦ埛闆嗗悎
		for (User user : userList) {
			//3.閲嶆柊璁＄畻鏉冮檺鐮�
			Set<Role> roleSet = userMapper.getRoleSetDeeply(user.getUserId());
			String codeArrStr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
			userMapper.updateCodeArrStrByUserId(user.getUserId(), codeArrStr);
		}

	}

	public Role getEntity(Integer entityId) {
		return roleMapper.selectByPrimaryKey(entityId);
	}

	public void removeEntityById(Integer entityId) {
		roleMapper.deleteByPrimaryKey(entityId);
	}

	public void saveEntity(Role t) {
		roleMapper.insert(t);
	}

}
