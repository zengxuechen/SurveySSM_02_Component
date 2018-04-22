package com.atguigu.survey.component.service.m;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.TbPaPcReportMapper;
import com.atguigu.survey.component.service.i.PaPcReportService;
import com.atguigu.survey.entities.zhyq.TbPaPcReport;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 16:28
 */
@Service
public class PaPcReportServiceImpl implements PaPcReportService {

    @Autowired
    TbPaPcReportMapper paPcReportMapper;

    public TbPaPcReport getPaPcDetailByStyleTypeCode(String styleTypeCode) {
    	return paPcReportMapper.getPaPcDetailByStyleTypeCode(styleTypeCode);
    }
}
