package com.atguigu.survey.component.service.m;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.AnswerMapper;
import com.atguigu.survey.component.service.i.EngageService;
import com.atguigu.survey.entities.guest.Answer;
import com.atguigu.survey.utils.DataProcessUtils;

@Service
public class EngageServiceImpl implements EngageService {

	
	@Autowired
	private AnswerMapper answerMapper ;

	public void parseAndSave(Map<Integer, Map<String, String[]>> allBagMap,
			Integer surveyId) {
		
		String uuid = UUID.randomUUID().toString();
		
		//涓�.瑙ｆ瀽
		//1.澹版槑涓�涓敤浜嶢nswer瀵硅薄鐨凩ist闆嗗悎
		List<Answer> answerList = new ArrayList<Answer>();
		
		//2.涓嶇敤鑰冭檻鑾峰彇鍒扮殑绛旀鏄睘浜庡摢涓�涓寘瑁癸紝鎵�浠ワ紝鍙渶瑕佽幏鍙栬繖涓狹ap涓殑鎵�鏈夌殑鍊�
		Collection<Map<String, String[]>> paramList = allBagMap.values();
		
		//3.閬嶅巻鎵�鏈夌殑绛旀
		for (Map<String, String[]> paramMap : paramList) {
			
			//4.鑾峰彇鎵�鏈夎姹傚弬鏁扮殑鍚嶇О
			Set<String> keySet = paramMap.keySet();
			
			//5.妫�鏌ュ綋鍓嶈姹傚弬鏁扮殑鍊兼槸鍚︽槸绛旀鏁版嵁锛岀瓟妗堟暟鎹殑鐗瑰緛鏄細paramName浠�'q'寮�澶�
			for (String paramName : keySet) {
				if(!paramName.startsWith("q")){
					continue ;
				}
				//6.浠巔aramName瑙ｆ瀽questionId
				String questionIdStr = paramName.substring(1);
				Integer questionId = Integer.parseInt(questionIdStr);
				
				//7.鏍规嵁paramName鏌ヨ瀵瑰簲鐨刾aramValues
				String[] paramValues = paramMap.get(paramName);
				
				//8.灏唒aramValues杞崲涓虹敤閫楀彿鍒嗛殧鐨勫瓧绗︿覆
				String content = DataProcessUtils.convertParamValues(paramValues);
				
					if("".equals(content)){
						continue ; 
					}
				
				//9.鏍规嵁鍓嶉潰鐨勫噯澶囨暟鎹紝鏉ユ瀯寤篈nswer瀵硅薄
				Answer answer = new Answer(null, content, uuid, questionId, surveyId);
				
				//10.灏咥nswer瀵硅薄瀛樻斁鍒癓ist闆嗗悎涓�
				answerList.add(answer);
			}
		}
		//======================================================
		for (Answer answer : answerList) {
			System.out.println(answer);
		}
		//======================================================
		
		//浜�.淇濆瓨
		
		answerMapper.batchSaveAnswer(answerList);
		
	}
	
}
