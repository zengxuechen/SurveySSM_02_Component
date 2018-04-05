package com.atguigu.survey.component.handler.zhyq;

import com.atguigu.survey.component.service.i.DepartmentService;
import com.atguigu.survey.entities.zhyq.TbDepartment;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 17:51
 */
@Controller
public class DepartmentHandler {

    @Autowired
    DepartmentService departmentService;

    @RequestMapping("/manager/departmentHandler/showDepartmentListByCompanyId/{companyId}")
    public List<TbDepartment> showDepartmentListByCompanyId(@PathVariable("companyId") Integer companyId ){
        List<TbDepartment> list = new ArrayList<TbDepartment>();

        return list;
    }



}
