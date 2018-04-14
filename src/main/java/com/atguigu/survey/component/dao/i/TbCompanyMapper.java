package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbCompany;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 12:56
 */
public interface TbCompanyMapper {

   Integer insertSelective(TbCompany company);

    List<TbCompany> getAllCompany();
}
