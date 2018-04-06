package com.atguigu.survey.component.service.i;

import java.util.List;
import java.util.Map; /**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 17:02
 */
public interface CustTestResultService {
    Integer saveCustTestResult(Map<String, Object> map);
    
    List<Map<String, Object>> queryResultByTypeCode(Map<String, Object> map);
}
