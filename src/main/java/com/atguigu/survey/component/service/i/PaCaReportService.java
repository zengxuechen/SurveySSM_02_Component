package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbPaCaReport;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/7 11:52
 */
public interface PaCaReportService {
    TbPaCaReport getPaCaReportByProfessionCode(String diskMax);
}
