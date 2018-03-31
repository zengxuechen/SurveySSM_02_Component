package com.atguigu.survey.component.handler.manager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.survey.component.service.i.ResService;
import com.atguigu.survey.entities.manager.Res;

@SuppressWarnings("all")
@Controller
public class ResHandler {

	@Autowired
	private ResService resService ;
	
	
	@RequestMapping("/manager/res/toggleStatus")
	@ResponseBody
	public Map<String,String> toggleStatus(@RequestParam("resId") Integer resId,HttpServletResponse response){
		
		Map<String,String> map = new HashMap<String,String>();
		
		try {
			boolean finalStatus = resService.updateStatus(resId);
			map.put("finalStatus", finalStatus+"");
			map.put("resultMessage", "success");
		} catch (Exception e) {
			map.put("resultMessage", "failed");
		}
		//response.setContentType("application/json;charset=UTF-8");
		return map;
	}
	
	@RequestMapping("/manager/res/batchDelete")
	public String batchDelete(@RequestParam(value="resList",required=false) List<Integer> resList){
		
		//如果有选中，不为空，那么就不再进行删除了
		if(resList!=null && resList.size()>0){
			resService.batchDelete(resList);
		}
		
		return "redirect:/manager/res/showAllRes";
	}
	
	//查询资源列表
	@RequestMapping("/manager/res/showAllRes")
	public String showAllRes(Map map){
		List<Res> resList = resService.queryAllRes();
		map.put("resList", resList);
		return "manager/res_showList";
	}
	
}
