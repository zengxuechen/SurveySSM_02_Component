package com.atguigu.survey.component.service.m;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.atguigu.survey.component.dao.i.AnswerMapper;
import com.atguigu.survey.component.dao.i.QuestionMapper;
import com.atguigu.survey.component.dao.i.SurveyMapper;
import com.atguigu.survey.component.service.i.StatisticsService;
import com.atguigu.survey.entities.guest.Answer;
import com.atguigu.survey.entities.guest.Bag;
import com.atguigu.survey.entities.guest.Question;
import com.atguigu.survey.entities.guest.Survey;

@Service
public class StatisticsServiceImpl implements StatisticsService {

	@Autowired
	private AnswerMapper answerMapper ;
	
	@Autowired
	private QuestionMapper questionMapper;
	
	@Autowired
	private SurveyMapper surveyMapper ;

	public List<String> getTextList(Integer questionId) {		
		return answerMapper.getTextList(questionId);
	}

	public JFreeChart getPieChart(Integer questionId) {
		
		//涓�.鍑嗗鏁版嵁锛屽皝瑁匘ataset瀵硅薄
		//1.鏍规嵁questionId鑾峰彇Question瀵硅薄
		Question question = questionMapper.selectByPrimaryKey(questionId);
		
		//2.鏍规嵁questionId缁熻闂琚弬涓庣殑娆℃暟
		int questionEngageCount = answerMapper.getQuestionEngageCount(questionId);
		
		//3.鑾峰彇question瀵硅薄鐨勫睘鎬�
		String questionName = question.getQuestionName();
		String[] optionsArr = question.getOptionsArr();
		
		//4.灏佽Dataset 瀵硅薄		
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (int i = 0; i < optionsArr.length; i++) {
			int index = i ;
			String option = optionsArr[index];
			int optionEngageCount = answerMapper.getOptionEngageCount(questionId,index);
			dataset.setValue(option, optionEngageCount);
		}
		
		//浜�.鍒涘缓JFreeChart瀵硅薄
		String title = questionName + " " + questionEngageCount +"娆″弬涓�" ;
		JFreeChart chart = ChartFactory.createPieChart(title, dataset);
	
		//涓�.淇グ璁剧疆
		Font bigFont = new Font("榛戜綋",Font.PLAIN,48);
		Font midlleFont = new Font("瀹嬩綋",Font.PLAIN,36);
		Font smallfont = new Font("瀹嬩綋",Font.PLAIN,24);
		
		chart.getTitle().setFont(bigFont);
		chart.getLegend().setItemFont(midlleFont);
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setLabelFont(smallfont);
		
		plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0},{1}/{3},{2}"));
		
		plot.setBackgroundAlpha(0.6f);
		
		return chart;
	}

	public HSSFWorkbook getExcelWorkbook(Integer surveyId) {
		
		//涓�銆佹煡璇㈡暟鎹�
		//1.鏍规嵁surveyId鏌ヨSurvey瀵硅薄
		Survey survey = surveyMapper.getSurveyDeeply(surveyId);
		String surveyName = survey.getSurveyName();
		
		//2.鑾峰彇Question闆嗗悎
		List<Question> questionList = new ArrayList<Question>();
		
		Set<Bag> bagSet = survey.getBagSet();
		for (Bag bag : bagSet) {
			Set<Question> questionSet = bag.getQuestionSet();
			questionList.addAll(questionSet);
		}
		
		//3.鑾峰彇璋冩煡鍙備笌鐨勬鏁�
		int surveyEngageCount = answerMapper.getSurveyEngageCount(surveyId);
		
		//4.鏌ヨ褰撳墠璋冩煡鐨勬墍鏈夌瓟妗�
		List<Answer> answerList = answerMapper.getAnswerListBySurveyId(surveyId);
		
		
		//浜屻�佽浆鎹㈡暟鎹粨鏋�
		Map<String,Map<Integer,String>> bigMap = generateBigMap(answerList);
		
		
		//涓夈�佸垱寤篐SSFWorkbook瀵硅薄
		//1.鍒涘缓HSSFWorkbook瀵硅薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		//2.鍒涘缓宸ヤ綔鍖哄悕绉�
		String sheetname = surveyName + " " + surveyEngageCount + "娆″弬涓�";
		
		//3.鍒涘缓宸ヤ綔鍖�
		HSSFSheet sheet = workbook.createSheet(sheetname);
		
		//4.鍒涘缓琛岋紝绗竴琛屼负鎵�鏈夐棶棰樺悕绉�
		HSSFRow firstRow = sheet.createRow(0);
		
		//5.杩唬闂闆嗗悎,璁剧疆绗竴琛屽崟鍏冩牸鏁版嵁
		for (int i=0; i<questionList.size(); i++) {
			Question question = questionList.get(i);
			String questionName = question.getQuestionName();
			
			HSSFCell cell = firstRow.createCell(i);
			cell.setCellValue(questionName);
		}
		
		if(surveyEngageCount == 0) {//濡傛灉璋冩煡鍙備笌鏁伴噺涓�0锛屽氨涓嶅啀杩涜涓嬪垪鍐欏叆鏁版嵁鎿嶄綔鍟�
			return workbook;
		}
		
		//6.杩唬澶ap灏嗘墍鏈夌瓟妗堝啓鍏ュ崟鍏冩牸		
		Collection<Map<Integer, String>> values = bigMap.values(); //Collection闆嗗悎娌℃湁get()鏂规硶锛岄渶瑕佽浆鎹负List闆嗗悎
		List<Map<Integer, String>> answerValues = new ArrayList<Map<Integer, String>>(values);
		for (int i = 0; i < values.size(); i++) {
			
			Map<Integer, String> smallMap = answerValues.get(i);
			
			HSSFRow row = sheet.createRow(i+1); //浠庣2琛屽紑濮嬭凯浠ｇ瓟妗堟暟鎹紙绗�1琛屾槸琛ㄥご锛岄棶棰樺悕绉帮級
			
			int size = questionList.size(); //鏍规嵁闂闀垮害鍒涘缓鍗曞厓鏍间釜鏁�
			//7.姣忎竴娆″惊鐜垱寤轰竴琛屽崟鍏冩牸锛屽苟璁剧疆鍗曞厓鏍兼暟鎹俊鎭�
			for (int j = 0; j < size; j++) { 
				HSSFCell cell = row.createCell(j);
				
				Question question = questionList.get(j);//鏍规嵁鍗曞厓鏍肩储寮曪紝浠庨棶棰橀泦鍚堜腑鑾峰彇闂瀵硅薄
				Integer questionId = question.getQuestionId(); //鏍规嵁闂瀵硅薄鑾峰彇闂ID
				String content = smallMap.get(questionId);//鏍规嵁questionId浠庡皬Map涓幏鍙朿ontent绛旀
				
				cell.setCellValue(content);//灏哻ontent绛旀璁剧疆鍒板崟鍏冩牸涓�
			}
			
		}

		return workbook;
	}

	/**
	 * @param answerList 涓�涓皟鏌ョ殑鎵�鏈夌瓟妗�
	 * @return 
	 * 	Map<String, Map<Integer, String>> 
	 * 	key : UUID 鍖哄垎鍚屼竴娆″弬鏁拌皟鏌�
	 * 		value : Map<Integer, String>
	 * 			key : questionId 闂
	 * 			value : content 绛旀
	 */
	private Map<String, Map<Integer, String>> generateBigMap(
			List<Answer> answerList) {
		//鍒涘缓澶ap
		Map<String, Map<Integer, String>> bigMap = new HashMap<String, Map<Integer, String>>();
		
		for (Answer answer : answerList) {
			String uuid = answer.getUuid();
			//鏍规嵁uuid浠庡ぇMap涓幏鍙栧皬Map
			Map<Integer, String> smallMap = bigMap.get(uuid);
			
			//濡傛灉灏廙ap涓簄ull锛� 閭ｄ箞锛屽垱寤哄皬Map锛屽皢灏廙ap瀛樻斁鍒板ぇMap涓�
			if(smallMap == null){
				smallMap = new HashMap<Integer,String>();				
				bigMap.put(uuid, smallMap);		
			}
			
			Integer questionId = answer.getQuestionId();
			String content = answer.getContent();			
			smallMap.put(questionId, content);	

		}
		
		return bigMap;
	}

}
