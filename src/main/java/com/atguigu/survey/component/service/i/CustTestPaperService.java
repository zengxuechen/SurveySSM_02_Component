package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.model.Page;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 13:23
 */
public interface CustTestPaperService {

    Page<TbCustTestPaper> queryPaperTypeByCode(String pageNoStr, String typeCode);

    TbCustTestPaper getCustTestResultAndPaperInfoByTestResultId(int i);

    List<TbCustTestPaper> getCustTestPaperList(List<Integer> ids);
}
