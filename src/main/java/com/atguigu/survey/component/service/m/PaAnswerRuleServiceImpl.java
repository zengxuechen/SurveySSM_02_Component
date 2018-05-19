package com.atguigu.survey.component.service.m;

import com.atguigu.survey.component.dao.i.TbPaAnswerRuleMapper;
import com.atguigu.survey.component.service.i.PaAnswerRuleService;
import com.atguigu.survey.entities.zhyq.TbPaAnswerRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/6 16:06
 */
@Service
public class PaAnswerRuleServiceImpl implements PaAnswerRuleService{

    @Autowired
    TbPaAnswerRuleMapper paAnswerRuleMapper;

    public List<TbPaAnswerRule> getPaAnswerRuleByQuestionId(Integer questionId) {
        List<TbPaAnswerRule> result = paAnswerRuleMapper.getPaAnswerRuleByQuestionId(questionId);
        return result;
    }

	public List<TbPaAnswerRule> getAllPaAnswerRule() {
		List<TbPaAnswerRule> list = paAnswerRuleMapper.getAllPaAnswerRule();
		return list;
	}
}
