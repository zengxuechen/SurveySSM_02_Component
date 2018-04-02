package com.atguigu.survey.component.handler.zhyq;

import com.atguigu.survey.component.service.i.CustTestPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 13:10
 */
@Controller("/custTestPaper")
public class CustTestPaper {

    @Autowired
    CustTestPaperService custTestPaper;


    /**
     * 根据测评类型码查询出所有试卷类型集合
     * @param typeCode
     */
    @RequestMapping("/queryPaperTypeByCode/{typeCode}")
    @ResponseBody
    public Map<String,Object> queryPaperTypeByCode(@PathVariable("typeCode") String typeCode){
         Map<String,Object> map = new HashMap<String,Object>();
         custTestPaper.queryPaperTypeByCode(typeCode);

         return map;
    }
}
