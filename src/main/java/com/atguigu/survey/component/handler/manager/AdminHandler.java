package com.atguigu.survey.component.handler.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.BatchUpdateException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.e.RemoveAdminFailedException;
import com.atguigu.survey.e.RemoveAuthFailedException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.utils.GlobalMessage;
import com.atguigu.survey.utils.GlobalNames;
import com.atguigu.survey.utils.PdfUtil;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SuppressWarnings("all")
@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService ;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private UserService userService ;

	@RequestMapping("/manager/admin/doDispatcherRole")
	public String doDispatcherRole(
				@RequestParam("adminId") Integer adminId ,
				@RequestParam(value="roleList",required=false) List<Integer> roleIdList){
		
		adminService.doDispatcherRole(roleIdList,adminId);
		
		return "redirect:/manager/admin/showAdminList";
	}
	
	
	//给管理员分配角色-列表页面
	@RequestMapping("manager/admin/toDispatcherUI/{adminId}")
	public String toDispatcherUI(@PathVariable("adminId") Integer adminId,Map map){
		List<Role> queryAllRole = roleService.queryRoleList();
		
		List<Integer> currentIdList =  adminService.getCurrentRoleList(adminId);
		
		map.put("allRoleList", queryAllRole);
		map.put("currentIdList", currentIdList);
		map.put("adminId", adminId);
		
		return "manager/admin_toDispatcherUI";
	}
	
	
	//批量删除
	@RequestMapping("/manager/admin/batchDelete")
	public String batchDelete(@RequestParam(value="adminList",required=false) List<Integer> adminIdList){
		
		try {
			adminService.batchDelete(adminIdList);
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if(cause instanceof BatchUpdateException){
				throw new RemoveAdminFailedException(GlobalMessage.REMOVE_ADMIN_FAILED);
			}
			if(cause != null && cause instanceof MySQLIntegrityConstraintViolationException) {
				throw new RemoveAuthFailedException(GlobalMessage.REMOVE_ADMIN_FAILED);
			}
		}
		
		return "redirect:/manager/admin/showAdminList";
	}
	
	//更新账号名称
	@ResponseBody
	@RequestMapping("/manager/admin/updateAdmin")
	public Map<String,String> updateAdmin(Admin admin){
		Map<String,String> map = new HashMap<String,String>();
		try {
			adminService.updateAdminNameByAdminId(admin) ;
			map.put("resultMessage", "true");
		} catch (Exception e) {
			map.put("resultMessage", "false");
		}
		return map;
	}
	
	//查看所有账号
	@RequestMapping("/manager/admin/showAdminList")
	public String showAdminList(Map map){
		List<Admin> adminList =  adminService.queryAllList();
		map.put("adminList", adminList);
		return "manager/admin_showAdminList";
	}
	
	
	//保存管理员
	@RequestMapping("/manager/admin/saveAdmin")
	public String saveAdmin(Admin admin){
		adminService.saveAdmin(admin);
		return "redirect:/manager/admin/showAdminList";
	}
	
	
	//--------------------------------------------------------
	
/*	//为升级拦截器，临时性测试
	@RequestMapping("/manager/statistics/showAllAvailable")
	public String showAllAvailable(){		
		return "redirect:/manager/admin/toMainUI";
	}*/
	 
	
	@RequestMapping("/manager/admin/logout")
	public String logout(HttpSession session){
		
		session.invalidate();
		
		return "redirect:/manager/admin/toMainUI";
	}
	
	
	@RequestMapping("/manager/admin/login")
	public String login(Admin admin,HttpSession session){
		
		Admin adminDB = adminService.loginForAdmin(admin);
		
		session.setAttribute(GlobalNames.LOGIN_Admin, adminDB);
		
		return "redirect:/manager/admin/toMainUI";
	}
	
	//新增客户信息
	@RequestMapping("/manager/admin/saveGuest")
	public String saveGuest(User user){
		userService.regist(user);
		return "redirect:/manager/admin/showAdminList";
	}
	
	//查看所有客户
	@RequestMapping("/manager/admin/showGuestList")
	public String showGuestList(Map map){
		List<User> userList =  userService.queryAllList();
		map.put("userList", userList);
		return "manager/admin_showGuestList";
	}
	
	//查看所有客户
	@RequestMapping("/manager/admin/exportReport")
	public void exportReport(Map map, HttpServletResponse response){
		String fileName = this.getClass().getClassLoader().getResource("/template/paReport.html").getPath();
		String content = PdfUtil.readToString(fileName);
		PdfUtil.createTempPdf(content);
		
		// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        // 设置响应头，控制浏览器下载该文件
        response.setHeader("content-disposition", "attachment;filename=" + "111.pdf");
        // 读取要下载的文件，保存到文件输入流
    	FileInputStream in = null;
		try {
			in = new FileInputStream("D:\\fff222.pdf");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 创建输出流
    	OutputStream out = null;
		try {
			out = response.getOutputStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        // 创建缓冲区
        byte buffer[] = new byte[1024];
        int len = 0;
        // 循环将输入流中的内容读取到缓冲区当中
        try {
			while ((len = in.read(buffer)) > 0) {
			    // 输出缓冲区的内容到浏览器，实现文件下载
			    out.write(buffer, 0, len);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
