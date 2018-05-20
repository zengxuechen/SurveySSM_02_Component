package com.atguigu.survey.component.service.i;

import java.util.List;
import java.util.Map;

import com.atguigu.survey.entities.zhyq.TbCustFunctionLevelMap;
import com.atguigu.survey.vo.CustFunctionVo;

/**
 * @author 李小鑫
 * 2018年5月19日
 */
public interface CustFunctionLevelMapService {
	
	List<TbCustFunctionLevelMap> getFunctionListByFunctionId(Map<String,Object> map);

	Integer addFunction(TbCustFunctionLevelMap custFunctionLevelMap);

	Integer updateByPrimaryKeySelective(TbCustFunctionLevelMap custFunctionLevelMap);

	Integer deleteFunction(TbCustFunctionLevelMap custFunctionLevelMap);

	TbCustFunctionLevelMap getFunction(Map<String, Object> map);


}
