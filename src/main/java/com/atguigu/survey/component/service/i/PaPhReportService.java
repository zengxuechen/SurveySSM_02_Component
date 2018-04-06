package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbPaPhReport;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 17:22
 */
public interface PaPhReportService {
    List<TbPaPhReport> getAll(Map<String,Object> map);
}
