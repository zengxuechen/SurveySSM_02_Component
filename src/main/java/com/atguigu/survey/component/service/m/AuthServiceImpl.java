package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.AdminMapper;
import com.atguigu.survey.component.dao.i.AuthMapper;
import com.atguigu.survey.component.dao.i.ResMapper;
import com.atguigu.survey.component.dao.i.UserMapper;
import com.atguigu.survey.component.service.i.AuthService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataProcessUtils;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired 
	private AuthMapper authMapper ;
	
	@Autowired
	private AdminMapper adminMapper ;
	
	@Autowired
	private ResMapper resMapper ;
	
	@Autowired
	private UserMapper userMapper ;

	public List<Auth> queryAuthList() {
		return authMapper.selectAll();
	}

	public void batchDelete(List<Integer> authIdList) {
		//authMapper.batchDeleteAuthRes(authIdList);
		authMapper.batchDelete(authIdList);
	}

	public List<Integer> getCurrentAuthResList(Integer authId) {
		return authMapper.getCurrentAuthResList(authId);
	}

	public void doDispatcherAuth(List<Integer> resIdList, Integer authId) {
		authMapper.removeOldRelationShip(authId);
		if(resIdList!=null ){ //濡傛灉resIdList涓簄ull,璇存槑涓嶉渶瑕佸垎閰嶄换浣曡祫婧愮粰杩欎釜鏉冮檺
			authMapper.saveNewRelationShip(resIdList,authId);
		}
		
		//鈥婚噸鏂拌绠楁墍鏈夌敤鎴风殑鏉冮檺鐮�
		Integer maxPos = resMapper.getMaxPos();
		
		//========================================adminMapper.reCalculateCode(maxPos);
		
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
			userMapper.updateCodeArrStrByUserId(user.getUserId(),codeArrStr);
		}

	}

	public Auth getEntity(Integer entityId) {
		return authMapper.selectByPrimaryKey(entityId);
	}

	public void updateEntity(Auth t) {
		authMapper.updateByPrimaryKey(t);
	}

	public void removeEntityById(Integer entityId) {
		authMapper.deleteByPrimaryKey(entityId);
	}

	public void saveEntity(Auth t) {
		authMapper.insert(t);
	}

	
}
