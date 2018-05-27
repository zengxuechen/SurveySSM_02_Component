package com.atguigu.survey.component.handler.zhyq;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.IdGenerator.SnowflakeIdGen;
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
	public String getFunctionListByFunctionId(Map<String, Object> mv, Integer departmentId,String functionId) {
		List<TbCustFunctionLevelMap> resultList = 
				new ArrayList<TbCustFunctionLevelMap>();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("departmentId", departmentId);
		map.put("functionId", functionId);
		String level = "";
		//先判断是不是标准职能
		if(functionId != null && functionId.contains("B")) {
			List<TbFunctionLevelMap> tbFunctionLevelMapList = 
					functionLevelMapService.getStandardFunctionList(map);
			//convert转换为TbCustFunctionLevelMap返回
			List<TbCustFunctionLevelMap> convert2Cust = 
					convert2Cust(tbFunctionLevelMapList);
			if(convert2Cust !=null && convert2Cust.size() >0) {
				resultList = convert2Cust;
			}
		}else if(functionId == null ) {
			//查询所有标准职能
			List<TbFunctionLevelMap> tbFunctionLevelMapList = 
					functionLevelMapService.getAll(map);
			//convert转换为TbCustFunctionLevelMap返回
			List<TbCustFunctionLevelMap> convert2Cust = 
					convert2Cust(tbFunctionLevelMapList);
			if(convert2Cust !=null && convert2Cust.size() >0) {
				resultList = convert2Cust;
			}
		}else if(departmentId !=null && functionId != null && functionId.contains("A")) {
			List<TbCustFunctionLevelMap> functionListByFunctionId = 
					custFunctionLevelMapService.getFunctionListByFunctionId(map);
		    if(functionListByFunctionId != null && functionListByFunctionId.size() > 0) {
		    	resultList  = functionListByFunctionId;
		    }
		}
		if(resultList.size()>0) {
			level = resultList.get(0).getFunctionLevel();
		}
		mv.put("functionList", resultList);
		mv.put("level", level);
		return "zhyq/function_list";
	}
	
	/**
	 * 在部门下增加职能
	 * 李小鑫 
	 * 2018年5月19日
	 */
	public String addFunctionByDepartmentId(TbCustFunctionLevelMap custFunctionLevelMap) {
		custFunctionLevelMap.setFunctionId(SnowflakeIdGen.createFunctionId("A"));
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
			tbCustFunctionLevelMap.setFunctionId(t.getFunctionId());
			tbCustFunctionLevelMap.setFunctionContent(t.getFunctionContent());
			tbCustFunctionLevelMap.setFunctionLevel(t.getFunctionLevel());
			tbCustFunctionLevelMap.setFunctionName(t.getFunctionName());
			tbCustFunctionLevelMap.setUpFunctionName(t.getUpFunctionName());
			tbCustFunctionLevelMap.setUpId(t.getUpId());
			resultList.add(tbCustFunctionLevelMap);
		}
		
		return resultList;
		
	}
	

}
