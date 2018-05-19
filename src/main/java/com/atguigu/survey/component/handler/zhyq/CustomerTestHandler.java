package com.atguigu.survey.component.handler.zhyq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.CustTestPaperService;
import com.atguigu.survey.component.service.i.CustTestResultService;
import com.atguigu.survey.component.service.i.CustomerTestService;
import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.entities.zhyq.TbCustomerTest;

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
    CustTestResultService custTestResultService;

    @Autowired
    CustTestPaperService custTestPaperService;

    @RequestMapping("manager/customerTestHandler/saveCustomerTestPaperIds")
    public String saveCustomerTestPaperIds(Integer userId, String testPaperIds){
    	testPaperIds = testPaperIds.replace(",", "@");
        customerTestService.saveCustomerTestPaperIds(userId,testPaperIds);
        return "redirect:/manager/customerRelationHandler/getAllUser";
    }

    @RequestMapping("guest/customerTestHandler/selectCustomerTestPaperByUesrId/{userId}")
    public String selectCustomerTestPaperByUesrId(Map<String, Object> map, @PathVariable("userId") Integer userId){
        
    	TbCustomerTest customerTest =  customerTestService.selectCustomerTestPaperByUesrId(userId);
    	if(customerTest==null) {
            return "zhyq/paper_list";
        }
    	String testPaperIds = customerTest.getTestPaperIds();
        String[] split = testPaperIds.split("@");
        List<String> strings = Arrays.asList(split);
        List<Integer> ids = new ArrayList<Integer>();
        for(String id : strings ){
        	Map<String, Object> map1 = new HashMap<String, Object>();
        	map1.put("userId", userId);
        	map1.put("paperId", Integer.valueOf(id));
        	Integer count = custTestResultService.queryResultByUserIdAndPaperId(map1);
        	if(count<=0) {
        		ids.add(Integer.parseInt(id));
        	}
        }
        List<TbCustTestPaper> resultList = new ArrayList<TbCustTestPaper>();
        if(ids.size()> 0) {
        	resultList = custTestPaperService.getCustTestPaperList(ids);
        }
        map.put("paperList", resultList);
        return "zhyq/paper_list";
    }



}
