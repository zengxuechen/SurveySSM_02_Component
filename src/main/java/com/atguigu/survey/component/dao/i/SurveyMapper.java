package com.atguigu.survey.component.dao.i;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.atguigu.survey.entities.guest.Survey;

public interface SurveyMapper {
	int deleteByPrimaryKey(Integer surveyId);

	void deleteQuestionBySurveyId(Integer surveyId);// 深度删除

	void deleteBagBySurveyId(Integer surveyId);// 深度删除

	int insert(Survey record);

	Survey selectByPrimaryKey(Integer surveyId);

	List<Survey> selectAll();

	int updateByPrimaryKey(Survey record);

	// ---------------------------------

	int getSurveyLimitedListCount(@Param("userId") Integer userId,
			@Param("completed") boolean completed);

	List<Survey> getSurveyLimitedList(@Param("userId") Integer userId,
			@Param("completed") boolean completed,
			@Param("startIndex") int startIndex, @Param("pageSize") int pageSize);

	void compledted(Integer surveyId);

	Survey getSurveyDeeply(Integer surveyId);

}