package com.atguigu.survey.component.handler.zhyq;

import com.atguigu.survey.component.service.i.CustTestPaperService;
import com.atguigu.survey.component.service.i.CustomerTestService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.entities.zhyq.TbCustomerTest;
import com.atguigu.survey.utils.GlobalNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.PanelUI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/15 10:02
 */
@Controller
public class CustomerTestHandler {

    @Autowired
    CustomerTestService customerTestService;

    @Autowired
    CustTestPaperService custTestPaperService;

    @RequestMapping("manager/customerTestHandler/saveCustomerTestPaperIds/{userId}/{testPaperIds}")
    public String saveCustomerTestPaperIds(@PathVariable("userId") Integer userId, @PathVariable("testPaperIds") String testPaperIds, HttpSession session){
        User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
        Integer count = customerTestService.saveCustomerTestPaperIds(userId,testPaperIds,user);
        if(count == 1){
            return "saveTestPaperIds_success";
        }else{
            return "saveTestPaperIds_error";
        }
    }

    @RequestMapping("guest//customerTestHandler/selectCustomerTestPaperByUesrId/{userId}")
    public List<TbCustTestPaper> selectCustomerTestPaperByUesrId(@PathVariable("userId") Integer userId){
        TbCustomerTest customerTest =  customerTestService.selectCustomerTestPaperByUesrId(userId);
        String testPaperIds = customerTest.getTestPaperIds();
        String[] split = testPaperIds.split("@");
        List<String> strings = Arrays.asList(split);
        List<Integer> ids = new ArrayList<Integer>();
        for(String id : strings ){
            ids.add(Integer.parseInt(id));
        }
        List<TbCustTestPaper> resultList =  custTestPaperService.getCustTestPaperList(ids);
        return resultList;
    }

}
