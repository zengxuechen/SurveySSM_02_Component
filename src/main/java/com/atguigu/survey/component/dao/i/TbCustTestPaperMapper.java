package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.entities.zhyq.TbSelectQuestionLib;
import com.atguigu.survey.model.Page;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 12:34
 */
public interface TbCustTestPaperMapper {

    List<TbCustTestPaper> queryPaperTypeByCode(Map<String,Object> map);

    Integer queryPaperTypeByCodeCount(String typeCode);
}
