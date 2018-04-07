package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbPaCaReportMapper;
import com.atguigu.survey.component.service.i.PaCaReportService;
import com.atguigu.survey.entities.zhyq.TbPaCaReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/7 11:53
 */
@Service
public class PaCaReportServiceImpl implements PaCaReportService {

    @Autowired
    TbPaCaReportMapper paCaReportMapper;

    public TbPaCaReport getPaCaReportByProfessionCode(String diskMax) {
        TbPaCaReport result =  paCaReportMapper.getPaCaReportByProfessionCode(diskMax);
        return result;
    }
}
