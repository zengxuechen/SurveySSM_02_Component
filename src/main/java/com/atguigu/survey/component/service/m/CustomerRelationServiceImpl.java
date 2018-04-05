package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbCustomerRelationMapper;
import com.atguigu.survey.component.service.i.CustomerRelationService;
import com.atguigu.survey.entities.zhyq.TbCustomerRelation;
import com.atguigu.survey.vo.CustomerRelationInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:28
 */
@Service
public class CustomerRelationServiceImpl implements CustomerRelationService {

    @Autowired
    TbCustomerRelationMapper customerRelationMapper;

    public Integer saveCustomerRelationInfo(TbCustomerRelation TbCustomerRelation) {
        Integer integer = customerRelationMapper.insertSelective(TbCustomerRelation);
        return integer;
    }
}
