package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.TbCustPositionMapMapper;
import com.atguigu.survey.component.service.i.CustPositionMapService;
import com.atguigu.survey.entities.zhyq.TbCustPositionMap;

@Service
public class CustPositionMapServiceImpl implements CustPositionMapService{
	
	@Autowired
	TbCustPositionMapMapper tbCustPositionMapMapper;

	public Integer addPositionFunction(TbCustPositionMap custPositionMap) {
		Integer count = tbCustPositionMapMapper.insertSelective(custPositionMap);
		return count;
	}

	public Integer deletePositionFunction(TbCustPositionMap custPositionMap) {
		Integer count =tbCustPositionMapMapper.deleteByPrimaryKey(custPositionMap);
		return count;
	}

	public Integer updatePositionFunction(TbCustPositionMap custPositionMap) {
		Integer count =tbCustPositionMapMapper.updateByPrimaryKeySelective(custPositionMap);
		return count;
	}

	public List<TbCustPositionMap> selectPositionFunction(TbCustPositionMap custPositionMap) {
		List<TbCustPositionMap> list = tbCustPositionMapMapper.selectPositionFunction(custPositionMap);
		return list;
	}

}
