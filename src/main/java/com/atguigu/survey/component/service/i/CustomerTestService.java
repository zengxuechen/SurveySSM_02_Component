package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbCustomerTest;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/15 10:05
 */
public interface CustomerTestService {
    Integer saveCustomerTestPaperIds(Integer userId, String testPaperIds);

    TbCustomerTest selectCustomerTestPaperByUesrId(Integer userId);
}
