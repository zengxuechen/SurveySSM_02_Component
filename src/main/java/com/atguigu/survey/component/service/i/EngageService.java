package com.atguigu.survey.component.service.i;

import java.util.Map;

public interface EngageService {

	void parseAndSave(Map<Integer, Map<String, String[]>> allBagMap,
			Integer surveyId);

}
