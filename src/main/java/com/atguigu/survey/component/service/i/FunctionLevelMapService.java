package com.atguigu.survey.component.service.i;

import java.util.List;
import java.util.Map;

import com.atguigu.survey.entities.zhyq.TbCustFunctionLevelMap;
import com.atguigu.survey.entities.zhyq.TbFunctionLevelMap;

/**
 * @author 李小鑫
 * 2018年5月20日
 */
public interface FunctionLevelMapService {

	List<TbFunctionLevelMap> getStandardFunctionList(Map<String, Object> map);

}
