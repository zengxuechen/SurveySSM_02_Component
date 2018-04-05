package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbDepartment;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 16:10
 */
public interface DepartmentService {

    List<TbDepartment> getDepartmentListByCompanyId(Integer companyId);

    Integer saveDepartmentBuCompanyId(Integer companyId, String departmentName);
}
