package com.atguigu.survey.component.handler.zhyq;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.DepartmentService;
import com.atguigu.survey.entities.zhyq.TbDepartment;
import com.atguigu.survey.utils.GlobalNames;
import com.atguigu.survey.vo.CustomerDetailVo;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 17:51
 */
@Controller
public class DepartmentHandler {

    @Autowired
    DepartmentService departmentService;

    /**
     * 根据公司Id返回部门List
     * @param companyId
     * @return
     */
    @RequestMapping("/manager/departmentHandler/showDepartmentListByCompanyId/{companyId}")
    public String showDepartmentListByCompanyId(Map<String, Object> map, @PathVariable("companyId") Integer companyId ){
        List<TbDepartment> selectList = departmentService.getDepartmentListByCompanyId(companyId);
        
        map.put("departmentList", selectList);
        return "zhyq/department_list";
    }

    /**
     * 在相应的公司下添加部门
     * @param companyId
     * @param departmentName
     * @return
     */
    @RequestMapping("/manager/departmentHandler/saveDepartmentByCid")
    public String addDepartment(Integer companyId , String departmentName) {
        Integer integer = departmentService.saveDepartmentBuCompanyId(companyId, departmentName);
        if(integer == 1){
            return "zhyq/addDepartment_success";
        }else{
            return "zhyq/addDepartment_error";
        }
    }
    
    /**
     * 根据公司Id返回部门List
     * @param companyId
     * @return
     */
    @RequestMapping("/guest/departmentHandler/showDepartmentListByCompanyId")
    public String showDepartmentList(Map<String, Object> map, HttpSession session){
        
    	Integer companyId = ((CustomerDetailVo)session.getAttribute(GlobalNames.USER_RELATION)).getCompanyId();
    	List<TbDepartment> selectList = departmentService.getDepartmentListByCompanyId(companyId);
        
        map.put("departmentList", selectList);
        return "zhyq/department_list";
    }

}
