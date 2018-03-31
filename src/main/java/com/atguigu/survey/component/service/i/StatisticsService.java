package com.atguigu.survey.component.service.i;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jfree.chart.JFreeChart;

public interface StatisticsService {

	/**
	 * 显示答案列表
	 * @param questionId
	 * @return
	 */
	List<String> getTextList(Integer questionId);

	/**
	 * 生成图表-饼图
	 * @param questionId
	 * @return
	 */
	JFreeChart getPieChart(Integer questionId);

	/**
	 * 导出Excel表格
	 * @param surveyId
	 * @return
	 */
	HSSFWorkbook getExcelWorkbook(Integer surveyId);

}
