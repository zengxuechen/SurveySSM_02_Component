package com.atguigu.survey.component.service.m;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.AnswerMapper;
import com.atguigu.survey.component.dao.i.BagMapper;
import com.atguigu.survey.component.dao.i.QuestionMapper;
import com.atguigu.survey.component.dao.i.SurveyMapper;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.e.BagEmptyException;
import com.atguigu.survey.e.SurveyEmptyException;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalMessage;

@Service
public class SurveyServiceImpl implements SurveyService {

	@Autowired
	private SurveyMapper surveyMapper ;
	
	@Autowired
	private AnswerMapper answerMapper ;
	
	@Autowired
	private QuestionMapper questionMapper ;
	
	@Autowired
	private BagMapper bagMapper ;

	public Page<Survey> getMyUnCompletedSurveyPage(User user, String pageNoStr) {
		//鈶犳煡璇㈡�昏褰曟暟
		int totalRecordNo = surveyMapper.getSurveyLimitedListCount(user.getUserId(), false);
		
		//鈶℃牴鎹�昏褰曟暟鍜孭ageNoSstr璋冨姩Page鐨勬瀯閫犲櫒鍒涘缓Page瀵硅薄
		Page<Survey> page = new Page<Survey>(pageNoStr,totalRecordNo);		
		
		//鈶粠Page瀵硅薄涓幏鍙栦慨姝ｇ殑pageNo
		int pageNo = page.getPageNo();
		
		//鈶ｄ粠Page瀵硅薄涓幏鍙杙ageSize
		int pageSize = page.getPageSize();
		
		//鈶ゆ牴鎹畃ageNo鍜宲ageSize鏌ヨList闆嗗悎
		List<Survey> list = surveyMapper.getSurveyLimitedList(user.getUserId(), false, page.getStartIndex(), pageSize);	
		
		//鈶ュ皢List璁剧疆鍒癙age瀵硅薄涓�
		page.setList(list);
		
		return page;
	}


	/*鏁堢巼浣�	
  	@Override
	public void deeplyRemove(Integer surveyId) {
		answerMapper.deleteBySurveyId(surveyId) ;
		List<Bag> bagList = bagMapper.getBagListBySurveyId(surveyId);
		for (Bag bag : bagList) {
			questionMapper.deleteByBagId(bag.getBagId()); 
			bagMapper.deleteByPrimaryKey(bag.getBagId());
		}
		surveyMapper.deleteByPrimaryKey(surveyId);
	}*/
	
	//浼樺寲锛屾彁楂樻晥鐜�
	public void deeplyRemove(Integer surveyId) {
		//鍒犻櫎璇ヨ皟鏌ョ殑鎵�鏈夐棶棰�
		surveyMapper.deleteQuestionBySurveyId(surveyId);
		//鍒犻櫎璇ヨ皟鏌ョ殑鎵�鏈夊寘瑁�
		surveyMapper.deleteBagBySurveyId(surveyId);
		//鍒犻櫎璇ヨ皟鏌�
		surveyMapper.deleteByPrimaryKey(surveyId);
	}
	
	public void complete(Integer surveyId) {
		
		Survey survey = surveyMapper.getSurveyDeeply(surveyId);
		
		Set<Bag> bagSet = survey.getBagSet();
		
		if(bagSet == null || bagSet.size() == 0){
			throw new SurveyEmptyException(GlobalMessage.SURVEY_EMPTY);
		}
		
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			
			if(questionSet == null || questionSet.size() == 0){
				throw new BagEmptyException(GlobalMessage.BAG_EMPTY);
			}
			
		}
		
		surveyMapper.compledted(surveyId);
	}


	public Page<Survey> getAllAvailableSurvey(String pageNoStr) {
		
		int totalRecordNo = surveyMapper.getSurveyLimitedListCount(null, true);
		
		Page<Survey> page = new Page<Survey>(pageNoStr,totalRecordNo);
		
		//int pageNo = page.getPageNo();
		
		int pageSize = page.getPageSize();

		List<Survey> list = surveyMapper.getSurveyLimitedList(null, true, page.getStartIndex(), pageSize);
		
		page.setList(list);
		
		return page;
	}



	public Survey getSurveyDeeply(Integer surveyId) {
		
		//return surveyMapper.selectByPrimaryKey(surveyId);
		return surveyMapper.getSurveyDeeply(surveyId);
	}


	public void updateEntity(Survey t) {
		surveyMapper.updateByPrimaryKey(t);
	}


	public void removeEntityById(Integer entityId) {
		surveyMapper.deleteByPrimaryKey(entityId);
	}


	public void saveEntity(Survey t) {
		surveyMapper.insert(t);
	}
	
	
	
	
	
	
	
	
	
	
	
}
