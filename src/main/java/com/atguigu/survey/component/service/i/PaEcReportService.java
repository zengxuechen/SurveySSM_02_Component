package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbPaEcReport;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 18:54
 */
public interface PaEcReportService {
    List<TbPaEcReport> getAll(Map<String,Object> map);
}
