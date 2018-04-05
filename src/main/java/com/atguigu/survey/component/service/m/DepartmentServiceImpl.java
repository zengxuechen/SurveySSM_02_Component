package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbDepartmentMapper;
import com.atguigu.survey.component.service.i.DepartmentService;
import com.atguigu.survey.entities.zhyq.TbDepartment;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 16:10
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    TbDepartmentMapper departmentMapper;

    public List<TbDepartment> getDepartmentListByCompanyId(Integer companyId) {
        List<TbDepartment> result = departmentMapper.getDepartmentListByCompanyId(companyId);
        return result;
    }

    public Integer saveDepartmentBuCompanyId(Integer companyId, String departmentName) {
        Integer integer = departmentMapper.saveDepartmentByCompanyId(companyId, departmentName);
        return integer;
    }
}
