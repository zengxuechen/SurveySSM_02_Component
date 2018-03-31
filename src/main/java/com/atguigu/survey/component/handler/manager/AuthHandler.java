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
import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.e.RemoveAuthFailedException;
import com.atguigu.survey.entities.manager.Auth;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.GlobalMessage;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SuppressWarnings("all")
@Controller
public class AuthHandler {

	@Autowired
	private AuthService authService ;
	
	@Autowired
	private ResService resService ;
	
	//给权限配置资源
	@RequestMapping("/manager/auth/doDispatcherAuth")
	public String doDispatcherAuth(
				@RequestParam("authId") Integer authId,
				@RequestParam(value="resList",required=false) List<Integer> resIdList){ //避免没有提交任何值情况
		
		authService.doDispatcherAuth(resIdList,authId);
		
		return "redirect:/manager/auth/showAuthList";
	}
	
	//查询所有资源，显示给权限分配资源的页面
	@RequestMapping("/manager/auth/toDispatcherUI/{authId}")
	public String toDispatcherUI(@PathVariable("authId") Integer authId,Map map){
		List<Res> queryAllRes = resService.queryAllRes();
		
		List<Integer> currentAuthResList =  authService.getCurrentAuthResList(authId);
		
		map.put("queryAllRes", queryAllRes);
		map.put("currentAuthResList", currentAuthResList);
		map.put("authId", authId);
		
		return "manager/auth_toDispatcherUI";
	}
	
	
	//批量删除权限
	@RequestMapping("/manager/auth/batchDelete")
	public String batchDelete(@RequestParam(value="authList",required=false) List<Integer> authIdList){
		
		if(authIdList != null && authIdList.size() > 0) {
			try {
				authService.batchDelete(authIdList);
			} catch (Exception e) {
				
				Throwable cause = e.getCause();
				
				if(cause != null && cause instanceof BatchUpdateException) {
					throw new RemoveAuthFailedException(GlobalMessage.REMOVE_AUTH_FAILED);
				}
				if(cause != null && cause instanceof MySQLIntegrityConstraintViolationException) {
					throw new RemoveAuthFailedException(GlobalMessage.REMOVE_AUTH_FAILED);
				}
				
				
			}
		}
		return "redirect:/manager/auth/showAuthList";
	}
	
	//更新权限名称
	@ResponseBody
	@RequestMapping("/manager/auth/updateAuth")
	public Map<String,String> updateAuth(Auth auth){
		Map<String,String> map = new HashMap<String,String>();
		try {
			/*String hql = "update Auth a set a.authName=? where a.authId=?";
			updateEntityByHql(hql, auth.getAuthName(), auth.getAuthId());*/
			authService.updateEntity(auth);
			map.put("resultMessage", "操作成功!");
		} catch (Exception e) {
			map.put("resultMessage", "操作失败!");
		}
		return map ;
	}
	
	//查询所有权限
	@RequestMapping("/manager/auth/showAuthList")
	public String showAuthList(Map map){
		List<Auth> authList =  authService.queryAuthList();
		map.put("authList", authList);
		return "manager/auth_showAuthList";
	}
	
	//保存权限
	@RequestMapping("/manager/auth/saveAuth")
	public String saveAuth(Auth auth){
		authService.saveEntity(auth);
		return "redirect:/manager/auth/showAuthList";
	}
	
}
