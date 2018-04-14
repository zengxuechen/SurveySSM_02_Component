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
        //保存到用户表（guest_user）中 并返回主键Id
        Integer userId = userService.saveUserAndReturnId(user);

        TbCustomerRelation customerRelation = new TbCustomerRelation();
        customerRelation.setUserId(userId);
        customerRelation.setUserNameCn(customerRelationInfoVo.getUserName());
        customerRelation.setDepartmentId(customerRelationInfoVo.getDepartmentId());
        customerRelation.setPositionId(customerRelationInfoVo.getPositionId());
        customerRelation.setEmail(customerRelationInfoVo.getEmail());
        customerRelation.setPhone(customerRelationInfoVo.getPhone());

        Integer integer = customerRelationService.saveCustomerRelationInfo(customerRelation);

        if(userId > 0 && integer >0){
            return "zhyq/saveUser_success";
        }else{
            return "zhyq/saveUser_error";
        }

    }

    @RequestMapping("manager/customerRelationHandler/getAll/{departmentId}")
    public List<CustomerRelationInfoVo> getAllUserInfoByDepartmentId(@PathVariable("departmentId") Integer departmentId){
        List<CustomerRelationInfoVo> resultList =  customerRelationService.getAllUserInfoByDepartmentId(departmentId);
        return resultList;
    }

}
