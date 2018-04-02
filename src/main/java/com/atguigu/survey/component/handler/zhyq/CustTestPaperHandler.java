package com.atguigu.survey.component.handler.zhyq;

import com.atguigu.survey.component.service.i.CustTestPaperService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.entities.zhyq.TbSelectQuestionLib;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 13:10
 */
@Controller("/custTestPaper")
public class CustTestPaperHandler {

    @Autowired
    CustTestPaperService custTestPaperService;

    /**
     * 根据测评类型码查询出所有试卷类型集合
     * @param typeCode
     */
    @RequestMapping("/queryPaperTypeByCode/{typeCode}/{pageNoStr}")
    public String queryPaperTypeByCode(@PathVariable("typeCode") String typeCode, Map map, HttpSession session, String pageNoStr){
         Page<TbCustTestPaper> page =
                custTestPaperService.queryPaperTypeByCode(pageNoStr,typeCode);

         User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);

         map.put("page",page);
         map.put("user",user);

         return "/zhyq/paper_type.jsp";
    }


}
