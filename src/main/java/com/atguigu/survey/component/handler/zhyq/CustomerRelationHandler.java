package com.atguigu.survey.component.handler.zhyq;

import com.atguigu.survey.component.service.i.CustomerRelationService;
import com.atguigu.survey.component.service.i.DepartmentService;
import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbCustomerRelation;
import com.atguigu.survey.vo.CustomerRelationInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:26
 */
@Controller
public class CustomerRelationHandler {

    @Autowired
    DepartmentService departmentService;

    @Autowired
    CustomerRelationService customerRelationService;

    @Autowired
    UserService userService;

    /**
     * 保存用户信息
     * @param customerRelationInfoVo
     * @return
     */
    @RequestMapping("manager/customerRelationHandler/saveCustomerInfo")
    public String saveCustomerInfo(CustomerRelationInfoVo customerRelationInfoVo){

        User user = new User();
        user.setUserName(customerRelationInfoVo.getUserName());
        user.setUserPwd(customerRelationInfoVo.getPassword());
        user.setCompany(customerRelationInfoVo.isCompany());
        //user.setCompany(false); //个人
        //保存到用户表（guest_user）中 并返回主键Id
        userService.regist(user);

        TbCustomerRelation customerRelation = new TbCustomerRelation();
        customerRelation.setUserId(user.getUserId());
        customerRelation.setUserNameCn(customerRelationInfoVo.getUserName());
        customerRelation.setDepartmentId(customerRelationInfoVo.getDepartmentId());
        customerRelation.setPositionId(customerRelationInfoVo.getPositionId());
        customerRelation.setEmail(customerRelationInfoVo.getEmail());
        customerRelation.setPhone(customerRelationInfoVo.getPhone());

        Integer integer = customerRelationService.saveCustomerRelationInfo(customerRelation);

        if(integer >0){
            return "zhyq/saveUser_success";
        }else{
            return "zhyq/saveUser_error";
        }

    }

    /**
     * 通过部门Id查询所有的员工信息
     * @param departmentId
     * @return
     */
    @RequestMapping("manager/customerRelationHandler/getAllUserInfoByDepartmentId/{departmentId}")
    public String getAllUserInfoByDepartmentId(Map<String, Object> map, @PathVariable("departmentId") Integer departmentId){
        List<CustomerRelationInfoVo> resultList =  customerRelationService.getAllUserInfoByDepartmentId(departmentId);
        map.put("userList", resultList);
        return "zhyq/department_users";
    }
    
    
    /**
     * 查询所有的员工信息
     * @param departmentId
     * @return
     */
    @RequestMapping("manager/customerRelationHandler/getAllUser")
    public String getAllUser(Map<String, Object> map){
        List<CustomerRelationInfoVo> resultList =  customerRelationService.getAllUser();
        map.put("userList", resultList);
        return "zhyq/admin_showGuestList";
    }


}
