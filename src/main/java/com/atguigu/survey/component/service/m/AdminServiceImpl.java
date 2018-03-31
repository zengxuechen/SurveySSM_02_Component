package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.AdminMapper;
import com.atguigu.survey.component.dao.i.ResMapper;
import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.e.AdminLoginFailedExcption;
import com.atguigu.survey.e.AdminNameAlreadyExistsException;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataProcessUtils;
import com.atguigu.survey.utils.GlobalMessage;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private AdminMapper adminMapper ;
	
	@Autowired
	private ResMapper resMapper ;

	public Admin loginForAdmin(Admin admin) {
		//鍔犲瘑
		String adminPwd = admin.getAdminPwd();
		String md5 = DataProcessUtils.md5(adminPwd);
		admin.setAdminPwd(md5);
		
		Admin loginAdmin = adminMapper.loginForAdmin(admin);
		
		if(loginAdmin == null){
			throw new AdminLoginFailedExcption(GlobalMessage.ADMIN_LOGIN_FAILED);
		}
		
		return loginAdmin ;
	}

	public List<Admin> queryAllList() {
		return adminMapper.selectAll();
	}

	public void updateAdminNameByAdminId(Admin admin) {
		boolean exists = adminMapper.checkAdminNameExists(admin.getAdminName()) > 0 ;
		if(exists){
			throw new AdminNameAlreadyExistsException("璐﹀彿鍚嶇О宸茬粡瀛樺湪锛岃閲嶆柊鎿嶄綔");
		}else{
			adminMapper.updateAdminNameByAdminId(admin);
		}
	}

	public void saveAdmin(Admin admin) {
		boolean exists = adminMapper.checkAdminNameExists(admin.getAdminName()) > 0 ;
		if(exists){
			throw new AdminNameAlreadyExistsException("璐﹀彿鍚嶇О宸茬粡瀛樺湪锛岃閲嶆柊娉ㄥ唽");
		}
		
		String adminPwd = admin.getAdminPwd();
		String md5 = DataProcessUtils.md5(adminPwd);
		admin.setAdminPwd(md5);
		adminMapper.insert(admin);
	}

	public void batchDelete(List<Integer> adminIdList) {
		adminMapper.batchDelete(adminIdList);
	}

	public List<Integer> getCurrentRoleList(Integer adminId) {		
		return adminMapper.getCurrentRoleList(adminId);
	}

	public void doDispatcherRole(List<Integer> roleIdList, Integer adminId) {
		
		//1.鍒犻櫎鏃х殑鍏宠仈鍏崇郴
		adminMapper.removeOldRelationship(adminId);
		
		//2.淇濆瓨鏂扮殑鍏宠仈鍏崇郴
		if(roleIdList!=null){
			adminMapper.saveNewRelationship(roleIdList,adminId);
		}
		
		//3.璁＄畻绠＄悊鍛樼殑鏉冮檺鐮佹暟缁�
		Integer maxPos = resMapper.getMaxPos(); //鑾峰彇鏈�澶ф潈闄愪綅
		
		//Admin admin = adminMapper.selectByPrimaryKey(adminId);
		
		//Set<Role> roleSet = admin.getRoleSet(); //authSet...   resSet...
		
		Set<Role> roleSet = adminMapper.getDeeplyRoleSet(adminId); //娣卞害鍔犺浇瑙掕壊闆嗗悎
		//涓嶇鏄鐞嗗憳杩樻槸鏅�氬憳宸ラ兘闇�瑕佽绠楅獙璇佺爜鏁扮粍锛屾墍浠ュ皝瑁呮垚涓�涓伐鍏锋柟娉�
		String codeArrStr = DataProcessUtils.calculateCodeArr(roleSet,maxPos);
		
		//鎸佷箙鍖栧璞″睘鎬у�艰缃悗锛岃嚜鍔ㄦ洿鏂�
		//admin.setCodeArrStr(codeArrStr);	
		adminMapper.updateCodeArrStrByAdminId(adminId,codeArrStr);
	}

	public Admin getEntity(Integer entityId) {
		return adminMapper.selectByPrimaryKey(entityId);
	}

	public void updateEntity(Admin t) {
		adminMapper.updateByPrimaryKey(t);
	}


	public void removeEntityById(Integer entityId) {
		adminMapper.deleteByPrimaryKey(entityId);
	}

	
}
