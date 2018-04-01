package com.atguigu.survey.component.handler.zhyq;

import com.atguigu.survey.component.service.i.CustTestResultService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.utils.GlobalNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 17:01
 */
@Controller("custTestResult")
public class CustTestResultHandler {

    @Autowired
    CustTestResultService custTestResultService;

    @RequestMapping("/saveCustTestResult/{typeId}/{result}")
    public String saveCustTestResult(HttpSession session , @PathVariable("typeId") String typeId ,@PathVariable("result") String result){

        User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
        Integer userId = user.getUserId();

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("userId",userId);
        map.put("typeId",typeId);
        map.put("result",result);

        Integer integer = custTestResultService.saveCustTestResult(map);

        if(integer == 1){
            return "/zhyq/saveUserResult_success.jsp";
        }else{
            return "/zhyq/saveUserResult_error.jsp";
        }

    }

}
