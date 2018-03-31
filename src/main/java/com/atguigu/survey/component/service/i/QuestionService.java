package com.atguigu.survey.component.service.i;

import com.atguigu.survey.entities.guest.Question;

public interface QuestionService{
	Question getEntity(Integer entityId);

	void updateEntity(Question t);

	void removeEntityById(Integer entityId);

	void saveEntity(Question t);
	
}
