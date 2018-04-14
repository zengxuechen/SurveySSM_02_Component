package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbCustomerRelation;
import com.atguigu.survey.vo.CustomerDetailVo;
import com.atguigu.survey.vo.CustomerRelationInfoVo;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 13:13
 */
public interface TbCustomerRelationMapper {

    Integer insertSelective(TbCustomerRelation customerRelation);

    CustomerDetailVo getRelationInfoByUserId(int i);

    List<CustomerRelationInfoVo> getAllUserInfoByDepartmentId(Integer departmentId);
}
