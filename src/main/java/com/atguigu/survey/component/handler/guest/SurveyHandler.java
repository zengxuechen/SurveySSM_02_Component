package com.atguigu.survey.component.handler.guest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.EditFileTooLargeException;
import com.atguigu.survey.e.EditFileTypeInvailidException;
import com.atguigu.survey.e.RemoveSurveyFailedException;
import com.atguigu.survey.e.SaveFileTooLargeException;
import com.atguigu.survey.e.SaveFileTypeInvailidException;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataProcessUtils;
import com.atguigu.survey.utils.GlobalMessage;
import com.atguigu.survey.utils.GlobalNames;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SuppressWarnings("all")
@Controller
public class SurveyHandler {

	@Autowired
	private SurveyService surveyService;
	
	@RequestMapping("/guest/survey/completeSurvey/{surveyId}")
	public String complete(@PathVariable("surveyId") Integer surveyId){
		
		surveyService.complete(surveyId);
		
		return "redirect:/guest/user/toLoginUI";
	}
	
	
	//深度删除
	@RequestMapping("/guest/survey/deeplyRemoveSurvey/{surveyId}/{pageNo}")
	public String deeplyRemoveSurvey(@PathVariable("surveyId") Integer surveyId,
									@PathVariable("pageNo") Integer pageNo){
		surveyService.deeplyRemove(surveyId);
		return "redirect:/guest/survey/showMyUnCompletedSurvey?pageNoStr="+pageNo;
	}
	
	
	//设计调查
	@RequestMapping("/guest/survey/toDesignUI/{surveyId}")
	public String toDesignUI(@PathVariable("surveyId") Integer surveyId,Map map){
		Survey survey = surveyService.getSurveyDeeply(surveyId);
		map.put("survey", survey);
		return "guest/survey_designUI";
	}
	
	//更新调查-更新数据库数据
	//提交表单时，将隐含域的值都封装到survey对象中：surveyId  logopath  user.userId
	@RequestMapping("guest/survey/updateSurvey")
	public String updateSurvey(
			HttpSession session,
			Survey survey,
			@RequestParam("pageNoStr") String pageNoStr ,
			@RequestParam("logoFile") MultipartFile logoFile
			/*,Map map*/
			,HttpServletRequest request) throws IOException{
		
		boolean empty = logoFile.isEmpty();
		
		if(!empty){ 
			
			//============文件上传验证===========================
			//一、文件大小验证
			long size = logoFile.getSize();
			if(size > 100*1024){  //一个程序员对另一个程序员说，你能不能借我1000块钱啊，好啊，我借你1024吧，凑个整数。
				/* map.put("survey",survey);//源码进行debug
				 * 框架会将异常对象存放到请求域中，但是，我们所存放到map中的survey就不会存放到requset域中了
				 */
				request.setAttribute("survey", survey);
				request.setAttribute("pageNoStr", pageNoStr);
				throw new EditFileTooLargeException(GlobalMessage.FILE_TOO_LARGE);
			}
			
			//二、文件类型验证
			String contentType = logoFile.getContentType();
			
			if(!contentType.startsWith("image/")){
				request.setAttribute("survey", survey);
				request.setAttribute("pageNoStr", pageNoStr);
				throw new EditFileTypeInvailidException(GlobalMessage.FILE_TYPE_INVAILID);
			}			
			//================================================			
			
			InputStream inputStream = logoFile.getInputStream();
			
			String virtualPath = "/surveyLogos";
			
			ServletContext servletContext = session.getServletContext();
			
			String realPath = servletContext.getRealPath(virtualPath);
			
			String logopath = DataProcessUtils.resizeImages(inputStream, realPath);
			
			survey.setLogoPath(logopath);
			
		}
		
		surveyService.updateEntity(survey);
		
		return "redirect:/guest/survey/showMyUnCompletedSurvey?pageNoStr="+pageNoStr;
	}
	
	//更新调查-表单回显
	@RequestMapping("guest/survey/toEditUI/{surveyId}/{pageNoStr}")
	public String toEditUI(
			@PathVariable("surveyId") Integer surveyId,
			@PathVariable("pageNoStr") String pageNoStr,
			Map map){
		
		Survey survey = surveyService.getSurveyDeeply(surveyId);
		
		map.put("survey", survey);
		map.put("pageNoStr", pageNoStr);
		
		return "guest/survey_editUI";
	}
	
	
	//删除调查
/*	@RequestMapping(value="/guest/survey/removeSurvey/{surveyId}/{pageNo}") //占位符不能前后有空格
	public String removeSurvey(@PathVariable("surveyId") Integer surveyId,
			@PathVariable("pageNo") Integer pageNo){
		surveyService.removeEntityById(surveyId);
		return "redirect:/guest/survey/showMyUnCompletedSurvey?pageNoStr="+pageNo;
	}
	*/
	
	//调查的删除保护
	@RequestMapping(value="/guest/survey/removeSurvey/{surveyId}/{pageNo}") //占位符不能前后有空格
	public String removeSurvey(@PathVariable("surveyId") Integer surveyId,
			@PathVariable("pageNo") Integer pageNo){
		
		try {
			surveyService.removeEntityById(surveyId);
		} catch (Exception e) {
			e.printStackTrace();
			
			Throwable cause = e.getCause();
			
			if(cause!=null && cause instanceof MySQLIntegrityConstraintViolationException){
				throw  new RemoveSurveyFailedException(GlobalMessage.REMOVE_SURVEY_FAILED);
			}
		}
		
		return "redirect:/guest/survey/showMyUnCompletedSurvey?pageNoStr="+pageNo;
	}
	
	
	
	//分页查询调查
	@RequestMapping("/guest/survey/showMyUnCompletedSurvey")
	public String showMyUnCompletedSurvey(
			HttpSession session,
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			Map<String,Object> map){
		
		User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
		
		Page<Survey> page = surveyService.getMyUnCompletedSurveyPage(user,pageNoStr);
		
		map.put(GlobalNames.PAGE, page);
		
		return "guest/survey_myuncompleted";
	}
	
	
	//保存调查对象
	//表单项和Survey实体类属性比较，都需要提供哪些值
	//将表单数据直接封装到指定的POJO中。注意logoFile不能直接封装。
	@RequestMapping("/guest/survey/saveSurvey")
	public String saveSurvey(
				HttpSession session,
				Survey survey,
				@RequestParam("logoFile") MultipartFile logoFile) throws IOException{
		
		//1.检查上传文件是否为空
		boolean empty = logoFile.isEmpty();
		
		if(!empty){
			
			//============文件上传验证===========================
			//一、文件大小验证
			long size = logoFile.getSize();
			if(size > 100*1024){  //一个程序员对另一个程序员说，你能不能借我1000块钱啊，好啊，我借你1024吧，凑个整数。
				throw new SaveFileTooLargeException(GlobalMessage.FILE_TOO_LARGE);
			}
			
			//二、文件类型验证
			String contentType = logoFile.getContentType();
			
			if(!contentType.startsWith("image/")){
				throw new SaveFileTypeInvailidException(GlobalMessage.FILE_TYPE_INVAILID);
			}			
			//================================================
			
			
			//2.声明虚拟路径
			String virtualPath = "/surveyLogos";
			
			//3.获取ServletContext对象
			ServletContext servletContext = session.getServletContext();
			
			//4.获取realPath
			String realPath = servletContext.getRealPath(virtualPath);
			
			//5.获取文件输入流
			InputStream inputStream = logoFile.getInputStream();
			
			//6.对上传文件进行压缩
			String logoPath = DataProcessUtils.resizeImages(inputStream, realPath);
			
			//7.设置文件的路径，将路径保存到数据库中
			survey.setLogoPath(logoPath);
		}
		
		//8.将user关联到survey中
		User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
		survey.setUserId(user.getUserId());
		
		//9保存survey对象
		surveyService.saveEntity(survey);
		
		//return "redirect:/guest/user/toLoginUI";
		return "redirect:/guest/survey/showMyUnCompletedSurvey";
	}
	
	
	//测试3-将上传文件压缩，调用压缩的工具方法，并查看物理路径下的压缩后文件情况
	//@RequestMapping("/guest/survey/saveSurvey")
	public String testResizeImage(HttpSession session,
								@RequestParam("logoFile") MultipartFile logoFile) throws IOException{
		
		//检查文件上传是否为空
		boolean empty = logoFile.isEmpty();
		if(!empty){
			//声明虚拟路径
			String virtualPath = "/surveyLogos";
			
			//获取ServletContext对象
			ServletContext servletContext = session.getServletContext();
			
			//获取realPath
			String realPath = servletContext.getRealPath(virtualPath);
			
			//获取文件输入流
			InputStream inputStream = logoFile.getInputStream();
			
			//对上传文件进行压缩
			String resizeImages = DataProcessUtils.resizeImages(inputStream, realPath);
			
			System.out.println("resizeImages="+resizeImages); //   surveyLogos/229804053705398.jpg
			
		}
		
		return "redirect:/guest/user/toLoginUI";
	}
	
	
	//测试2-获取realPath路径
	//@RequestMapping("/guest/survey/saveSurvey")
	public String testRealPath(HttpSession session){
		
		//定义一个虚拟路径
		String virtualPath = "/surveyLogos";
		
		//获取ServletContext对象
		ServletContext servletContext = session.getServletContext();
		
		//根据虚拟路径获取虚拟路径在部署到服务器上的真实路径
		String realPath = servletContext.getRealPath(virtualPath);
		
		//realPath=F:\SurveyNew\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\Survey_01_UI\surveyLogos
		System.out.println("realPath="+realPath);
		
		return "redirect:/guest/user/toLoginUI";
	}
	
	//测试1-获取文件上传文件的信息（普通表单项和文件上传表单项）
	//@RequestMapping("/guest/survey/saveSurvey")
	public String showUploadMessage(@RequestParam("surveyName") String surveyName,
							@RequestParam("logoFile") MultipartFile logoFile) throws IOException{
		
		System.out.println("surveyName="+surveyName);
		System.out.println("logoFile="+logoFile);
		
		//即使浏览器没有上传文件，logoFile也不是null,而是通过isEmpty()方法判断是否上传了文件
		boolean empty = logoFile.isEmpty();	
		System.out.println("上传的文件是否为空="+empty);
		
		String contentType = logoFile.getContentType();
		System.out.println("contentType="+contentType);
		
		InputStream inputStream = logoFile.getInputStream();
		System.out.println("inputStream="+inputStream);
		
		String name = logoFile.getName();
		System.out.println("name="+name);
		
		String originalFilename = logoFile.getOriginalFilename(); //原始的文件名称
		System.out.println("originalFilename="+originalFilename);
		
		long size = logoFile.getSize();
		System.out.println("size="+size);
		
		return "redirect:/guest/user/toLoginUI";
	}
	
	/*	<mvc:view-controller path="/guest/survey/toAddUI" view-name="guest/survey_addUI"/>
  	@RequestMapping("/guest/survey/toAddUI")
	public String toAddUI(){
		System.out.println("SurveyHandler - toAddUI...");
		return "guest/survey_addUI";
	}
	*/
}
