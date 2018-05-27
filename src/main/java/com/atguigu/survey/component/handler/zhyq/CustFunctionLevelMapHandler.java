package com.atguigu.survey.component.handler.zhyq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.CustFunctionLevelMapService;
import com.atguigu.survey.component.service.i.FunctionLevelMapService;
import com.atguigu.survey.entities.zhyq.TbCustFunctionLevelMap;
import com.atguigu.survey.entities.zhyq.TbFunctionLevelMap;
import com.atguigu.survey.vo.CustFunctionVo;

/**
 * @author 李小鑫
 * 2018年5月19日
 */
@Controller
public class CustFunctionLevelMapHandler {
	
	@Autowired
	CustFunctionLevelMapService custFunctionLevelMapService;
	
	@Autowired
	FunctionLevelMapService functionLevelMapService;
	
	/**
	 * 获取职能List
	 * 李小鑫 
	 * 2018年5月19日
	 */
	@RequestMapping("guest/custFunctionLevelMapHandler/getFunctionListByFunctionId")
	public List<TbCustFunctionLevelMap> getFunctionListByFunctionId(Integer departmentId,String functionId) {
		List<TbCustFunctionLevelMap> resultList = 
				new ArrayList<TbCustFunctionLevelMap>();

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departmentId", departmentId);
		map.put("functionId", functionId);
		//先判断是不是标准职能
		TbCustFunctionLevelMap result = custFunctionLevelMapService.getFunction(map);
		if(result != null) { //判空
			if(result.getStandardId() != null) {
				List<TbFunctionLevelMap> tbFunctionLevelMapList = 
						functionLevelMapService.getStandardFunctionList(map);
				//convert转换为TbCustFunctionLevelMap返回
				List<TbCustFunctionLevelMap> convert2Cust = 
						convert2Cust(tbFunctionLevelMapList);
				if(convert2Cust !=null && convert2Cust.size() >0) {
					resultList = convert2Cust;
				}
			}else {
				List<TbCustFunctionLevelMap> functionListByFunctionId = 
						custFunctionLevelMapService.getFunctionListByFunctionId(map);
			    if(functionListByFunctionId != null && functionListByFunctionId.size() > 0) {
			    	resultList  = functionListByFunctionId;
			    }
			}
		}
		return resultList;
	}
	
	/**
	 * 在部门下增加职能
	 * 李小鑫 
	 * 2018年5月19日
	 */
	public String addFunctionByDepartmentId(TbCustFunctionLevelMap custFunctionLevelMap) {
		Integer addFunction = custFunctionLevelMapService.addFunction(custFunctionLevelMap);
		if(addFunction==1) {
			return "zhyq/saveFunction_success";
		}else {
			return "zhyq/saveFunction_error";
		}
	}
	
	/**
	 * 在部门下修改职能
	 * 李小鑫 
	 * 2018年5月19日
	 */
	public String updateFunction(TbCustFunctionLevelMap custFunctionLevelMap) {
		Integer addFunction = custFunctionLevelMapService.updateByPrimaryKeySelective(custFunctionLevelMap);
		if(addFunction==1) {
			return "zhyq/saveFunction_success";
		}else {
			return "zhyq/saveFunction_error";
		}
	}
	
	/**
	 * 在部门下删除职能
	 * 李小鑫 
	 * 2018年5月19日
	 */
	public String deleteFunction(TbCustFunctionLevelMap custFunctionLevelMap) {
		Integer addFunction = custFunctionLevelMapService.deleteFunction(custFunctionLevelMap);
		if(addFunction==1) {
			return "zhyq/deleteFunction_success";
		}else {
			return "zhyq/deleteFunction_error";
		}
	}
	
	private List<TbCustFunctionLevelMap> convert2Cust(List<TbFunctionLevelMap> list){
		List<TbCustFunctionLevelMap> resultList = 
				new ArrayList<TbCustFunctionLevelMap>();
		for (TbFunctionLevelMap t : list) {
			TbCustFunctionLevelMap tbCustFunctionLevelMap = new TbCustFunctionLevelMap();
			tbCustFunctionLevelMap.setFunctionContent(t.getFunctionContent());
			tbCustFunctionLevelMap.setFunctionLevel(t.getFunctionLevel());
			tbCustFunctionLevelMap.setFunctionName(t.getFunctionName());
			tbCustFunctionLevelMap.setUpFunctionName(t.getUpFunctionName());
			tbCustFunctionLevelMap.setUpId(t.getUpId());
			tbCustFunctionLevelMap.setStandardId(t.getId());
			resultList.add(tbCustFunctionLevelMap);
		}
		
		return resultList;
		
	}
	

}