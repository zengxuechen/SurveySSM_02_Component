package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.TbCustFunctionLevelMapMapper;
import com.atguigu.survey.component.service.i.CustFunctionLevelMapService;
import com.atguigu.survey.entities.zhyq.TbCustFunctionLevelMap;
import com.atguigu.survey.vo.CustFunctionVo;

/**
 * @author 李小鑫
 * 2018年5月19日
 */
@Service
public class CustFunctionLevelMapServiceImpl implements CustFunctionLevelMapService{
	
	@Autowired
	TbCustFunctionLevelMapMapper custFunctionLevelMapMapper;

	public List<TbCustFunctionLevelMap> getFunctionListByFunctionId(Map<String, Object> map) {
		
		List<TbCustFunctionLevelMap> list = 
				custFunctionLevelMapMapper.getFunctionListByFunctionId(map);
		
		return list;
	}

	public Integer addFunction(TbCustFunctionLevelMap custFunctionLevelMap) {
		Integer addFunction = 
				custFunctionLevelMapMapper.insertSelective(custFunctionLevelMap);
		return addFunction;
	}

	public Integer updateByPrimaryKeySelective(TbCustFunctionLevelMap custFunctionLevelMap) {
		Integer updateByPrimaryKeySelective =
				custFunctionLevelMapMapper.updateByPrimaryKeySelective(custFunctionLevelMap);
		return updateByPrimaryKeySelective;
	}

	public Integer deleteFunction(TbCustFunctionLevelMap custFunctionLevelMap) {
		Integer deleteByPrimaryKey = 
				custFunctionLevelMapMapper.deleteByPrimaryKey(custFunctionLevelMap.getFunctionId());
		return deleteByPrimaryKey;
	}

	public TbCustFunctionLevelMap getFunction(Map<String, Object> map) {
		TbCustFunctionLevelMap result = custFunctionLevelMapMapper.getFunction(map);
		return result;
	}

}
