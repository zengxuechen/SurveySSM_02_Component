package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;

public interface SurveyService {
	Survey getSurveyDeeply(Integer surveyId);

	void updateEntity(Survey t);

	void removeEntityById(Integer entityId);

	void saveEntity(Survey t);
	
	//-----------------------------
	Page<Survey> getMyUnCompletedSurveyPage(User user, String pageNoStr);

	void deeplyRemove(Integer surveyId);

	void complete(Integer surveyId);

	Page<Survey> getAllAvailableSurvey(String pageNoStr);

}
