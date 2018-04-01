package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.zhyq.TbSelectQuestionLib;
import com.atguigu.survey.model.Page;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 15:26
 */
public interface SelectQuestionLibService {

    Page<TbSelectQuestionLib> queryExQuestionByIds(String pageNoStr, List<Integer> idIntList);

}
