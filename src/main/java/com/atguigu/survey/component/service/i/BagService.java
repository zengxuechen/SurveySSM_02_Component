package com.atguigu.survey.component.service.i;

import java.util.List;

import com.atguigu.survey.entities.guest.Bag;

public interface BagService{
	Bag getEntity(Integer entityId);

	void removeEntityById(Integer entityId);

	//-----------------------------
	void saveBag(Bag bag);

	void updateBag(Bag bag);

	List<Bag> getBagListBySurveyId(Integer surveyId);

	void batchUpdateBagOrder(List<Integer> bagIdList, List<Integer> bagOrderList);

	void updateRelationshipByMove(Integer bagId, Integer surveyId);

	void updateRelationshipByCopy(Integer bagId, Integer surveyId); 

}
