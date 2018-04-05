package com.atguigu.survey.component.service.m;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.ResMapper;
import com.atguigu.survey.component.dao.i.RoleMapper;
import com.atguigu.survey.component.dao.i.UserMapper;
import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.e.UserLoginFailedExcption;
import com.atguigu.survey.e.UserNameAlreadyExistsException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.DataProcessUtils;
import com.atguigu.survey.utils.GlobalMessage;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper ;
	
	
	@Autowired
	private RoleMapper roleMapper ;
	
	@Autowired
	private ResMapper resMapper ;

	public void regist(User user) {
		
		//1.鑾峰彇鐢ㄦ埛鍚嶅拰瀵嗙爜瀛楃涓�
		String userName = user.getUserName();
		String userPwd = user.getUserPwd();
		
		//鈶犳鏌ョ敤鎴锋槸鍚﹀湪鏁版嵁搴撲腑瀛樺湪
		boolean exists = userMapper.checkUserName(userName) > 0;
		//鈶″鏋滅敤鎴峰悕绉板湪鏁版嵁搴撲腑瀛樺湪锛岄偅涔堝氨鎶涘紓甯�
		if(exists){
			throw new UserNameAlreadyExistsException(GlobalMessage.USER_NAME_ALREADY_EXISTS);			
		}
		

		//4.濡傛灉鐢ㄦ埛鍚嶅彲鐢紝鍒欏瀵嗙爜杩涜鍔犲瘑
		userPwd = DataProcessUtils.md5(userPwd);
		user.setUserPwd(userPwd);
		
		//5.淇濆瓨User瀵硅薄
		userMapper.insert(user);
		
		//----------------璁＄畻鐢ㄦ埛鏉冮檺鐮佹暟缁�---------------
		//i.鍒ゆ柇鐢ㄦ埛鐨勭被鍒�
		Boolean company = user.getCompany();
		
		//ii.鏍规嵁鐢ㄦ埛绫诲埆鏌ヨ瀵瑰簲鐨凴ole瀵硅薄
		String roleName = null;
		
		if(company) {
			roleName = "浼佷笟鐢ㄦ埛";
		}else{
			roleName = "涓汉鐢ㄦ埛";
		}
		
		Role role = roleMapper.getRoleByName(roleName);
		
		//iii.淇濆瓨鍏宠仈鍏崇郴
		Integer userId = user.getUserId();
		Integer roleId = role.getRoleId();
		
		userMapper.saveRelationship(userId, roleId);
		
		//iv.鏍规嵁userId鏌ヨSet<Role>
		Set<Role> roleSet = userMapper.getRoleSetDeeply(userId);
		
		//v.鏌ヨ鏈�澶ф潈闄愪綅鍊�
		Integer maxPos = resMapper.getMaxPos();
		
		//vi.璁＄畻鏉冮檺鐮佹暟缁勫��
		String codeArr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
		
		//vii.缁橴ser瀵硅薄璁剧疆codeArr
		userMapper.updateUserCodeArr(userId, codeArr);
		
		//---------------------------------------------
		
	}

	//<tx:method name="login" read-only="true" />
	public User login(String userName, String userPwd) {
		
		//瀵嗙爜鍔犲瘑
		String md5Pwd = DataProcessUtils.md5(userPwd);		
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", userName);
		params.put("userPwd",md5Pwd);
		User user = userMapper.login(params);
		
		if(user == null){
			throw new UserLoginFailedExcption(GlobalMessage.USER_LOGIN_FAILED);
		}
		
		return user;
	}

    public Integer saveUserAndReturnId(User user) {
        return userMapper.saveUserAndReturnId(user);
    }

    public User getEntity(Integer entityId) {
		return userMapper.selectByPrimaryKey(entityId);
	}

	public void updateEntity(User t) {
		userMapper.updateByPrimaryKey(t);
	}

	public void removeEntityById(Integer entityId) {
		userMapper.deleteByPrimaryKey(entityId);
	}
}
