package com.atguigu.survey.component.handler.guest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.QuestionService;
import com.atguigu.survey.entities.guest.Question;

@SuppressWarnings("all")
@Controller
public class QuestionHandler {

	@Autowired
	private QuestionService questionService ; 
	
	@RequestMapping("/guest/question/updateQuestion")
	public String updateQuestion(Question question,@RequestParam("surveyId") Integer surveyId){
		questionService.updateEntity(question);
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	//璺宠浆鍒颁慨鏀归〉闈�
	@RequestMapping("/guest/question/toEidtUI/{questionId}/{surveyId}")
	public String toEditUI(@PathVariable("questionId") Integer questionId,
			@PathVariable("surveyId") Integer surveyId,
			Map map){
		
		Question question = questionService.getEntity(questionId);
		map.put("question", question);
		
		Map<String,String> questionTypeMap = new HashMap<String,String>();
		questionTypeMap.put("0", "鍗曢�夐");
		questionTypeMap.put("1", "澶氶�夐");
		questionTypeMap.put("2", "绠�绛旈");
		map.put("questionTypeMap", questionTypeMap);
		
		return "guest/question_editUI";
	}
	
	
	//鍒犻櫎闂
	@RequestMapping("/guest/question/removeQuestion/{qquestionId}/{surveyId}")
	public String removeQuestion(@PathVariable("qquestionId") Integer questionId,@PathVariable("surveyId") Integer surveyId){
		questionService.removeEntityById(questionId);
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}
	
	//淇濆瓨闂
	@RequestMapping("/guest/question/saveQuestion")
	public String saveQuestion(Question question,@RequestParam("surveyId") Integer surveyId){
		
		questionService.saveEntity(question);
		
		return "redirect:/guest/survey/toDesignUI/"+surveyId;
	}

	//璺宠浆鍒版坊鍔犻棶棰橀〉闈�
	@RequestMapping("/guest/question/toAddUI/{bagId}/{surveyId}")
	public String toAddUI(@PathVariable("bagId") Integer bagId,@PathVariable("surveyId") Integer surveyId){
		
		return "guest/question_addUI";
	}
	
}
