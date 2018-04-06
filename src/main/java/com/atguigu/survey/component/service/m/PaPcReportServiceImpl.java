package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbPaCaReportMapper;
import com.atguigu.survey.component.service.i.PaPcReportService;
import com.atguigu.survey.entities.zhyq.TbPaPcReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 16:28
 */
@Service
public class PaPcReportServiceImpl implements PaPcReportService {

    @Autowired
    TbPaCaReportMapper paCaReportMapper;

    public TbPaPcReport getPaPcDetailByStyleTypeCode(String styleTypeCode) {
        TbPaPcReport result =  paCaReportMapper.getPaPcDetailByStyleTypeCode(styleTypeCode);
        return null;
    }
}
