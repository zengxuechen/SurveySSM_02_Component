package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbCustomerTest;

import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/15 9:57
 */
public interface TbCustomerTestMapper {
    Integer saveCustomerTestPaperIds(Map<String,Object> map);

    TbCustomerTest selectCustomerTestPaperByUesrId(Integer userId);
}
