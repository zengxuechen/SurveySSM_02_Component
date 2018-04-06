package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbPaEcReportMapper;
import com.atguigu.survey.component.service.i.PaEcReportService;
import com.atguigu.survey.entities.zhyq.TbPaEcReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 18:54
 */
@Service
public class PaEcReportServiceImpl implements PaEcReportService {
    @Autowired
    TbPaEcReportMapper  paEcReportMapper;

    public List<TbPaEcReport> getAll(Map<String,Object> map) {
        List<TbPaEcReport> result = paEcReportMapper.getAll(map);
        return result;
    }
}

