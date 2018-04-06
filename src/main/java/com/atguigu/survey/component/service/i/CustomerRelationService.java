package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbCustomerRelation;
import com.atguigu.survey.vo.CustomerDetailVo;
import com.atguigu.survey.vo.CustomerRelationInfoVo;
/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:27
 */
public interface CustomerRelationService {

    Integer saveCustomerRelationInfo(TbCustomerRelation TbCustomerRelation);

    //List<> getUserInfoListByDepartmentId(Integer departmentId);

    CustomerDetailVo getRelationInfoByUserId(int i);
}
