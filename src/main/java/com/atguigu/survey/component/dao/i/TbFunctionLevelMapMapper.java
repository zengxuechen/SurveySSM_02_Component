package com.atguigu.survey.component.dao.i;

import java.util.List;
import java.util.Map;

import com.atguigu.survey.entities.zhyq.TbFunctionLevelMap;

/**
 * @author 李小鑫
 * 2018年5月19日
 */
public interface TbFunctionLevelMapMapper {

	List<TbFunctionLevelMap> getStandardFunctionList(Map<String, Object> map);

}
