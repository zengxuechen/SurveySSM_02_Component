package com.atguigu.survey.component.handler.zhyq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.CustTestResultService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.utils.GlobalNames;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 17:01
 */
@Controller
public class CustTestResultHandler {

    @Autowired
    CustTestResultService custTestResultService;

    @RequestMapping("guest/custTestResult/saveCustTestResult/{typeCode}/{paperId}/{result}")
    public String saveCustTestResult(HttpSession session , @PathVariable("typeCode") String typeCode, @PathVariable("paperId") String paperId ,@PathVariable("result") String result){

        User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
        Integer userId = user.getUserId();

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("userId",userId);
        map.put("paperId",paperId);
        map.put("result",result);
        map.put("typeCode",typeCode);

        Integer integer = custTestResultService.saveCustTestResult(map);

        if(integer == 1){
            return "/zhyq/saveUserResult_success";
        }else{
            return "/zhyq/saveUserResult_error";
        }

    }
    
    /**
     * 根据测评类型码查询出所有试卷类型集合
     * @param typeCode
     */
    @RequestMapping("guest/custTestResult/queryResultByTypeCode/{typeCode}")
    public String queryResultByTypeCode(@PathVariable("typeCode") String typeCode, Map<String,Object> map, HttpSession session){
        

         User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
         
         Map<String, Object> map1 = new HashMap<String,Object>();
         map1.put("userId",user.getUserId());
         map1.put("typeCode",typeCode);
         List<Map<String, Object>> list=
        		 custTestResultService.queryResultByTypeCode(map1);
         
         map.put("user",user);
         map.put("typeCode",typeCode);
         map.put("list",list);

         return "zhyq/result_type";
    }

}
