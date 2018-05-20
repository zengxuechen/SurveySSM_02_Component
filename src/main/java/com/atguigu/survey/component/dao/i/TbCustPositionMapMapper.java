package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.entities.zhyq.TbCustPositionMap;

/**
 * @author 李小鑫
 * 2018年5月19日
 */
public interface TbCustPositionMapMapper {

	Integer insertSelective(TbCustPositionMap custPositionMap);

	Integer deleteByPrimaryKey(TbCustPositionMap custPositionMap);

	Integer updateByPrimaryKeySelective(TbCustPositionMap custPositionMap);

	List<TbCustPositionMap> selectPositionFunction(TbCustPositionMap custPositionMap);

}
