package com.atguigu.survey.component.handler.zhyq;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.CustomerRelationService;
import com.atguigu.survey.component.service.i.DepartmentService;
import com.atguigu.survey.entities.zhyq.TbCustomerRelation;
import com.atguigu.survey.entities.zhyq.TbDepartment;
import com.atguigu.survey.utils.GlobalNames;
import com.atguigu.survey.utils.SimpleEncrypUtil;
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
    
    @Autowired
    CustomerRelationService customerRelationService;

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
    	for (int i = 0; i < selectList.size(); i++) {
    		try {
				String encrypt = 
						SimpleEncrypUtil.Encrypt(selectList.get(i).getId().toString(), 2018);
				selectList.get(i).setDepartmentId(encrypt);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
        
        map.put("departmentList", selectList);
        return "zhyq/department_list";
    }
    
    
    /**
     * 查询出所有部门集合
     * @param typeCode
     */
    @RequestMapping("manager/departmentHandler/queryAll")
    public String queryAll(Map<String,Object> map, @Param("userId") Integer userId, @RequestParam ("companyId") Integer companyId){
    	List<TbDepartment> selectList = departmentService.getDepartmentListByCompanyId(companyId);
        map.put("departmentList", selectList);
        map.put("userId",userId);
        return "zhyq/department_toDispatcherUI";
    }
    
    /**
     * 保存部门Id
     * @param typeCode
     */
    @RequestMapping("manager/departmentHandler/saveDepartment")
    public String saveDepartment(Map<String,Object> map, @Param("userId") Integer userId, @RequestParam ("departmentId") Integer departmentId){
    	TbCustomerRelation tcr = new TbCustomerRelation();
    	tcr.setUserId(userId);
    	tcr.setDepartmentId(departmentId);
    	customerRelationService.updateCustomerRelationInfo(tcr);
    	return "redirect:/manager/customerRelationHandler/getAllUser";
    }

}
