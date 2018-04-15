package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.zhyq.TbPosition;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:48
 */
public interface PositionService {

	public Integer savePositionInfo(TbPosition position);

    List<TbPosition> getAll();
}
