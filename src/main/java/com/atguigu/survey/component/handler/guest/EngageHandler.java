package com.atguigu.survey.component.handler.guest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;

@Controller
public class EngageHandler { //参与调查

	@Autowired
	private SurveyService surveyService ;
	
	@Autowired
	private EngageService engageService ;
	
	//整理后代码：完成四个按钮的功能。
	@RequestMapping("/guest/engage/engage")
	public String engage(@RequestParam("currentIndex") Integer currentIndex,
			HttpServletRequest request,
			@RequestParam("bagId") Integer bagId){

		//一 准备工作
		//【1】从session域中获取allBagMap
		HttpSession session = request.getSession();
		Map<Integer, Map<String,String[]>> allBagMap = (Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
				
		//【2】以当前包裹的id为键，以当前请求参数parameterMap为值存放到allBagMap
		Map<String,String[]> parameterMap = request.getParameterMap();
		
		//【3】合并答案
		//①创建一个新的集合用来封装当前包裹的请求参数数据
		Map<String,String[]> newParameterMap = new HashMap(parameterMap);
		
		//②以当前包裹的id为键，以当前请求 parameterMap 为值放入 allBagMap
		allBagMap.put(bagId, newParameterMap);
		
		//【4】从Session域中获取Bag集合
		List<Bag> bagList = (List<Bag>)session.getAttribute(GlobalNames.BAG_LIST);
		
		//二 包裹导航
		//1.计算索引值
		int newIndex = 0 ;
		if(parameterMap.containsKey("submit_prev")){
			newIndex = currentIndex - 1 ;
		}
		if(parameterMap.containsKey("submit_next")){
			newIndex = currentIndex + 1 ;
		}

		if(parameterMap.containsKey("submit_prev") || parameterMap.containsKey("submit_next")){

			//获取下一个包裹
			Bag bag = bagList.get(newIndex);
			
			//将下一个包裹存放到request域
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			//将下一个包裹的索引存放到request域中，也就变成了当前包裹的索引
			request.setAttribute(GlobalNames.CURRENT_INDEX, newIndex);
			
			return "guest/engage_engage";
		}

		//三 完成和放弃
		//1.完成
		if(parameterMap.containsKey("submit_done")){
			
			Survey survey = (Survey)session.getAttribute(GlobalNames.CURRENT_SURVEY);
			Integer surveyId = survey.getSurveyId();
			
			//2.解析并保存答案
			engageService.parseAndSave(allBagMap,surveyId);
		}
		
		//3.不管是完成还是放弃，都需要清理Session域
		session.removeAttribute(GlobalNames.CURRENT_SURVEY);
		session.removeAttribute(GlobalNames.BAG_LIST);
		session.removeAttribute(GlobalNames.ALL_BAG_MAP);
		session.removeAttribute(GlobalNames.LAST_INDEX);
		
		return "redirect:/index.jsp";
	}
	
	
	/* 整理前代码：完成四个按钮的功能。
  	@RequestMapping("/guest/engage/engage")
	public String engage(@RequestParam("currentIndex") Integer currentIndex,
			HttpServletRequest request,
			@RequestParam("bagId") Integer bagId){
		
		HttpSession session = request.getSession();
		
		//【1】从session域中获取allBagMap
		Map<Integer, Map<String,String[]>> allBagMap = (Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		
		//【2】获取当前包裹的 id
		//@RequestParam("bagId") Integer bagId
		
		//【3】以当前包裹的id为键，以当前请求参数parameterMap为值存放到allBagMap
		Map<String,String[]> parameterMap = request.getParameterMap();
		System.out.println(parameterMap.hashCode());
		
		Map<String,String[]> newParameterMap = new HashMap(parameterMap);
		
		//allBagMap.put(bagId, parameterMap);
		allBagMap.put(bagId, newParameterMap);
		
		//===================================================================
		//测试提交的数据：
		
		//Set<Entry<Integer, Map<String, String[]>>> entrySet = allBagMap.entrySet();
		
		Set<Integer> keySet = allBagMap.keySet();
		for (Integer key : keySet) {
			System.out.println("-----------------------------------");
			Map<String, String[]> paramMap = allBagMap.get(key);
			Set<String> paramValues = paramMap.keySet();
			for (String paramKey : paramValues) {
				String[] values = paramMap.get(paramKey);
				
				System.out.println(paramKey + " = " + Arrays.asList(values));
			}
		}

		//===================================================================

		if(parameterMap.containsKey("submit_prev")){
			//从Session域中获取Bag集合
			List<Bag> bagList = (List<Bag>)session.getAttribute(GlobalNames.BAG_LIST);
		
			//计算索引
			int prevIndex = currentIndex - 1 ;
			
			//获取下一个包裹
			Bag bag = bagList.get(prevIndex);
			
			//将下一个包裹存放到request域
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			//将下一个包裹的索引存放到request域中，也就变成了当前包裹的索引
			request.setAttribute(GlobalNames.CURRENT_INDEX, prevIndex);
		}
		
		if(parameterMap.containsKey("submit_next")){
			
			//从Session域中获取Bag集合
			List<Bag> bagList = (List<Bag>)session.getAttribute(GlobalNames.BAG_LIST);
		
			//计算索引
			int nextIndex = currentIndex + 1 ;
			
			//获取下一个包裹
			Bag bag = bagList.get(nextIndex);
			
			//将下一个包裹存放到request域
			request.setAttribute(GlobalNames.CURRENT_BAG, bag);
			
			//将下一个包裹的索引存放到request域中，也就变成了当前包裹的索引
			request.setAttribute(GlobalNames.CURRENT_INDEX, nextIndex);
		}
		
		if(parameterMap.containsKey("submit_quit")){
			//放弃，需要清理Session域
			session.removeAttribute(GlobalNames.CURRENT_SURVEY);
			session.removeAttribute(GlobalNames.BAG_LIST);
			session.removeAttribute(GlobalNames.ALL_BAG_MAP);
			session.removeAttribute(GlobalNames.LAST_INDEX);
		}
		
		if(parameterMap.containsKey("submit_done")){
			
			Survey survey = (Survey)session.getAttribute(GlobalNames.CURRENT_SURVEY);
			Integer surveyId = survey.getSurveyId();
			
			engageService.parseAndSave(allBagMap,surveyId);
			
			return "redirect:/index.jsp";
		}
		
		return "guest/engage_engage";
	}*/
	
	
	//显示参与调查的包裹导航页面
	@RequestMapping("/guest/engage/entry/{surveyId}")
	public String entry(
			@PathVariable("surveyId") Integer surveyId,
			HttpServletRequest request,
			HttpSession session){
		
		//【1】服务于整个包裹过程的操作
		//1) 根据surveyId查询Survey对象
		//Survey survey = surveyService.getEntity(surveyId);
		Survey survey = surveyService.getSurveyDeeply(surveyId);
		//2) 将Survey对象保存到Session域
		session.setAttribute(GlobalNames.CURRENT_SURVEY, survey);
		//3) 从Survey对象中获取包裹的集合:Set<Bag>
		Set<Bag> bagSet = survey.getBagSet();
		//4) 将Set<Bag>转换为List<Bag>集合
		List<Bag> bagList = new ArrayList<Bag>(bagSet);
		//5) 将List<Bag>保存到Session域
		session.setAttribute(GlobalNames.BAG_LIST, bagList);
		//6) 创建allBagMap对象
		Map<Integer,Map<String,String[]>> allBagMap = new HashMap<Integer,Map<String,String[]>>();
		//7) 将allBagMap对象保存到Session域
		session.setAttribute(GlobalNames.ALL_BAG_MAP, allBagMap);
		//8) 将List<Bag>的最后一个lastIndex的索引也保存到Session域中
		int lastIndex = bagList.size()-1;
		session.setAttribute(GlobalNames.LAST_INDEX, lastIndex);
		//【2】服务于第一个包裹的操作
		//1) 获取List<Bag>的第一个元素Bag对象
		Bag bag = bagList.get(0);
		//2) 将Bag对象保存到request域中
		request.setAttribute(GlobalNames.CURRENT_BAG, bag);
		//3) 将当前Bag的索引0保存到request域中
		request.setAttribute(GlobalNames.CURRENT_INDEX, 0);
		return "guest/engage_engage";
	}
	
	//参与调查列表显示，显示所有有效的调查
	@RequestMapping("/guest/engage/showAllAvailableSurvey")
	public String showAllAvailableSurvey(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			Map<String,Object> map){
		
		Page<Survey> page = surveyService.getAllAvailableSurvey(pageNoStr);
		
		map.put("page", page);
		
		return "guest/engage_list";
	}
	
}
