package com.atguigu.survey.component.handler.zhyq;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.atguigu.survey.component.service.i.CompanyService;
import com.atguigu.survey.entities.zhyq.TbCompany;
import com.atguigu.survey.entities.zhyq.TbCustTestPaper;

import lombok.extern.slf4j.Slf4j;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:48
 */
@Controller
@Slf4j
public class CompanyHandler {

    @Autowired
    CompanyService companyService;

    /**
     * 保存公司信息
     * @param company
     * @return
     */
    @RequestMapping("manager/companyHandler/saveCompanyInfo")
    public String saveCompanyInfo(HttpServletRequest request, TbCompany company, @RequestParam("logoFile") MultipartFile logoFile){

        String uploadPath
                = request.getSession().getServletContext().getRealPath("/upload/image");
        //获取文件名称
        String imageFileName = logoFile.getOriginalFilename();
        //拼装文件名
        String fileName = System.currentTimeMillis()+ imageFileName;
        //上传图片
        try {
            logoFile.transferTo(new File(uploadPath+"/"+fileName));
        } catch (IOException e) {
            log.info("图片上传出现异常！", e);
            return "zhyq/uploadLogo_failure";
        }
        //封装公司Logo参数
        company.setCompanyLogo("/upload/image/"+fileName);
        Integer integer = companyService.saveCompanyInfo(company);
        if(integer == 1 ){
            return "zhyq/saveCompany_success";
        }else{
            return "zhyq/saveCompany_error";
        }
    }

    /**
     * 查询所有的公司列表
     * @return
     */
    @RequestMapping("manager/companyHandler/getAllCompany")
    public String getAllCompany(Map<String,Object> map){
        List<TbCompany> resultList =  companyService.getAllCompany();
        map.put("companyList", resultList);
        return "zhyq/company_list";
    }
    
    /**
     * 跳转添加部门页面
     * @return
     */
    @RequestMapping("manager/admin/toDepartmentUI/{companyId}")
    public String toDepartmentUI(Map<String,Object> map, @PathVariable("companyId") Integer companyId){
        map.put("companyId", companyId);
        return "zhyq/admin_departmentUI";
    }
    
    
    /**
     * 查询出所有公司集合
     * @param typeCode
     */
    @RequestMapping("manager/companyHandler/queryAll/{userId}")
    public String queryAll(Map<String,Object> map, @PathVariable("userId") Integer userId){
    	List<TbCompany> resultList =  companyService.getAllCompany();
        map.put("companyList", resultList);
        map.put("userId",userId);
        return "zhyq/company_toDispatcherUI";
    }
    
}
