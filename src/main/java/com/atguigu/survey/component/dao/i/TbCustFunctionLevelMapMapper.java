package com.atguigu.survey.component.dao.i;

import java.util.List;
import java.util.Map;

import com.atguigu.survey.entities.zhyq.TbCustFunctionLevelMap;
import com.atguigu.survey.vo.CustFunctionVo;

public interface TbCustFunctionLevelMapMapper {

	List<TbCustFunctionLevelMap> getFunctionListByFunctionId(Map<String, Object> map);

	Integer insertSelective(TbCustFunctionLevelMap custFunctionLevelMap);

	Integer updateByPrimaryKeySelective(TbCustFunctionLevelMap custFunctionLevelMap);

	Integer deleteByPrimaryKey(Integer functionId);

	TbCustFunctionLevelMap getFunction(Map<String, Object> map);

}
