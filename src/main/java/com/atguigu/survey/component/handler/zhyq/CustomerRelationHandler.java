package com.atguigu.survey.component.handler.zhyq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.CustTestPaperService;
import com.atguigu.survey.component.service.i.CustTestResultService;
import com.atguigu.survey.component.service.i.CustomerRelationService;
import com.atguigu.survey.component.service.i.CustomerTestService;
import com.atguigu.survey.component.service.i.DepartmentService;
import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.entities.zhyq.TbCustomerRelation;
import com.atguigu.survey.entities.zhyq.TbCustomerTest;
import com.atguigu.survey.vo.CustomerRelationInfoVo;

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
    CustomerTestService customerTestService;
    
    @Autowired
    CustTestResultService custTestResultService;
    
    @Autowired
    CustTestPaperService custTestPaperService;

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
        customerRelation.setUserNameCn(customerRelationInfoVo.getUserNameCn());
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
        
        for(int i=0; i<resultList.size(); i++) {
        	CustomerRelationInfoVo vo = resultList.get(i);
        	Integer userId = Integer.parseInt(vo.getUserId());
    		TbCustomerTest customerTest =  customerTestService.selectCustomerTestPaperByUesrId(userId);
        	if(customerTest==null) {
        		vo.setPaperList(null); 
            }else {
            	String testPaperIds = customerTest.getTestPaperIds();
                String[] split = testPaperIds.split("@");
                List<String> strings = Arrays.asList(split);
                List<Integer> ids = new ArrayList<Integer>();
                for(String id : strings ){
            		ids.add(Integer.parseInt(id));
                }
            	List<TbCustTestPaper> paperList = custTestPaperService.getCustTestPaperList(ids);
            	vo.setPaperList(paperList); 
            }
    	}
        map.put("userList", resultList);
        return "zhyq/admin_showGuestList";
    }


}
