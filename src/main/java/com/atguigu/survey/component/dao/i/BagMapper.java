package com.atguigu.survey.component.dao.i;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.atguigu.survey.entities.guest.Bag;

public interface BagMapper {
	
	int deleteByPrimaryKey(Integer bagId);

	int insert(Bag record);

	Bag selectByPrimaryKey(Integer bagId);

	List<Bag> selectAll();

	int updateByPrimaryKey(Bag record);

	// ---------------------------------

	List<Bag> getBagListBySurveyId(Integer surveyId);

	/**
	 * Map<Integer,Integer> 
	 * 	bagId:1 
	 *  bagOrder:11
	 * @param List
	 */
	void batchUpdateBagOrder(List<Map<String, Integer>> listMap);

	void updateRelationshipByMove(@Param("bagId") Integer bagId,
			@Param("surveyId") Integer surveyId);

	Bag getBagDeeply(Integer bagId);
}