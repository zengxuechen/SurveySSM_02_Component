package com.atguigu.survey.component.dao.i;

import com.atguigu.survey.entities.zhyq.TbPaPcReport;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 12:54
 */
public interface TbPaPcReportMapper {
	 TbPaPcReport getPaPcDetailByStyleTypeCode(String styleTypeCode);
}
