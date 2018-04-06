package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbPaPcReport;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 16:27
 */
public interface PaPcReportService {
    TbPaPcReport getPaPcDetailByStyleTypeCode(String styleTypeCode);
}
