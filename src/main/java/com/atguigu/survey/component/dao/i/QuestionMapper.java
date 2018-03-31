package com.atguigu.survey.component.dao.i;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.guest.Question;

public interface QuestionMapper {

	int deleteByPrimaryKey(Integer questionId);

	int insert(Question record);

	Question selectByPrimaryKey(Integer questionId);

	List<Question> selectAll();

	int updateByPrimaryKey(Question record);

	List<Question> getQuestionListByBagId(Integer bagId);

	// ---------------------------------
	void batchSaveQuestion(@Param("questionSet") Set<Question> questionSet);

}