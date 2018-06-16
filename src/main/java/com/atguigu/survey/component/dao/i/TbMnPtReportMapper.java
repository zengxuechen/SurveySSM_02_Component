package com.atguigu.survey.component.dao.i;

import java.util.List;
import java.util.Map;

import com.atguigu.survey.entities.zhyq.TbMnPtReport;


public interface TbMnPtReportMapper {
	
	List<TbMnPtReport> getAll(Map<String, Object> map);
}
