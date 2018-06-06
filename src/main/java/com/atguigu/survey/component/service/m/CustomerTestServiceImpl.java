package com.atguigu.survey.component.service.m;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.TbCustomerTestMapper;
import com.atguigu.survey.component.service.i.CustomerTestService;
import com.atguigu.survey.entities.zhyq.TbCustomerTest;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/15 10:05
 */
@Service
public class CustomerTestServiceImpl implements CustomerTestService {

    @Autowired
    TbCustomerTestMapper customerTestMapper;

    public Integer saveCustomerTestPaperIds(Integer userId, String testPaperIds) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("testPaperIds",testPaperIds);
        TbCustomerTest test = customerTestMapper.selectCustomerTestPaperByUesrId(userId);
        Integer count;
        if(test!=null) {
        	test.setTestPaperIds(testPaperIds);
        	count = customerTestMapper.updateByPrimaryKeySelective(test);
        }else {
        	count = customerTestMapper.saveCustomerTestPaperIds(map);
        }
        return count;
    }

    public TbCustomerTest selectCustomerTestPaperByUesrId(Integer userId) {
        return customerTestMapper.selectCustomerTestPaperByUesrId(userId);
    }
}
