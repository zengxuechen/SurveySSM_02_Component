package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 13:02
 */
public interface TbDepartmentMapper {

    List<TbDepartment> getDepartmentListByCompanyId(Integer companyId);

    Integer saveDepartmentByCompanyId(@Param("companyId") Integer companyId , @Param("departmentName") String departmentName);

}
