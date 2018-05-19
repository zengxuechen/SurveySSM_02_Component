package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbPaAnswerRule;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 12:47
 */
public interface TbPaAnswerRuleMapper {

    List<TbPaAnswerRule> getPaAnswerRuleByQuestionId(Integer questionId);

	List<TbPaAnswerRule> getAllPaAnswerRule();

}
