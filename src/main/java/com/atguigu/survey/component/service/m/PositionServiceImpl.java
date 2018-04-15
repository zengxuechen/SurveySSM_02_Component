package com.atguigu.survey.component.service.m;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.TbPositionMapper;
import com.atguigu.survey.component.service.i.PositionService;
import com.atguigu.survey.entities.zhyq.TbPosition;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:49
 */
@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    TbPositionMapper positionMapper;

    public Integer savePositionInfo(TbPosition position) {
        Integer integer = positionMapper.insertSelective(position);
        return integer;
    }

    public List<TbPosition> getAll() {
        return positionMapper.getAll();
    }
}
