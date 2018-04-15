package com.atguigu.survey.component.service.m;

import java.util.HashMap;
import java.util.List;
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
		
		//1.获取用户名和密码字符串
		String userName = user.getUserName();
		String userPwd = user.getUserPwd();
		
		//①检查用户是否在数据库中存在
		boolean exists = userMapper.checkUserName(userName) > 0;
		//②如果用户名称在数据库中存在，那么就抛异常
		if(exists){
			throw new UserNameAlreadyExistsException(GlobalMessage.USER_NAME_ALREADY_EXISTS);			
		}
		

		//4.如果用户名可用，则对密码进行加密
		userPwd = DataProcessUtils.md5(userPwd);
		user.setUserPwd(userPwd);
		
		//5.保存User对象
		userMapper.insert(user);
		
		//----------------计算用户权限码数组---------------
		//i.判断用户的类别
		Boolean company = user.getCompany();
		
		//ii.根据用户类别查询对应的Role对象
		String roleName = null;
		
		if(company) {
			roleName = "企业用户";
		}else{
			roleName = "个人用户";
		}
		
		Role role = roleMapper.getRoleByName(roleName);
		
		//iii.保存关联关系
		Integer userId = user.getUserId();
		Integer roleId = role.getRoleId();
		
		userMapper.saveRelationship(userId, roleId);
		
		//iv.根据userId查询Set<Role>
		Set<Role> roleSet = userMapper.getRoleSetDeeply(userId);
		
		//v.查询最大权限位值
		Integer maxPos = resMapper.getMaxPos();
		
		//vi.计算权限码数组值
		String codeArr = DataProcessUtils.calculateCodeArr(roleSet, maxPos);
		
		//vii.给User对象设置codeArr
		userMapper.updateUserCodeArr(userId, codeArr);
		
		//---------------------------------------------
		
	}

	//<tx:method name="login" read-only="true" />
	public User login(String userName, String userPwd) {
		
		//密码加密
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
	
	public List<User> queryAllList(){
		return userMapper.selectAll();
	}


	public void updateEntity(User t) {
		userMapper.updateByPrimaryKey(t);
	}

	public void removeEntityById(Integer entityId) {
		userMapper.deleteByPrimaryKey(entityId);
	}

	public Integer saveUserAndReturnId(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public User getEntity(Integer entityId) {
		// TODO Auto-generated method stub
		return null;
	}
}
