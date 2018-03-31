package com.atguigu.survey.component.service.m;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.QuestionMapper;
import com.atguigu.survey.component.service.i.QuestionService;
import com.atguigu.survey.entities.guest.Question;

@Service
public class QuestionServiceImpl  implements QuestionService {

	@Autowired
	private QuestionMapper questionMapper ;

	public Question getEntity(Integer entityId) {
		return questionMapper.selectByPrimaryKey(entityId);
	}

	public void updateEntity(Question t) {
		questionMapper.updateByPrimaryKey(t);
	}

	public void removeEntityById(Integer entityId) {
		questionMapper.deleteByPrimaryKey(entityId);
	}

	public void saveEntity(Question t) {
		questionMapper.insert(t);
	}
	
}
