package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbSelectQuestionLibMapper;
import com.atguigu.survey.component.service.i.SelectQuestionLibService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbSelectQuestionLib;
import com.atguigu.survey.model.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 15:26
 */
@Service
public class SelectQuestionLibServiceImpl implements SelectQuestionLibService {

    @Autowired
    TbSelectQuestionLibMapper selectQuestionLibMapper;

    public Page<TbSelectQuestionLib> queryExQuestionByIds( String pageNoStr, List<Integer> idIntList) {

        Integer count = selectQuestionLibMapper.queryExQuestionsCount(idIntList);

        Page<TbSelectQuestionLib> page = new Page<TbSelectQuestionLib>(pageNoStr,count);

        int pageSize = page.getPageSize();

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("idIntList",idIntList);
        map.put("startIndex",page.getStartIndex());
        map.put("pageSize",pageSize);

        List<TbSelectQuestionLib>  tbSelectQuestionLibs =
                selectQuestionLibMapper.queryExQuestionByIds(map);

        page.setList(tbSelectQuestionLibs);

        return page;
    }

}
