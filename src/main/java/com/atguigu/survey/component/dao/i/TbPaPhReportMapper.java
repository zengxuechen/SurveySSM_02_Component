package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbPaPhReport;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 12:56
 */
public interface TbPaPhReportMapper {
    List<TbPaPhReport> getAll(Map<String,Object> map);
}
