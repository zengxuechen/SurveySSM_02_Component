package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.zhyq.TbPaAnswerRule;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 16:06
 */
public interface PaAnswerRuleService {

    List<TbPaAnswerRule> getPaAnswerRuleByQuestionId(Integer questionId);

	List<TbPaAnswerRule> getAllPaAnswerRule();

}
