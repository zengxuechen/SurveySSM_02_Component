package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbPaEcReport;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 12:52
 */
public interface TbPaEcReportMapper {
    List<TbPaEcReport> getAll(Map<String,Object> map);
}
