package com.atguigu.survey.component.dao.i;

import java.util.List;

import com.atguigu.survey.entities.zhyq.TbPosition;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 13:08
 */
public interface TbPositionMapper {

	List<TbPosition> getAll();

	Integer insertSelective(TbPosition position);
	
}
