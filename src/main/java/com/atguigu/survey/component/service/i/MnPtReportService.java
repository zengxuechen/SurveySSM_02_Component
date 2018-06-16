package com.atguigu.survey.component.service.i;

import java.util.HashMap;
import java.util.List;

import com.atguigu.survey.entities.zhyq.TbMnPtReport;

public interface MnPtReportService {

	List<TbMnPtReport> getAll(HashMap<String, Object> hashMap);

}
