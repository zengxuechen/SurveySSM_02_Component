package com.atguigu.survey.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Res;
import com.atguigu.survey.utils.DataProcessUtils;
import com.atguigu.survey.utils.GlobalNames;

/**
 * 细粒度的权限控制标签
 * @author zhangyu
 */
public class AuthTag extends SimpleTagSupport {

	private String servletPath ;
	
	@Override
	public void doTag() throws JspException, IOException {

		PageContext pageContext = (PageContext) getJspContext();		
		HttpSession session = pageContext.getSession();
		
		ServletContext servletContext = session.getServletContext();
		
		ApplicationContext ioc = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		
		ResService resService = ioc.getBean(ResService.class);
		Res res = resService.getResByServletpath(servletPath);
		
		
		//1.判断当前资源是否为公共资源
		//①如果是公共资源就放行
		if(res.getPublicRes()){
			getJspBody().invoke(null);
			return ;
		}
		
		//②如果不是公共资源，需要检查用户登录状态
		//还需要判断用户的资源是属于哪种资源
		if(servletPath.startsWith("/guest")){
			User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
			if(user!=null){
				String codeArrStr = user.getCodeArrStr();
				boolean checkedAuthority = DataProcessUtils.checkedAuthority(codeArrStr, res);
				if(checkedAuthority){
					getJspBody().invoke(null);
					return ;
				}				
			}
		}
		
		if(servletPath.startsWith("/manager")){
			Admin admin = (Admin)session.getAttribute(GlobalNames.LOGIN_Admin);
			if(admin!=null){
				String adminName = admin.getAdminName();
				if("SuperAdmin".equals(adminName)){
					getJspBody().invoke(null);
					return ;
				}
				
				String codeArrStr = admin.getCodeArrStr();
				boolean checkedAuthority = DataProcessUtils.checkedAuthority(codeArrStr, res);
				if(checkedAuthority){
					getJspBody().invoke(null);
					return ;	
				}
			}
		}

	}

	public void setServletPath(String servletPath) {
		this.servletPath = servletPath;
	}

}
