package com.atguigu.survey.component.handler.manager;

import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_AU;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_CH;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_EC;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_GM;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_LS;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_SE;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_SV;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_CA_TF;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_ALL_AVERAGE;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_ANXIOUS;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_DEPRESSED;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_HOSTILE;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_INTERPERSONAL;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_OBSESSION;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_OTHER;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_PARANOID;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_PSYCHOSIS;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_SOMATIZATION;
import static com.atguigu.survey.constant.RuleTypeEnum.PA_PH_TERROR;
import static com.atguigu.survey.constant.TestTypeEnum.PA_CA;
import static com.atguigu.survey.constant.TestTypeEnum.PA_EC;
import static com.atguigu.survey.constant.TestTypeEnum.PA_PC;
import static com.atguigu.survey.constant.TestTypeEnum.PA_PH;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.BatchUpdateException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atguigu.survey.component.service.i.AdminService;
import com.atguigu.survey.component.service.i.CustTestPaperService;
import com.atguigu.survey.component.service.i.CustTestResultService;
import com.atguigu.survey.component.service.i.CustomerRelationService;
import com.atguigu.survey.component.service.i.MnPtReportService;
import com.atguigu.survey.component.service.i.PaAnswerRuleService;
import com.atguigu.survey.component.service.i.PaCaReportService;
import com.atguigu.survey.component.service.i.PaEcReportService;
import com.atguigu.survey.component.service.i.PaPcReportService;
import com.atguigu.survey.component.service.i.PaPhReportService;
import com.atguigu.survey.component.service.i.RoleService;
import com.atguigu.survey.component.service.i.UserService;
import com.atguigu.survey.constant.ManagementPotentialEnum;
import com.atguigu.survey.e.RemoveAdminFailedException;
import com.atguigu.survey.e.RemoveAuthFailedException;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Role;
import com.atguigu.survey.entities.zhyq.TbCustTestPaper;
import com.atguigu.survey.entities.zhyq.TbCustTestResult;
import com.atguigu.survey.entities.zhyq.TbMnPtReport;
import com.atguigu.survey.entities.zhyq.TbPaAnswerRule;
import com.atguigu.survey.entities.zhyq.TbPaCaReport;
import com.atguigu.survey.entities.zhyq.TbPaEcReport;
import com.atguigu.survey.entities.zhyq.TbPaPcReport;
import com.atguigu.survey.entities.zhyq.TbPaPhReport;
import com.atguigu.survey.utils.BubbleSort;
import com.atguigu.survey.utils.GlobalMessage;
import com.atguigu.survey.utils.GlobalNames;
import com.atguigu.survey.utils.PdfUtil;
import com.atguigu.survey.vo.CustomerDetailVo;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@SuppressWarnings("all")
@Controller
public class AdminHandler {

	@Autowired
	private AdminService adminService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService userService;

	@Autowired
	CustomerRelationService customerRelationService;

	@Autowired
	CustTestResultService custTestResultService;

	@Autowired
	CustTestPaperService custTestPaperService;

	@Autowired
	PaAnswerRuleService paAnswerRuleService;

	@Autowired
	PaPcReportService paPcReportService;

	@Autowired
	PaPhReportService paPhReportService;

	@Autowired
	PaEcReportService paEcReportService;

	@Autowired
	PaCaReportService paCaReportService;

	@Autowired
	MnPtReportService mnPtReportService;

	static final String[] CHAPTER = { "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

	@RequestMapping("/manager/admin/doDispatcherRole")
	public String doDispatcherRole(@RequestParam("adminId") Integer adminId,
			@RequestParam(value = "roleList", required = false) List<Integer> roleIdList) {

		adminService.doDispatcherRole(roleIdList, adminId);

		return "redirect:/manager/admin/showAdminList";
	}

	// 给管理员分配角色-列表页面
	@RequestMapping("manager/admin/toDispatcherUI/{adminId}")
	public String toDispatcherUI(@PathVariable("adminId") Integer adminId, Map map) {
		List<Role> queryAllRole = roleService.queryRoleList();

		List<Integer> currentIdList = adminService.getCurrentRoleList(adminId);

		map.put("allRoleList", queryAllRole);
		map.put("currentIdList", currentIdList);
		map.put("adminId", adminId);

		return "manager/admin_toDispatcherUI";
	}

	// 批量删除
	@RequestMapping("/manager/admin/batchDelete")
	public String batchDelete(@RequestParam(value = "adminList", required = false) List<Integer> adminIdList) {

		try {
			adminService.batchDelete(adminIdList);
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof BatchUpdateException) {
				throw new RemoveAdminFailedException(GlobalMessage.REMOVE_ADMIN_FAILED);
			}
			if (cause != null && cause instanceof MySQLIntegrityConstraintViolationException) {
				throw new RemoveAuthFailedException(GlobalMessage.REMOVE_ADMIN_FAILED);
			}
		}

		return "redirect:/manager/admin/showAdminList";
	}

	// 更新账号名称
	@ResponseBody
	@RequestMapping("/manager/admin/updateAdmin")
	public Map<String, String> updateAdmin(Admin admin) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			adminService.updateAdminNameByAdminId(admin);
			map.put("resultMessage", "true");
		} catch (Exception e) {
			map.put("resultMessage", "false");
		}
		return map;
	}

	// 查看所有账号
	@RequestMapping("/manager/admin/showAdminList")
	public String showAdminList(Map map) {
		List<Admin> adminList = adminService.queryAllList();
		map.put("adminList", adminList);
		return "manager/admin_showAdminList";
	}

	// 保存管理员
	@RequestMapping("/manager/admin/saveAdmin")
	public String saveAdmin(Admin admin) {
		adminService.saveAdmin(admin);
		return "redirect:/manager/admin/showAdminList";
	}

	// --------------------------------------------------------

	/*
	 * //为升级拦截器，临时性测试
	 * 
	 * @RequestMapping("/manager/statistics/showAllAvailable") public String
	 * showAllAvailable(){ return "redirect:/manager/admin/toMainUI"; }
	 */

	@RequestMapping("/manager/admin/logout")
	public String logout(HttpSession session) {

		session.invalidate();

		return "redirect:/manager/admin/toMainUI";
	}

	@RequestMapping("/manager/admin/login")
	public String login(Admin admin, HttpSession session) {

		Admin adminDB = adminService.loginForAdmin(admin);

		session.setAttribute(GlobalNames.LOGIN_Admin, adminDB);

		return "redirect:/manager/admin/toMainUI";
	}

	// 新增客户信息
	@RequestMapping("/manager/admin/saveGuest")
	public String saveGuest(User user) {
		userService.regist(user);
		return "redirect:/manager/admin/showAdminList";
	}

	// 查看所有客户
	@RequestMapping("/manager/admin/showGuestList")
	public String showGuestList(Map map) {
		List<User> userList = userService.queryAllList();
		map.put("userList", userList);
		return "manager/admin_showGuestList";
	}

	// 查看所有客户
	@RequestMapping("/manager/admin/exportReport/{userId}")
	public void exportReport(Map map, HttpServletRequest request, HttpServletResponse response,
			@PathVariable("userId") Integer userId) {

		// 用户信息
		CustomerDetailVo user = customerRelationService.getRelationInfoByUserId(userId);
		// userId查询结果表（tb_cust_test_result）
		List<TbCustTestResult> resultLists = custTestResultService.getTbCustTestResultListByUserId(userId);
		// 获取所有结果集
		List<TbPaAnswerRule> paAnswerRuleList = paAnswerRuleService.getAllPaAnswerRule();
		// 当前时间
		LocalDateTime now = LocalDateTime.now();

		// 1.替换用户信息
		String personInfoContent = makePersonInfoContent(user, now);

		// 2.测评结果报告信息
		// 是否含有PDP标志
		boolean pdpFlg = true;
		int i;
		for (i = 0; i < resultLists.size(); i++) {
			TbCustTestResult custTestResult = resultLists.get(i);// 获取各个测评显示的对象
			TbCustTestPaper custTestPaper = custTestPaperService
					.getCustTestResultAndPaperInfoByTestResultId(custTestResult.getId());
			String questionIds = custTestPaper.getQuestionIds();// 获取试题集
			String testResult = custTestResult.getTestResult(); // 获取测试结果集（以"@"区分开）

			// PA_PC("PA_PC", "人才测评_职业性格")
			if (PA_PC.getCode().equals(custTestPaper.getTestTypeCode())) {
				List<TbPaAnswerRule> paAnswer = new ArrayList<TbPaAnswerRule>();
				for (TbPaAnswerRule p : paAnswerRuleList) {
					if ("PA_PC".equals(p.getRuleTypeCode().substring(0, 5))) {
						paAnswer.add(p);
					}
				}
				// 2-1.替换职业性格信息
				String contentPA_PC = makeContentPA_PC(paAnswer, questionIds, testResult);
				personInfoContent = personInfoContent + contentPA_PC;
				pdpFlg = false;
			}
			// PA_PH("PA_PH", "人才测评_心理健康"),
			if (PA_PH.getCode().equals(custTestPaper.getTestTypeCode())) {
				List<TbPaAnswerRule> paAnswer = new ArrayList<TbPaAnswerRule>();
				for (TbPaAnswerRule p : paAnswerRuleList) {
					if ("PA_PH".equals(p.getRuleTypeCode().substring(0, 5))) {
						paAnswer.add(p);
					}
				}
				// 2-2.替换心理健康信息
				String contentPA_PH = makeContentPA_PH(paAnswer, questionIds, testResult);
				personInfoContent = personInfoContent + contentPA_PH;
			}
			// PA_EC("PA_EC", "人才测评_情绪能力"),
			if (PA_EC.getCode().equals(custTestPaper.getTestTypeCode())) {
				List<TbPaAnswerRule> paAnswer = new ArrayList<TbPaAnswerRule>();
				for (TbPaAnswerRule p : paAnswerRuleList) {
					if ("PA_EC".equals(p.getRuleTypeCode().substring(0, 5))) {
						paAnswer.add(p);
					}
				}
				// 2-3.替换情绪能力信息
				String contentPA_EC = makeContentPA_EC(paAnswer, questionIds, testResult);
				personInfoContent = personInfoContent + contentPA_EC;
			}
			// PA_CA("PA_CA", "人才测评_职业锚");*/
			if (PA_CA.getCode().equals(custTestPaper.getTestTypeCode())) {
				List<TbPaAnswerRule> paAnswer = new ArrayList<TbPaAnswerRule>();
				for (TbPaAnswerRule p : paAnswerRuleList) {
					if ("PA_CA".equals(p.getRuleTypeCode().substring(0, 5))) {
						paAnswer.add(p);
					}
				}
				// 2-4.替换职业锚信息
				String contentPA_CA = makeContentPA_CA(paAnswer, questionIds, testResult);
				personInfoContent = personInfoContent + contentPA_CA;
			}
			personInfoContent = personInfoContent.replace("${chapter}", CHAPTER[i]);
		}

		if (pdpFlg) {
			// 2-5.pdp信息
			String contentPA_PDP = makeContentPA_PDP();
			personInfoContent = personInfoContent + contentPA_PDP;
			personInfoContent = personInfoContent.replace("${chapter}", CHAPTER[i]);
			i++;
		}

		String contentPA_ADVISE = makeContentPA_ADVISE();
		personInfoContent = personInfoContent + contentPA_ADVISE;
		personInfoContent = personInfoContent.replace("${chapter}", CHAPTER[i]);

		String tempFilePath = request.getSession().getServletContext().getRealPath("/tempFile/");
		String fileName = "测评报告_" + user.getUserName() + "_" + now.getYear() + "-" + now.getMonthValue() + "-"
				+ now.getDayOfMonth() + "_" + now.getHour() + "_" + now.getMinute() + "_" + now.getSecond() + ".pdf";
		boolean isover = PdfUtil.createTempPdf(personInfoContent, tempFilePath + fileName);

		if (isover) {
			// 设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("multipart/form-data");
			// 设置响应头，控制浏览器下载该文件
			response.setHeader("content-disposition", "attachment;filename=" + fileName);
			// 读取要下载的文件，保存到文件输入流
			FileInputStream in = null;
			try {
				in = new FileInputStream(tempFilePath + fileName);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// 创建输出流
			OutputStream out = null;
			try {
				out = response.getOutputStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// 创建缓冲区
			byte buffer[] = new byte[1024];
			int len = 0;
			// 循环将输入流中的内容读取到缓冲区当中
			try {
				while ((len = in.read(buffer)) > 0) {
					// 输出缓冲区的内容到浏览器，实现文件下载
					out.write(buffer, 0, len);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private String makePersonInfoContent(CustomerDetailVo user, LocalDateTime now) {
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_PA_PERSONINFO.html")
				.getPath();
		String personInfoContent = PdfUtil.readToString(personInfoTemplate);
		personInfoContent = personInfoContent.replace("${companyName}", user.getCompanyNameCN());
		personInfoContent = personInfoContent.replace("${companyLogo}", user.getCompanyLogo());
		personInfoContent = personInfoContent.replace("${userName}", user.getUserName());
		personInfoContent = personInfoContent.replace("${departmentName}", user.getDepartmentName());
		personInfoContent = personInfoContent.replace("${positionName}", user.getPositionName());
		personInfoContent = personInfoContent.replace("${year}", now.getYear() + "");
		personInfoContent = personInfoContent.replace("${month}", now.getMonthValue() + "");
		personInfoContent = personInfoContent.replace("${date}", now.getDayOfMonth() + "");
		return personInfoContent;
	}

	/**
	 * 生成PA_PC报告
	 * 
	 * @param answerList
	 * @param questionIds
	 * @param testResult
	 * @return
	 */
	private String makeContentPA_PC(List<TbPaAnswerRule> answerList, String questionIds, String testResult) {
		String[] questionIdArr = questionIds.split("@");
		String[] resultArr = testResult.split("@");
		int jCount = 0; // J的数量
		int pCount = 0; // P的数量
		int tCount = 0; // T的数量
		int fCount = 0; // F的数量
		int sCount = 0; // S的数量
		int nCount = 0; // N的数量
		int eCount = 0; // E的数量
		int iCount = 0; // I的数量
		for (int i = 0; i < questionIdArr.length; i++) {
			// 通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
			// List<TbPaAnswerRule> answerList =
			// paAnswerRuleService.getPaAnswerRuleByQuestionId(Integer.parseInt(questionIdArr[i]));
			String questionId = questionIdArr[i];
			for (TbPaAnswerRule pa : answerList) {
				if (questionId.equals(pa.getQuestionId().toString())) {
					String answerBitmap = pa.getAnswerBitmap(); // 标准答案
					if (answerBitmap.equals(resultArr[i])) {
						if ("J".equals(pa.getAnswerAnalysis())) {
							jCount++;
						}
						if ("P".equals(pa.getAnswerAnalysis())) {
							pCount++;
						}
						if ("T".equals(pa.getAnswerAnalysis())) {
							tCount++;
						}
						if ("F".equals(pa.getAnswerAnalysis())) {
							fCount++;
						}
						if ("S".equals(pa.getAnswerAnalysis())) {
							sCount++;
						}
						if ("N".equals(pa.getAnswerAnalysis())) {
							nCount++;
						}
						if ("E".equals(pa.getAnswerAnalysis())) {
							eCount++;
						}
						if ("I".equals(pa.getAnswerAnalysis())) {
							iCount++;
						}
					}
				}
			}
		}
		// 拼装所属类型
		String styleTypeCode = new String();
		if (eCount > iCount) {
			styleTypeCode += "E";
		} else {
			styleTypeCode += "I";
		}
		if (sCount > nCount) {
			styleTypeCode += "S";
		} else {
			styleTypeCode += "N";
		}
		if (tCount > fCount) {
			styleTypeCode += "T";
		} else {
			styleTypeCode += "F";
		}
		if (jCount > pCount) {
			styleTypeCode += "J";
		} else {
			styleTypeCode += "P";
		}
		// 根据所属类型替换相对应的信息
		TbPaPcReport paPcReportResult = paPcReportService.getPaPcDetailByStyleTypeCode(styleTypeCode);
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_PA_PC.html")
				.getPath();
		String contentPA_PC = PdfUtil.readToString(personInfoTemplate);
		contentPA_PC = contentPA_PC.replace("${styleTypeCode}", paPcReportResult.getStyleTypeCode());
		contentPA_PC = contentPA_PC.replace("${styleTypeTendency}", paPcReportResult.getStyleTypeTendency());
		contentPA_PC = contentPA_PC.replace("${styleTypeName}", paPcReportResult.getStyleTypeName());
		contentPA_PC = contentPA_PC.replace("${styleTypeDesc}", paPcReportResult.getStyleTypeDesc());
		contentPA_PC = contentPA_PC.replace("${blindSpotTips}", paPcReportResult.getBlindSpotTips());
		contentPA_PC = contentPA_PC.replace("${suitedCareer}", paPcReportResult.getSuitedCareer());
		return contentPA_PC;
	}

	/**
	 * 生成PA_PH报告
	 * 
	 * @param answerList
	 * @param questionIds
	 * @param testResult
	 * @return
	 */
	private String makeContentPA_PH(List<TbPaAnswerRule> answerList, String questionIds, String testResult) {
		String[] questionIdArr = questionIds.split("@");
		String[] resultArr = testResult.split("@");

		int totalCount = questionIdArr.length;// 答题总个数
		int totalScore = 0;// 答题总分数
		int somatizationCount = 0;// 躯体化症状个数
		int somatizationScore = 0;// 躯体化症状分数
		int obsessionCount = 0;// 强迫症状个数
		int obsessionScore = 0;// 强迫症状分数
		int interpersonalCount = 0;// 人际关系症状个数
		int interpersonalScore = 0;// 人际关系症状分数
		int depressedCount = 0;// 抑郁症状个数
		int depressedScore = 0;// 抑郁症状分数
		int anxiousCount = 0;// 焦虑症状个数
		int anxiousScore = 0;// 焦虑症状分数
		int hostileCount = 0;// 敌对症状个数
		int hostileScore = 0;// 敌对症状分数
		int terrorCount = 0;// 恐怖症状个数
		int terrorScore = 0;// 恐怖症状分数
		int paranoidCount = 0;// 偏执症状个数
		int paranoidScore = 0;// 偏执症状分数
		int psychosisCount = 0;// 精神病性症状个数
		int psychosisScore = 0;// 精神病性症状分数
		int otherCount = 0;// 其他症状个数
		int otherScore = 0;// 其他症状分数
		int positiveCount = 0;// 阳性症状个数
		int positiveScore = 0;// 阳性症状分数
		for (int i = 0; i < questionIdArr.length; i++) {
			// 通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
			// List<TbPaAnswerRule> answerList =
			// paAnswerRuleService.getPaAnswerRuleByQuestionId(Integer.parseInt(questionIdArr[i]));
			String questionId = questionIdArr[i];
			String s = resultArr[i];// 测试结果
			for (TbPaAnswerRule pa : answerList) {
				if (questionId.equals(pa.getQuestionId().toString())) {
					String answerBitmap = pa.getAnswerBitmap(); // 标准答案
					if (answerBitmap.equals(s)) {
						if (PA_PH_SOMATIZATION.getCode().equals(pa.getRuleTypeCode())) {// 躯体化
							somatizationCount++;
							somatizationScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_OBSESSION.getCode().equals(pa.getRuleTypeCode())) {// 强迫症状
							obsessionCount++;
							obsessionScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_INTERPERSONAL.getCode().equals(pa.getRuleTypeCode())) {// 人际关系
							interpersonalCount++;
							interpersonalScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_DEPRESSED.getCode().equals(pa.getRuleTypeCode())) {// 抑郁
							depressedCount++;
							depressedScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_ANXIOUS.getCode().equals(pa.getRuleTypeCode())) {// 焦虑
							anxiousCount++;
							anxiousScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_HOSTILE.getCode().equals(pa.getRuleTypeCode())) {// 敌对
							hostileCount++;
							hostileScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_TERROR.getCode().equals(pa.getRuleTypeCode())) {// 恐怖
							terrorCount++;
							terrorScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_PARANOID.getCode().equals(pa.getRuleTypeCode())) {// 偏执
							paranoidCount++;
							paranoidScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_PSYCHOSIS.getCode().equals(pa.getRuleTypeCode())) {// 精神病性
							psychosisCount++;
							psychosisScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_PH_OTHER.getCode().equals(pa.getRuleTypeCode())) {// 其他
							otherCount++;
							otherScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (2 < Integer.parseInt(pa.getAnswerAnalysis())) {// 阳性
							positiveCount++;// 阳性症状个数
							positiveScore += Integer.parseInt(pa.getAnswerAnalysis());// 阳性症状分数
						}
						totalScore += Integer.parseInt(pa.getAnswerAnalysis());// 总分数
					}
				}
			}
		}

		BigDecimal allAverageScore = new BigDecimal((float) totalScore / totalCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 总均分
		BigDecimal somatizationAverage = new BigDecimal((float) somatizationScore / somatizationCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 躯体化症状均分
		BigDecimal obsessionAverage = new BigDecimal((float) obsessionScore / obsessionCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 强迫症均分
		BigDecimal interpersonalAverage = new BigDecimal((float) interpersonalScore / interpersonalCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 人际关系均分
		BigDecimal depressedAverage = new BigDecimal((float) depressedScore / depressedCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 抑郁症均分
		BigDecimal anxiousAverage = new BigDecimal((float) anxiousScore / anxiousCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 焦虑均分
		BigDecimal hostileAverage = new BigDecimal((float) hostileScore / hostileCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 敌对症状均分
		BigDecimal terrorAverage = new BigDecimal((float) terrorScore / terrorCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 恐怖症均分
		BigDecimal paranoidAverage = new BigDecimal((float) paranoidScore / paranoidCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 偏执症状均分
		BigDecimal psychosisAverage = new BigDecimal((float) psychosisScore / psychosisCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 精神病均分
		BigDecimal otherAverage = new BigDecimal((float) otherScore / otherCount).setScale(2, BigDecimal.ROUND_HALF_UP);// 其他症均分
		BigDecimal positiveAverage = new BigDecimal((float) positiveScore / positiveCount).setScale(2,
				BigDecimal.ROUND_HALF_UP);// 阳性均分

		String desc_ALL_AVERAGE = "";// 总均分描述
		String symptomDesc_PART_SOMATIZATION = "";// 躯体化描述
		String symptomDesc_PART_OBSESSION = "";// 强迫症状描述
		String symptomDesc_PART_INTERPERSONAL = "";// 人际关系敏感描述
		String symptomDesc_PART_DEPRESSED = "";// 抑郁描述
		String symptomDesc_PART_ANXIOUS = "";// 焦虑描述
		String symptomDesc_PART_HOSTILE = "";// 敌对描述
		String symptomDesc_PART_TERROR = "";// 恐怖描述
		String symptomDesc_PART_PARANOID = "";// 偏执描述
		String symptomDesc_PART_PSYCHOSIS = "";// 精神性病描述
		String symptomDesc_PART_OTHER = "";// 其他描述
		// 获取所有的心理健康测评数据
		Map<String, Object> map = new HashMap<String, Object>();
		List<TbPaPhReport> resultList = paPhReportService.getAll(map);
		for (TbPaPhReport pa : resultList) {
			BigDecimal begin = pa.getStandardValueBgn();
			BigDecimal end = pa.getStandardValueEnd();
			String code = pa.getSymptomTypeCode();
			String symptomDesc = pa.getSymptomDesc();
			if (PA_PH_ALL_AVERAGE.getCode().equals(code)) {// 总症状
				if (allAverageScore.compareTo(begin) > 0 && allAverageScore.compareTo(end) < 0) {
					desc_ALL_AVERAGE = symptomDesc;
				}
			}
			if (PA_PH_SOMATIZATION.getCode().equals(code)) {// 躯体化
				if (somatizationAverage.compareTo(begin) > 0 && somatizationAverage.compareTo(end) < 0) {
					symptomDesc_PART_SOMATIZATION = symptomDesc;
				}
			}
			if (PA_PH_OBSESSION.getCode().equals(code)) {// 强迫症
				if (obsessionAverage.compareTo(begin) > 0 && obsessionAverage.compareTo(end) < 0) {
					symptomDesc_PART_OBSESSION = symptomDesc;
				}
			}
			if (PA_PH_INTERPERSONAL.getCode().equals(code)) {// 人际关系
				if (interpersonalAverage.compareTo(begin) > 0 && interpersonalAverage.compareTo(end) < 0) {
					symptomDesc_PART_INTERPERSONAL = symptomDesc;
				}
			}
			if (PA_PH_DEPRESSED.getCode().equals(code)) {// 抑郁
				if (depressedAverage.compareTo(begin) > 0 && depressedAverage.compareTo(end) < 0) {
					symptomDesc_PART_DEPRESSED = symptomDesc;
				}
			}
			if (PA_PH_ANXIOUS.getCode().equals(code)) {// 焦虑
				if (anxiousAverage.compareTo(begin) > 0 && anxiousAverage.compareTo(end) < 0) {
					symptomDesc_PART_ANXIOUS = symptomDesc;
				}
			}
			if (PA_PH_HOSTILE.getCode().equals(code)) {// 敌对
				if (hostileAverage.compareTo(begin) > 0 && hostileAverage.compareTo(end) < 0) {
					symptomDesc_PART_HOSTILE = symptomDesc;
				}
			}
			if (PA_PH_TERROR.getCode().equals(code)) {// 恐怖
				if (terrorAverage.compareTo(begin) > 0 && terrorAverage.compareTo(end) < 0) {
					symptomDesc_PART_TERROR = symptomDesc;
				}
			}
			if (PA_PH_PARANOID.getCode().equals(code)) {// 偏执
				if (paranoidAverage.compareTo(begin) > 0 && paranoidAverage.compareTo(end) < 0) {
					symptomDesc_PART_PARANOID = symptomDesc;
				}
			}
			if (PA_PH_PSYCHOSIS.getCode().equals(code)) {// 精神性病
				if (psychosisAverage.compareTo(begin) > 0 && psychosisAverage.compareTo(end) < 0) {
					symptomDesc_PART_PSYCHOSIS = symptomDesc;
				}
			}
			if (PA_PH_OTHER.getCode().equals(code)) {// 其他
				if (otherAverage.compareTo(begin) > 0 && otherAverage.compareTo(end) < 0) {
					symptomDesc_PART_OTHER = symptomDesc;
				}
			}
		}

		// 根据所属类型替换相对应的信息
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_PA_PH.html")
				.getPath();
		String contentPA_PH = PdfUtil.readToString(personInfoTemplate);

		contentPA_PH = contentPA_PH.replace("${score_ALL_AVERAGE}", allAverageScore + "");
		contentPA_PH = contentPA_PH.replace("${desc_ALL_AVERAGE}", desc_ALL_AVERAGE);
		contentPA_PH = contentPA_PH.replace("${count_ALL_POSITIVENUM}", positiveCount + "");
		contentPA_PH = contentPA_PH.replace("${score_ALL_POSITIVEAVERAGE}", positiveAverage + "");
		contentPA_PH = contentPA_PH.replace("${score_PART_SOMATIZATION}", somatizationAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_SOMATIZATION}", symptomDesc_PART_SOMATIZATION);
		contentPA_PH = contentPA_PH.replace("${score_PART_OBSESSION}", obsessionAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_OBSESSION}", symptomDesc_PART_OBSESSION);
		contentPA_PH = contentPA_PH.replace("${score_PART_INTERPERSONAL}", interpersonalAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_INTERPERSONAL}", symptomDesc_PART_INTERPERSONAL);
		contentPA_PH = contentPA_PH.replace("${score_PART_DEPRESSED}", depressedAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_DEPRESSED}", symptomDesc_PART_DEPRESSED);
		contentPA_PH = contentPA_PH.replace("${score_PART_ANXIOUS}", anxiousAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_ANXIOUS}", symptomDesc_PART_ANXIOUS);
		contentPA_PH = contentPA_PH.replace("${score_PART_HOSTILE}", hostileAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_HOSTILE}", symptomDesc_PART_HOSTILE);
		contentPA_PH = contentPA_PH.replace("${score_PART_TERROR}", terrorAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_TERROR}", symptomDesc_PART_TERROR);
		contentPA_PH = contentPA_PH.replace("${score_PART_PARANOID}", paranoidAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_PARANOID}", symptomDesc_PART_PARANOID);
		contentPA_PH = contentPA_PH.replace("${score_PART_PSYCHOSIS}", psychosisAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_PSYCHOSIS}", symptomDesc_PART_PSYCHOSIS);
		contentPA_PH = contentPA_PH.replace("${score_PART_OTHER}", otherAverage + "");
		contentPA_PH = contentPA_PH.replace("${symptomDesc_PART_OTHER}", symptomDesc_PART_OTHER);
		return contentPA_PH;
	}

	/**
	 * 生成PA_EC报告
	 * 
	 * @param answerList
	 * @param questionIds
	 * @param testResult
	 * @return
	 */
	private String makeContentPA_EC(List<TbPaAnswerRule> answerList, String questionIds, String testResult) {
		String[] questionIdArr = questionIds.split("@");
		String[] resultArr = testResult.split("@");

		int pa_ec_score = 0;
		for (int i = 0; i < questionIdArr.length; i++) {
			String questionId = questionIdArr[i];
			String s = resultArr[i];// 测试结果
			// 通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
			// List<TbPaAnswerRule> answerList =
			// paAnswerRuleService.getPaAnswerRuleByQuestionId(Integer.parseInt(questionIdArr[i]));
			for (TbPaAnswerRule pa : answerList) {
				if (questionId.equals(pa.getQuestionId().toString())) {
					String answerBitmap = pa.getAnswerBitmap(); // 标准答案
					if (answerBitmap.equals(s)) {
						pa_ec_score += Integer.parseInt(pa.getAnswerAnalysis());
					}
				}
			}
		}
		String sectionDesc = "";// 需要替换
		Map<String, Object> map = new HashMap<String, Object>();
		List<TbPaEcReport> paEcList = paEcReportService.getAll(map);
		for (TbPaEcReport pa : paEcList) {
			if (pa_ec_score > pa.getStandardValueBgn() && pa_ec_score < pa.getStandardValueEnd()) {
				sectionDesc = pa.getSectionDesc();
			}
		}

		// 根据所属类型替换相对应的信息
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_PA_EC.html")
				.getPath();
		String contentPA_EC = PdfUtil.readToString(personInfoTemplate);
		contentPA_EC = contentPA_EC.replace("${pa_ec_score}", pa_ec_score + "");
		contentPA_EC = contentPA_EC.replace("${sectionDesc}", sectionDesc);
		return contentPA_EC;
	}

	/**
	 * 生成PA_CA报告
	 * 
	 * @param answerList
	 * @param questionIds
	 * @param testResult
	 * @return
	 */
	private String makeContentPA_CA(List<TbPaAnswerRule> answerList, String questionIds, String testResult) {
		String[] questionIdArr = questionIds.split("@");
		String[] resultArr = testResult.split("@");

		int tfScore = 0;// TF分数
		int gmScore = 0;// GM分数
		int auScore = 0;// AU分数
		int seScore = 0;// SE分数
		int ecScore = 0;// EC分数
		int svScore = 0;// SV分数
		int chScore = 0;// CH分数
		int lsScore = 0;// LS分数
		for (int i = 0; i < questionIdArr.length; i++) {
			// 通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
			Integer questionId = Integer.parseInt(questionIdArr[i]);
			String s = resultArr[i];// 测试结果
			// List<TbPaAnswerRule> answerList =
			// paAnswerRuleService.getPaAnswerRuleByQuestionId(questionId);
			for (TbPaAnswerRule pa : answerList) {
				if (pa.getQuestionId() == questionId) {
					String answerBitmap = pa.getAnswerBitmap(); // 标准答案
					if (answerBitmap.equals(s)) {
						if (PA_CA_TF.getCode().equals(pa.getRuleTypeCode())) {
							tfScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_CA_GM.getCode().equals(pa.getRuleTypeCode())) {
							gmScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_CA_AU.getCode().equals(pa.getRuleTypeCode())) {
							auScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_CA_SE.getCode().equals(pa.getRuleTypeCode())) {
							seScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_CA_EC.getCode().equals(pa.getRuleTypeCode())) {
							ecScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_CA_SV.getCode().equals(pa.getRuleTypeCode())) {
							svScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_CA_CH.getCode().equals(pa.getRuleTypeCode())) {
							chScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
						if (PA_CA_LS.getCode().equals(pa.getRuleTypeCode())) {
							lsScore += Integer.parseInt(pa.getAnswerAnalysis());
						}
					}
				}
			}
		}
		Map<String, Integer> map2 = new HashMap<String, Integer>();
		map2.put("TF", tfScore);
		map2.put("GM", gmScore);
		map2.put("AU", auScore);
		map2.put("SE", seScore);
		map2.put("EC", ecScore);
		map2.put("SV", svScore);
		map2.put("CH", chScore);
		map2.put("LS", lsScore);
		String diskMax = getDiskMax(map2);
		String star1 = "";
		String star2 = "";
		String star3 = "";
		String star4 = "";
		String star5 = "";
		String star6 = "";
		String star7 = "";
		String star8 = "";
		if ("TF".equals(diskMax)) {
			star1 = "★";
		}
		if ("GM".equals(diskMax)) {
			star2 = "★";
		}
		if ("AU".equals(diskMax)) {
			star3 = "★";
		}
		if ("SE".equals(diskMax)) {
			star4 = "★";
		}
		if ("EC".equals(diskMax)) {
			star5 = "★";
		}
		if ("SV".equals(diskMax)) {
			star6 = "★";
		}
		if ("CH".equals(diskMax)) {
			star7 = "★";
		}
		if ("LS".equals(diskMax)) {
			star7 = "★";
		}
		TbPaCaReport tbPaCaReport = paCaReportService.getPaCaReportByProfessionCode(diskMax);
		String characterSummarize = tbPaCaReport.getCharacterSummarize();
		String characterDesc = tbPaCaReport.getCharacterDesc();

		// 根据所属类型替换相对应的信息
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_PA_CA.html")
				.getPath();
		String contentPA_CA = PdfUtil.readToString(personInfoTemplate);
		contentPA_CA = contentPA_CA.replace("${star1}", star1);
		contentPA_CA = contentPA_CA.replace("${star2}", star2);
		contentPA_CA = contentPA_CA.replace("${star3}", star3);
		contentPA_CA = contentPA_CA.replace("${star4}", star4);
		contentPA_CA = contentPA_CA.replace("${star5}", star5);
		contentPA_CA = contentPA_CA.replace("${star6}", star6);
		contentPA_CA = contentPA_CA.replace("${star7}", star7);
		contentPA_CA = contentPA_CA.replace("${star8}", star8);
		contentPA_CA = contentPA_CA.replace("${characterSummarize}", characterSummarize);
		contentPA_CA = contentPA_CA.replace("${characterDesc}", characterDesc);
		return contentPA_CA;
	}

	/**
	 * 生成管理潜力报告
	 * 
	 * @param answerList
	 * @param questionIds
	 * @param testResult
	 * @return
	 */
	private String makeContentMN_PT(List<TbPaAnswerRule> answerList, String questionIds, String testResult) {
		String[] questionIdArr = questionIds.split("@");
		String[] resultArr = testResult.split("@");
		// 总体潜力分析得分
		int mnPtTotalScore = 0;
		// 提高自己的时间回报得分
		int mnPtTimeScore = 0;
		// 激发他人及培养他人得分
		int mnPtOtherScore = 0;
		// 成为创意及执行大师得分
		int mnPtMasterScore = 0;
		// 研究客户、对手及环境得分
		int mnPtSearchScore = 0;
		// 提高思考及判断能力得分
		int mnPtThinkScore = 0;
		// 潜力分析描述
		String mnPtTotalDescription = "";
		// 剩余潜力描述
		String mnPtOtherDescription = "";
		// 提升重点
		String mnPtOtherTips = "";
		for (int i = 0; i < questionIdArr.length; i++) {
			// 通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
			Integer questionId = Integer.parseInt(questionIdArr[i]);
			String s = resultArr[i];// 测试结果
			// 第一步：计算出所有需要计算的分数
			for (TbPaAnswerRule pa : answerList) {
				if (pa.getQuestionId() == questionId) {
					String answerBitmap = pa.getAnswerBitmap(); // 标准答案
					if (answerBitmap.equals(s)) {
						// 总体潜力分析得分
						int count = Integer.parseInt(pa.getAnswerAnalysis());
						if (ManagementPotentialEnum.MN_PT_TOTAL.getCode().equals(pa.getRuleTypeCode())) {
							mnPtTotalScore += count;
						}
						// 提高自己的时间回报得分
						if (ManagementPotentialEnum.MN_PT_TIME.getCode().equals(pa.getRuleTypeCode())) {
							mnPtTimeScore += count;
						}
						// 激发他人及培养他人得分
						if (ManagementPotentialEnum.MN_PT_OTHER.getCode().equals(pa.getRuleTypeCode())) {
							mnPtOtherScore += count;
						}
						// 成为创意及执行大师得分
						if (ManagementPotentialEnum.MN_PT_MASTER.getCode().equals(pa.getRuleTypeCode())) {
							mnPtMasterScore += count;
						}
						// 研究客户、对手及环境得分
						if (ManagementPotentialEnum.MN_PT_SEARCH.getCode().equals(pa.getRuleTypeCode())) {
							mnPtSearchScore += count;
						}
						// 提高思考及判断能力得分
						if (ManagementPotentialEnum.MN_PT_THINK.getCode().equals(pa.getRuleTypeCode())) {
							mnPtThinkScore += count;
						}
					}
				}
			}
			// 第二步：根据计算得出的分数来进行参数的封装
			int[] array = new int[] { mnPtTimeScore, mnPtOtherScore, mnPtMasterScore, mnPtSearchScore, mnPtThinkScore };
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put(ManagementPotentialEnum.MN_PT_TIME.getCode(), mnPtTimeScore);
			map.put(ManagementPotentialEnum.MN_PT_OTHER.getCode(), mnPtOtherScore);
			map.put(ManagementPotentialEnum.MN_PT_MASTER.getCode(), mnPtMasterScore);
			map.put(ManagementPotentialEnum.MN_PT_SEARCH.getCode(), mnPtSearchScore);
			map.put(ManagementPotentialEnum.MN_PT_THINK.getCode(), mnPtThinkScore);
			// 获取所有的report结果数据
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			List<TbMnPtReport> tbMnPtReportList = mnPtReportService.getAll(hashMap);
			for (TbMnPtReport mp : tbMnPtReportList) {
				String mnPtTotalcode = ManagementPotentialEnum.MN_PT_TOTAL.getCode();
				int standardValueBgn = mp.getStandardValueBgn();
				int standardValueEnd = mp.getStandardValueEnd();
				String ptTypeCode = mp.getPtTypeCode();
				// 总体潜力分析
				if (mnPtTotalcode.equals(ptTypeCode) && mnPtTotalScore > standardValueBgn
						&& mnPtTotalScore <= standardValueEnd) {
					// 总体潜力描述
					mnPtTotalDescription = mp.getPtDesc();
				}
			}

			// 其他潜力描述
			int j1 = BubbleSort.bubbleSort(array)[0];
			int j2 = BubbleSort.bubbleSort(array)[1];
			int j3 = BubbleSort.bubbleSort(array)[2];
			int j4 = BubbleSort.bubbleSort(array)[3];
			int j5 = BubbleSort.bubbleSort(array)[4];
			if (mnPtTimeScore == mnPtOtherScore && mnPtOtherScore == mnPtMasterScore
					&& mnPtMasterScore == mnPtSearchScore && mnPtSearchScore == mnPtThinkScore) {
				// 其他潜力描述
				for (TbMnPtReport mp : tbMnPtReportList) {
					if (mnPtTimeScore > mp.getStandardValueBgn() && mnPtTimeScore <= mp.getStandardValueEnd()) {
						mnPtOtherDescription = mp.getPtTips();
					}
				}
				if(mnPtTimeScore >= 20) {
					mnPtOtherTips = "无";
				}else{
					for (TbMnPtReport mp1 : tbMnPtReportList) {
						if (ManagementPotentialEnum.MN_PT_TIME.getCode().equals(mp1.getPtTypeCode())
								|| ManagementPotentialEnum.MN_PT_OTHER.getCode().equals(mp1.getPtTypeCode())
								|| ManagementPotentialEnum.MN_PT_MASTER.getCode().equals(mp1.getPtTypeCode())
								|| ManagementPotentialEnum.MN_PT_SEARCH.getCode().equals(mp1.getPtTypeCode())
								|| ManagementPotentialEnum.MN_PT_THINK.getCode().equals(mp1.getPtTypeCode())) {
							mnPtOtherTips += mp1.getPtTips();
						}
					}
				}
				// 如果最大的值小于15
			} else if (j5 < 15) {
				mnPtOtherDescription = "你的弱项在于：";
				int count = 1;
				for (TbMnPtReport mp1 : tbMnPtReportList) {
					if (ManagementPotentialEnum.MN_PT_TIME.getCode().equals(mp1.getPtTypeCode())
							|| ManagementPotentialEnum.MN_PT_OTHER.getCode().equals(mp1.getPtTypeCode())
							|| ManagementPotentialEnum.MN_PT_MASTER.getCode().equals(mp1.getPtTypeCode())
							|| ManagementPotentialEnum.MN_PT_SEARCH.getCode().equals(mp1.getPtTypeCode())
							|| ManagementPotentialEnum.MN_PT_THINK.getCode().equals(mp1.getPtTypeCode())) {
						mnPtOtherDescription += "\"" + mp1.getPtTypeName() + "\"";
						mnPtOtherTips += mp1.getPtTips();
						if (count < 5) {
							mnPtOtherDescription += ",";
							count++;
						}
					}
				}
				// 最大值大于等于15
			} else {
				int count = 1;
				int count1 = 1;
				mnPtOtherDescription = "相对来说，你的强项在于：";
				String mnPtOtherDescription1 = ",你的弱项在于：";
				for (Map.Entry<String, Integer> entry : map.entrySet()) {
					String key = entry.getKey();
					Integer value = entry.getValue();
					if(value == j5) {
						for (TbMnPtReport mp1 : tbMnPtReportList) {
							if(key.equals(mp1.getPtTypeCode()) && ManagementPotentialEnum.MN_PT_TIME.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_OTHER.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_MASTER.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_SEARCH.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_THINK.getCode().equals(mp1.getPtTypeCode())) {
								if(count != 1) {
									mnPtOtherDescription += ",";
									count++;
								}
								mnPtOtherDescription += "\"" + mp1.getPtTypeName() + "\"";
							}
						}
					}else if(value == j1) {
						for (TbMnPtReport mp1 : tbMnPtReportList) {
							if(key.equals(mp1.getPtTypeCode()) && ManagementPotentialEnum.MN_PT_TIME.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_OTHER.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_MASTER.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_SEARCH.getCode().equals(mp1.getPtTypeCode())
									|| ManagementPotentialEnum.MN_PT_THINK.getCode().equals(mp1.getPtTypeCode())) {
								if(count1 != 1) {
									mnPtOtherDescription += ",";
									count1++;
								}
								mnPtOtherDescription1 += "\"" + mp1.getPtTypeName() + "\"";
								mnPtOtherTips += mp1.getPtTips();
							}
						}
					}
				}
				mnPtOtherDescription += mnPtOtherDescription1;
			}
		}
		// 根据所属类型替换相对应的信息
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_MN_PT.html")
				.getPath();
		String contentMN_PT = PdfUtil.readToString(personInfoTemplate);
		contentMN_PT = contentMN_PT.replace("${score_MN_PT_TOTAL}",mnPtTotalScore+"");
		contentMN_PT = contentMN_PT.replace("${desc_MN_PT_TOTAL}", mnPtTotalDescription);
		contentMN_PT = contentMN_PT.replace("${desc_MN_PT}", mnPtOtherDescription);
		contentMN_PT = contentMN_PT.replace("${score_MN_PT_TIME}", mnPtTimeScore+"");
		contentMN_PT = contentMN_PT.replace("${score_MN_PT_OTHER}", mnPtOtherScore+"");
		contentMN_PT = contentMN_PT.replace("${score_MN_PT_MASTER}", mnPtMasterScore+"");
		contentMN_PT = contentMN_PT.replace("${score_MN_PT_SEARCH}", mnPtSearchScore+"");
		contentMN_PT = contentMN_PT.replace("${score_MN_PT_THINK}", mnPtThinkScore+"");
		contentMN_PT = contentMN_PT.replace("${tips_MN_PT}", mnPtOtherTips);

		return contentMN_PT;
	}

	/**
	 * 生成PA_PDP报告
	 * 
	 * @param questionIds
	 * @param testResult
	 * @return
	 */
	private String makeContentPA_PDP() {

		// 根据所属类型替换相对应的信息
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_PA_PDP.html")
				.getPath();
		String contentPA_EC = PdfUtil.readToString(personInfoTemplate);
		return contentPA_EC;
	}

	/**
	 * 生成PA_ADVISE报告
	 * 
	 * @param questionIds
	 * @param testResult
	 * @return
	 */
	private String makeContentPA_ADVISE() {
		// 根据所属类型替换相对应的信息
		String personInfoTemplate = this.getClass().getClassLoader().getResource("/template/REPORT_PA_ADVISE.html")
				.getPath();
		String contentPA_ADVISE = PdfUtil.readToString(personInfoTemplate);
		return contentPA_ADVISE;
	}

	/**
	 * 求Map里的最大值并且返回key
	 * 
	 * @param map
	 * @return
	 */
	private String getDiskMax(Map<String, Integer> map) {
		List<Integer> list = new ArrayList<Integer>();
		for (String temp : map.keySet()) {
			Integer value = map.get(temp);
			list.add(value);
		}
		Integer max = 0;
		for (int i = 0; i < list.size(); i++) {
			Integer size = list.get(i);
			max = (max > size) ? max : size;
		}
		for (String key : map.keySet()) {
			if (max == map.get(key)) {
				return key;
			}
		}
		return null;
	}

}
