package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbCompany;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:48
 */
public interface CompanyService {

    Integer  saveCompanyInfo(TbCompany company);

    List<TbCompany> getAllCompany();
}
