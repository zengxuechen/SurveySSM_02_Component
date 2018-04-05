package com.atguigu.survey.component.handler.zhyq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.SelectQuestionLibService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbSelectQuestionLib;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 15:25
 */
@Controller
public class SelectQuestionLibHandler {

    @Autowired
    SelectQuestionLibService selectQuestionLibService;

    /**
     * 根据题号String 返回题库List
     * @param questionIds
     * @return
     */
    @RequestMapping("guest/selectQuestionLib/queryExQuestionByIds/{paperId}/{questionIds}/{pageNoStr}/{result}")
    public String queryExQuestionByIds(@PathVariable("paperId") String paperId, @PathVariable("questionIds") String questionIds, HttpSession session, Map<String, Object> map, @PathVariable("pageNoStr") String pageNoStr, @PathVariable("result") String result){
        String[] ids = questionIds.split("@");
        List<String> idList = Arrays.asList(ids);
        List<Integer> idIntList = new ArrayList<Integer>();
        for(String id : idList){
            int i = Integer.parseInt(id);
            idIntList.add(i);
        }
        User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
        Page<TbSelectQuestionLib> page =
                selectQuestionLibService.queryExQuestionByIds(pageNoStr,idIntList);
        map.put("page",page);
        map.put("user",user);
        map.put("questionIds",questionIds);
        map.put("paperId",paperId);
        map.put("result",result);
        
        return "/zhyq/question_list";
    }


}
