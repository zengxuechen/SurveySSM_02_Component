package com.atguigu.survey.component.handler.guest;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.BagService;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.BagOrderDuplicateException;
import com.atguigu.survey.e.RemoveBagFailedException;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.DataProcessUtils;
import com.atguigu.survey.utils.GlobalMessage;
import com.atguigu.survey.utils.GlobalNames;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SuppressWarnings("all")
@Controller
public class BagHandler {

	@Autowired
	private BagService bagService ;
	
	@Autowired
	private SurveyService surveyService ;
	
	
	//复制包裹
	@RequestMapping("/guest/bag/copyToHere/{bagId}/{surveyId}")
	public String copyToHere(@PathVariable("bagId") Integer bagId ,
			@PathVariable("surveyId") Integer surveyId){
		
		bagService.updateRelationshipByCopy(bagId,surveyId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	
	//移动包裹
	@RequestMapping("/guest/bag/moveToHere/{bagId}/{surveyId}")
	public String moveToHere(@PathVariable("bagId") Integer bagId ,
			@PathVariable("surveyId") Integer surveyId){
		
		bagService.updateRelationshipByMove(bagId,surveyId);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	//显示移动/复制包裹列表页面
	@RequestMapping("/guest/bag/toMoveOrCopyPage/{bagId}/{surveyId}")
	public String toMoveOrCopyPage(
			@PathVariable("bagId") Integer bagId ,
			@PathVariable("surveyId") Integer surveyId ,
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			HttpSession session,
			Map map ){
			
		User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
		
		Page<Survey> page = surveyService.getMyUnCompletedSurveyPage(user, pageNoStr);
		 
		map.put("page", page);
		 
		return "guest/bag_moveOrCopyPage";
	}
	
	
	//包裹排序
	@RequestMapping("/guest/bag/doAdjust")
	public String doAdjust(
			@RequestParam("surveyId") Integer surveyId,
			@RequestParam("bagIdList") List<Integer> bagIdList,
			@RequestParam("bagOrderList")List<Integer> bagOrderList,
			HttpServletRequest request) {

		boolean checkBagOrderDuplicate = DataProcessUtils.checkBagOrderDuplicate(bagOrderList);
		
		if(!checkBagOrderDuplicate){
			List<Bag> bagList = bagService.getBagListBySurveyId(surveyId);
			//map.put("bagList", bagList);//不能使用map携带数据
			request.setAttribute("bagList",bagList);
			request.setAttribute("surveyId", surveyId);//surveyId不是通过@PathVariable接收的，必须手动存放到request域
			throw new BagOrderDuplicateException(GlobalMessage.BAG_ORDER_DUPLICATE);
		}
		
		bagService.batchUpdateBagOrder(bagIdList,bagOrderList);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	
	//跳转到调整包裹顺序表单
	@RequestMapping("/guest/bag/toAdjustUI/{surveyId}")
	public String toAdjustUI(@PathVariable("surveyId") Integer surveyId,Map map){		
		List<Bag> bagList = bagService.getBagListBySurveyId(surveyId);
		map.put("bagList", bagList);
		return "guest/bag_adjustUI";
	}
	
	
	
	
	/* 尽量避免使用，如果更新实体类有相关需要可以尝试使用其他办法解决。
	 * 其他办法1：让表单隐藏域提交不需要修改的数据
	 * 其他办法2：使用HQL或SQL进行精确的更新相应的字段	
	 * Struts2中是使用prepare前缀方法来定制操作的。而@ModelAttribute所修饰方法是为所有处理请求方法定制操作的。
	@ModelAttribute
	public void getBag(){
		
	}*/
	
	//bagOrder字段被更新为null啦，数据丢啦
	@RequestMapping("/guest/bag/updateBag")
	public String updateBag(Bag bag){
		//bagService.updateEntity(bag);
		bagService.updateBag(bag);
		return "redirect:/guest/survey/toDesignUI/"+bag.getSurveyId();
	}
	
	
	//更新包裹-表单回显
	@RequestMapping("/guest/bag/editBagUI/{bagId}")
	public String editBagUI(@PathVariable("bagId") Integer bagId,Map map){
		Bag bag = bagService.getEntity(bagId);
		map.put("bag", bag);
		return "guest/bag_editUI";
	}
	
	
	//删除包裹
	@RequestMapping("/guest/bag/removeBag/{bagId}/{surveyId}")
	public String removeBag(@PathVariable("bagId") Integer bagId,@PathVariable("surveyId") Integer surveyId){
		
		try {
			bagService.removeEntityById(bagId);
		} catch (Exception e) {
			e.printStackTrace();
			
			Throwable cause = e.getCause();
			
			if(cause != null && cause instanceof MySQLIntegrityConstraintViolationException) {
				throw new RemoveBagFailedException(GlobalMessage.REMOVE_BAG_FAILED);
			}
			
		}
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	//添加包裹
	@RequestMapping("/guest/bag/saveBag")
	public String saveBag(Bag bag){
		//临时状态
		
		//bagService.saveEntity(bag); //持久化状态
		bagService.saveBag(bag);
		
		//游离状态
		//bag.setBagOrder(bag.getBagId()); //游离状态的对象修改属性值，不会自动更新

		return "redirect:/guest/survey/toDesignUI/"+bag.getSurveyId();
	}
	
	//跳转添加包裹页面
	@RequestMapping("/guest/bag/addUI/{surveyId}")
	public String addUI(@PathVariable("surveyId") Integer surveyId){
		//@PathVariable匹配的值，自动存放到request域中
		return "guest/bag_addUI";
	}
	
}
