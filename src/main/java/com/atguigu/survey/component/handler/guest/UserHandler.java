package com.atguigu.survey.component.handler.guest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class UserHandler {
	
	@Autowired
	private UserService userService ;
	
	@RequestMapping("/guest/user/logout")
	public String logout(HttpSession session){		
		session.invalidate();		
		//session.removeAttribute(GlobalNames.LOGIN_USER);		
		return "redirect:/index.jsp";
	}
	
	/**
	 * 需要在当前项目中引用javax.servlet,否则，无法导入HttpSession包
	 * 	<dependency>
	 *		<groupId>javax.servlet</groupId>
	 *		<artifactId>servlet-api</artifactId>
	 *		<version>2.5</version>
	 *		<scope>provided</scope>
	 *	</dependency>
	 * @param userName
	 * @param userPwd
	 * @param session
	 * @return
	 */
	@RequestMapping("/guest/user/login")
	public String login(@RequestParam("userName") String userName,
						@RequestParam("userPwd")String userPwd,
						HttpSession session){		
		User user = userService.login(userName,userPwd);		
		session.setAttribute(GlobalNames.LOGIN_USER, user);
		return "redirect:/index.jsp";
	}
	
	
	@RequestMapping("/guest/user/regist")
	public String regist(User user){		
		userService.regist(user);		
		return "guest/user_login" ;
	}
	
}
