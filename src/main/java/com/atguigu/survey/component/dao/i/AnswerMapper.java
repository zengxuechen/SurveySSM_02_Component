package com.atguigu.survey.component.dao.i;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.guest.Answer;

public interface AnswerMapper {

	int deleteByPrimaryKey(Integer answerId);

	int insert(Answer record);

	Answer selectByPrimaryKey(Integer answerId);

	List<Answer> selectAll();

	int updateByPrimaryKey(Answer record);

	// -------------------------------

	void batchSaveAnswer(List<Answer> answerList);

	List<String> getTextList(Integer questionId);

	int getQuestionEngageCount(Integer questionId);

	int getOptionEngageCount(@Param("questionId") Integer questionId,
			@Param("index") int index);

	// Excel导出
	List<Answer> getAnswerListBySurveyId(Integer surveyId);

	int getSurveyEngageCount(Integer surveyId);

}