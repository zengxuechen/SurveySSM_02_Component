package com.atguigu.survey.component.service.i;

import com.atguigu.survey.component.handler.zhyq.CustTestPaper;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 13:23
 */
public interface CustTestPaperService {

    List<CustTestPaper> queryPaperTypeByCode(String typeCode);

}
