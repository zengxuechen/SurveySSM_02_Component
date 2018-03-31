package com.atguigu.survey.tag;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.utils.GlobalNames;

public class RedisplayTag extends SimpleTagSupport{

	private String paramName ;
	private int questionType ;
	private String currentValue ; //简答题无法提供这个值
	
	@Override
	public void doTag() throws JspException, IOException {
		
		//准备工作
		PageContext pageContext = (PageContext) getJspContext();//9个内建对象都可以通过它来获取
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		HttpSession session = pageContext.getSession();
		
		
		//从域中获取相关数据
		Map<Integer,Map<String,String[]>> allBagMap = (Map<Integer, Map<String, String[]>>) session.getAttribute(GlobalNames.ALL_BAG_MAP);
		Bag bag = (Bag)request.getAttribute(GlobalNames.CURRENT_BAG);
		
		//从allBagMap中获取bagId对应的paramMap
		Integer bagId = bag.getBagId();
		Map<String, String[]> paramMap = allBagMap.get(bagId);
		
		//表示这个包裹之前没有来过
		if(paramMap == null ){
			return ;
		}
		
		//根据paramName从paramMap获取paramValues
		String[] paramValues = paramMap.get(paramName);
		
		//以前来过这个包裹，但是没有提交数据
		if(paramValues == null || paramValues.length == 0){
			return ;
		}
		
		if(questionType == 0 || questionType == 1){
			//选择题
			//检查currentValue是否在paramValues中是否存在
			List<String> paramList = Arrays.asList(paramValues);
			
			if(paramList.contains(currentValue)){
				out.print("checked='checked'");
			}
			return ;
		}else if(questionType == 2){
			
			//简答题
			//检查paramVlaues数组是否有值
			out.print(paramValues[0]);
			
		}
		
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}
	
}
