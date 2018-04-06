package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbPaPhReportMapper;
import com.atguigu.survey.component.service.i.PaPhReportService;
import com.atguigu.survey.entities.zhyq.TbPaPhReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 17:22
 */
@Service
public class PaPhReportServiceImpl implements PaPhReportService {

    @Autowired
    TbPaPhReportMapper paPhReportMapper;

    public List<TbPaPhReport> getAll(Map<String,Object> map) {
        List<TbPaPhReport> result = paPhReportMapper.getAll(map);
        return result;
    }
}
