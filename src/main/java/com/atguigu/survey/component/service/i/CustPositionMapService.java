package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.zhyq.TbCustPositionMap;

public interface CustPositionMapService {

	Integer addPositionFunction(TbCustPositionMap custPositionMap);

	Integer deletePositionFunction(TbCustPositionMap custPositionMap);

	Integer updatePositionFunction(TbCustPositionMap custPositionMap);

	List<TbCustPositionMap> selectPositionFunction(TbCustPositionMap custPositionMap);

}
