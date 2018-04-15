package com.atguigu.survey.component.handler.zhyq;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.survey.component.service.i.PositionService;
import com.atguigu.survey.entities.zhyq.TbPosition;

import lombok.extern.slf4j.Slf4j;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/5 15:48
 */
@Controller
public class PositionHandler {

    @Autowired
    PositionService positionService;

    /**
     * 保存职位信息
     * @param position
     * @return
     */
    @RequestMapping("manager/positionHandler/savePositionInfo")
    public String savePositionInfo(HttpServletRequest request, TbPosition position){
        Integer integer = positionService.savePositionInfo(position);
        if(integer == 1 ){
            return "zhyq/savePosition_success";
        }else{
            return "zhyq/savePosition_error";
        }
    }

    /**
     * 查询所有的职位列表
     * @return
     */
    @RequestMapping("manager/positionHandler/getAllPosition")
    public String getAllPosition(Map<String,Object> map){
        List<TbPosition> resultList =  positionService.getAll();
        map.put("positionList", resultList);
        return "zhyq/position_list";
    }
    
    
    

}
