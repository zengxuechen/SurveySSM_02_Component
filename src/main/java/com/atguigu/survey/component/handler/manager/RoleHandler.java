package com.atguigu.survey.component.handler.manager;

import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.survey.component.service.i.AuthService;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.e.RemoveAuthFailedException;
import com.atguigu.survey.e.RemoveRoleFailedException;
import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.GlobalMessage;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SuppressWarnings("all")
@Controller
public class RoleHandler {

	@Autowired
	private RoleService roleService ;
	
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping("/manager/role/doDispatcherRole")
	public String doDispatcher(
			@RequestParam("roleId") Integer roleId, 
			@RequestParam(value="authList", required=false) List<Integer> authIdList) {
		
		roleService.updateRelationship(roleId, authIdList);
		
		return "redirect:/manager/role/showRoleList";
	}
	
	@RequestMapping("/manager/role/toDispatcherUI/{roleId}")
	public String toDispatcherUI(@PathVariable("roleId") Integer roleId,Map<String, Object> map) {
		
		List<Auth> allAuthList = authService.queryAuthList();
		List<Integer> currentIdList = roleService.getCurrentAuthIdList(roleId);
		
		map.put("allAuthList", allAuthList);
		map.put("currentIdList", currentIdList);
		map.put("roleId", roleId);
		
		return "/manager/role_toDispatcherUI";
	}
	
	@ResponseBody
	@RequestMapping("/manager/role/updateRole")
	public Map<String, Object> updateRole(Role role) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		
		try {
			roleService.updateRole(role);
			jsonMap.put("resultMessage", "鎿嶄綔鎴愬姛锛�");
		} catch (Exception e) {
			e.printStackTrace();
			jsonMap.put("resultMessage", "鎿嶄綔澶辫触锛�");
		}
		
		return jsonMap;
	}
	
	@RequestMapping("/manager/role/batchDelete")
	public String batchDelete(@RequestParam(value="roleList", required=false) List<Integer> roleIdList) {
		
		if(roleIdList != null && roleIdList.size() > 0) {
			try {
				roleService.batchDelete(roleIdList);
			} catch (Exception e) {
				
				Throwable cause = e.getCause();
				
				if(cause != null && cause instanceof BatchUpdateException) {
					throw new RemoveRoleFailedException(GlobalMessage.REMOVE_ROLE_FAILED);
				}
				if(cause != null && cause instanceof MySQLIntegrityConstraintViolationException) {
					throw new RemoveAuthFailedException(GlobalMessage.REMOVE_ROLE_FAILED);
				}
				
			}
		}
		
		return "redirect:/manager/role/showRoleList";
	}
	
	
	//鏌ヨ鎵�鏈夎鑹�
	@RequestMapping("/manager/role/showRoleList")
	public String showRoleList(Map map){
		List<Role> roleList =  roleService.queryRoleList();
		map.put("roleList", roleList);
		return "manager/role_showRoleList";
	}
	
	//淇濆瓨瑙掕壊
	@RequestMapping("/manager/role/saveRole")	
	public String saveRole(Role role){
		roleService.saveEntity(role);
		return "redirect:/manager/role/showRoleList";
	}
}
