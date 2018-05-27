package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.TbFunctionLevelMapMapper;
import com.atguigu.survey.component.service.i.FunctionLevelMapService;
import com.atguigu.survey.entities.zhyq.TbFunctionLevelMap;

@Service
public class FunctionLevelMapServiceImpl implements FunctionLevelMapService{
	
	@Autowired
	TbFunctionLevelMapMapper tbFunctionLevelMapMapper;

	public List<TbFunctionLevelMap> getStandardFunctionList(Map<String, Object> map) {
		List<TbFunctionLevelMap> result = tbFunctionLevelMapMapper.getStandardFunctionList(map);
		return result;
	}

	public List<TbFunctionLevelMap> getAll(Map<String, Object> map) {
		List<TbFunctionLevelMap> result = tbFunctionLevelMapMapper.getAll(map);
		return result;
	}
	
	

}
