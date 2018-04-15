package com.atguigu.survey.component.service.i;

import java.util.List;
import com.atguigu.survey.entities.zhyq.TbCustTestResult;

import java.util.Map; /**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 17:02
 */
public interface CustTestResultService {
    Integer saveCustTestResult(Map<String, Object> map);

    List<Map<String, Object>> queryResultByTypeCode(Map<String, Object> map);

    TbCustTestResult getTbCustTestResultByTestPaperId(Integer id);

    List<TbCustTestResult> getTbCustTestResultListByUserId(String userId);

	Integer queryResultByUserIdAndPaperId(Map<String, Object> map1);
}
