package com.atguigu.survey.component.dao.i;

import java.util.List;
import com.atguigu.survey.entities.zhyq.TbCustTestResult;

import java.util.Map; /**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 12:40
 */
public interface TbCustTestResultMapper {

    Integer saveCustTestResult(Map<String, Object> map);

    List<Map<String, Object>> queryResultByTypeCode(Map<String, Object> map);
    TbCustTestResult getTbCustTestResultByTestPaperId(Integer id);

	Integer queryResultByUserIdAndPaperId(Map<String, Object> map1);
}
