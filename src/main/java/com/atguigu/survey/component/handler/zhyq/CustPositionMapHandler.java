package com.atguigu.survey.component.handler.zhyq;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.CustPositionMapService;
import com.atguigu.survey.entities.zhyq.TbCustPositionMap;

@Controller
public class CustPositionMapHandler {
	
	@Autowired
	CustPositionMapService custPositionMapService;
	
	@RequestMapping("/guest/custPositionMapHandler/addPositionFunction")
	public String addPositionFunction(TbCustPositionMap custPositionMap) {
		Integer count = custPositionMapService.addPositionFunction(custPositionMap);
        if(count == 1) {
        	return "zhyq/savePositionFunction_success";
        }else {
        	return "zhyq/savePositionFunction_error";
        }
		
	}
	
	@RequestMapping("/guest/custPositionMapHandler/deletePositionFunction")
	public String deletePositionFunction(TbCustPositionMap custPositionMap) {
		Integer count = custPositionMapService.deletePositionFunction(custPositionMap);
        if(count == 1) {
        	return "zhyq/deletePositionFunction_success";
        }else {
        	return "zhyq/deletePositionFunction_error";
        }
		
	}
	
	@RequestMapping("/guest/custPositionMapHandler/updatePositionFunction")
	public String updatePositionFunction(TbCustPositionMap custPositionMap) {
		Integer count = custPositionMapService.updatePositionFunction(custPositionMap);
        if(count == 1) {
        	return "zhyq/savePositionFunction_success";
        }else {
        	return "zhyq/savePositionFunction_error";
        }
		
	}
	
	@RequestMapping("/guest/custPositionMapHandler/selectPositionFunction")
	public List<TbCustPositionMap> selectPositionFunction(TbCustPositionMap custPositionMap) {
		List<TbCustPositionMap> list = new ArrayList<TbCustPositionMap>();
		List<TbCustPositionMap> list1 = custPositionMapService.selectPositionFunction(custPositionMap);
		if(list1 != null && list1.size() > 0) {
			list = list1;
		}
		return list;
        
	}

}
