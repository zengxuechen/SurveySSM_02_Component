package com.atguigu.survey.component.handler.zhyq;

import com.atguigu.survey.component.service.i.CompanyService;
import com.atguigu.survey.entities.zhyq.TbCompany;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Action;
import java.io.File;
import java.io.IOException;

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
            log.info("图片上传出现异常！");
            return "zhyq/uploadLogo_failure";
        }
        //封装公司Logo参数
        company.setCompanyLogo(fileName);
        Integer integer = companyService.saveCompanyInfo(company);
        if(integer == 1 ){
            return "zhyq/saveCompany_success";
        }else{
            return "zhyq/saveCompany_error";
        }
    }
}
