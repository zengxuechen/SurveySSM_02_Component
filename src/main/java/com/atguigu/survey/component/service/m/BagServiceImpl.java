package com.atguigu.survey.component.service.m;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.BagMapper;
import com.atguigu.survey.component.dao.i.QuestionMapper;
import com.atguigu.survey.component.service.i.BagService;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.utils.DataProcessUtils;

@Service
public class BagServiceImpl implements BagService {

	@Autowired
	private BagMapper bagMapper;
	
	@Autowired
	private QuestionMapper questionMapper ;

	public void saveBag(Bag bag) {
		bagMapper.insert(bag);
	}

	public void updateBag(Bag bag) {
		bagMapper.updateByPrimaryKey(bag);
	} 

	public List<Bag> getBagListBySurveyId(Integer surveyId) {		
		List<Bag> bagList = bagMapper.getBagListBySurveyId(surveyId);		
		return bagList;
	}

	public void batchUpdateBagOrder(List<Integer> bagIdList,
			List<Integer> bagOrderList) {
		List<Map<String,Integer>> list = new ArrayList<Map<String,Integer>>();
		for (int i = 0; i < bagIdList.size(); i++) {
			Map<String,Integer> map = new HashMap<String,Integer>();
			map.put("bagId", bagIdList.get(i));
			map.put("bagOrder", bagOrderList.get(i));
			list.add(map);
		}
		
		bagMapper.batchUpdateBagOrder(list); 
	}

	public void updateRelationshipByMove(Integer bagId, Integer surveyId) {
		bagMapper.updateRelationshipByMove( bagId,  surveyId) ;
	}


	public void updateRelationshipByCopy(Integer bagId, Integer surveyId) {
		//鑾峰彇鐩爣瀵硅薄杩涜娣卞害澶嶅埗
		Bag bag = bagMapper.getBagDeeply(bagId);

		//璋冪敤宸ュ叿鏂规硶杩涜娣卞害澶嶅埗鍚庡璞�
		Bag targetObject = (Bag)DataProcessUtils.deeplyCopy(bag);
		targetObject.setBagId(null);
		//璁剧疆瀵硅薄鍏宠仈
		targetObject.setSurveyId(surveyId);
		targetObject.setBagOrder(targetObject.getBagId());
		//淇濆瓨鍖呰９
		bagMapper.insert(targetObject);
		
		//淇濆瓨闂闆嗗悎
		Set<Question> questionSet = targetObject.getQuestionSet();

		for (Question question : questionSet) {
			question.setBagId(targetObject.getBagId());
		}
		
		questionMapper.batchSaveQuestion(questionSet);
	}

	public Bag getEntity(Integer entityId) {
		return bagMapper.selectByPrimaryKey(entityId);
	}

	public void removeEntityById(Integer entityId) {
		bagMapper.deleteByPrimaryKey(entityId);
	}
	
}
