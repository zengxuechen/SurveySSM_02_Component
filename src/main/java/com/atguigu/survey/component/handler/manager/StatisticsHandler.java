package com.atguigu.survey.component.handler.manager;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.atguigu.survey.component.service.i.StatisticsService;
import com.atguigu.survey.component.service.i.SurveyService;
import com.atguigu.survey.entities.guest.Survey;
import com.atguigu.survey.model.Page;
import com.atguigu.survey.utils.GlobalNames;

//教统 - 教务统计 
@SuppressWarnings("all")
@Controller
public class StatisticsHandler {

	
	@Autowired
	private SurveyService surveyService ;
	
	@Autowired
	private StatisticsService statisticsService ;

	
	@RequestMapping("/manager/statistics/exportExcel/{surveyId}")
	public void exportExcel(@PathVariable("surveyId") Integer surveyId,
			HttpServletResponse response) throws IOException{
		
		//1.创建HSSFWorkbook对象
		HSSFWorkbook workbook = statisticsService.getExcelWorkbook(surveyId);
		
		//2.获取输出流
		ServletOutputStream outputStream = response.getOutputStream();

		//3.准备文件名字符串
		String fileName = System.nanoTime() + ".xls";
		
		//4.设置响应的内容类型
		response.setContentType("application/vnd.ms-excel");
		
		//5.设置浏览器下载文件时的文件名响应消息头
		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
		
		//6.将HSSFWorkbook中数据写入输出流，返回给客户端
		workbook.write(outputStream);
	}
	
	
	//生成图表-饼图
	@RequestMapping("/manager/statistics/showChart/{questionId}")
	public void showChart(
			@PathVariable("questionId") Integer questionId,
			HttpServletResponse response) throws IOException{
		
		// 1.调用业务层获得JFreeChart对象
		JFreeChart chart =  statisticsService.getPieChart(questionId);
		
		// 2.获取字节输出流
		ServletOutputStream outputStream = response.getOutputStream();
		 
		// 3.将图表数据写到输出流中，返回给客户端浏览器
		ChartUtilities.writeChartAsJPEG(outputStream, chart, 1200, 600);
	}
	
	//显示简答题列表
	@RequestMapping("/manager/statistics/showText/{questionId}")
	public String showText(@PathVariable("questionId") Integer questionId ,Map map){
		
		List<String> textList = statisticsService.getTextList(questionId);
		
		map.put("textList", textList); //简答题答案
		
		return "manager/statistics_textList";
	}
	
	
	//统计大纲
	@RequestMapping("/manager/statistics/showSummary/{surveyId}")
	public String showSummary(
			@PathVariable("surveyId") Integer surveyId ,Map map){
		
		Survey survey = surveyService.getSurveyDeeply(surveyId);
		map.put("survey", survey);
		
		return "manager/statistics_showSummary";
	}
	

	
	//统计调查列表
	@RequestMapping("/manager/statistics/showAllAvailable")
	public String showAllAvailable(
			@RequestParam(value="pageNoStr",required=false) String pageNoStr,
			Map map){
		
		Page<Survey> page = surveyService.getAllAvailableSurvey(pageNoStr);
		
		map.put(GlobalNames.PAGE, page);
		
		return "manager/statistics_list";
	}
	
}
