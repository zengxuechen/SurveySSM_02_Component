package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbCompanyMapper;
import com.atguigu.survey.component.service.i.CompanyService;
import com.atguigu.survey.entities.zhyq.TbCompany;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:49
 */
@Service
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    TbCompanyMapper companMapper;

    public Integer saveCompanyInfo(TbCompany company) {
        Integer integer = companMapper.insertSelective(company);
        return integer;
    }
}
