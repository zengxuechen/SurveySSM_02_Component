package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbCustomerTestMapper;
import com.atguigu.survey.component.service.i.CustomerTestService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbCustomerTest;
import com.atguigu.survey.utils.GlobalNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/15 10:05
 */
@Service
public class CustomerTestServiceImpl implements CustomerTestService {

    @Autowired
    TbCustomerTestMapper customerTestMapper;

    public Integer saveCustomerTestPaperIds(Integer userId, String testPaperIds,User user) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("testPaperIds",testPaperIds);
        map.put("user",user.getUserId().toString());
        Integer count = customerTestMapper.saveCustomerTestPaperIds(map);
        return count;
    }

    public TbCustomerTest selectCustomerTestPaperByUesrId(Integer userId) {
        return customerTestMapper.selectCustomerTestPaperByUesrId(userId);
    }
}
