package com.atguigu.survey.component.handler.zhyq;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.atguigu.survey.component.service.i.*;
import com.atguigu.survey.entities.zhyq.*;
import com.atguigu.survey.utils.PdfUtil;
import com.atguigu.survey.vo.CustomerDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

import static com.atguigu.survey.constant.ProfessionEnum.TF;
import static com.atguigu.survey.constant.RuleTypeEnum.*;
import static com.atguigu.survey.constant.TestTypeEnum.*;
import static com.atguigu.survey.constant.TestTypeEnum.PA_EC;

import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.utils.GlobalNames;

/**
 * Using IntelliJ IDEA.
 *
 * @author 李小鑫 at 2018/4/1 17:01
 */
@Controller
public class CustTestResultHandler {

    @Autowired
    UserService userService;

    @Autowired
    CustomerRelationService customerRelationService;

    @Autowired
    CustTestPaperService custTestPaperService;

    @Autowired
    CustTestResultService custTestResultService;


    @Autowired
    PaAnswerRuleService paAnswerRuleService;

    @Autowired
    PaPcReportService paPcReportService;

    @Autowired
    PaPhReportService paPhReportService;

    @Autowired
    PaEcReportService paEcReportService;


    @RequestMapping("guest/custTestResult/saveCustTestResult/{typeCode}/{paperId}/{result}")
    public String saveCustTestResult(HttpSession session , @PathVariable("typeCode") String typeCode, @PathVariable("paperId") String paperId ,@PathVariable("result") String result){
        User user = (User)session.getAttribute(GlobalNames.LOGIN_USER);
        Integer userId = user.getUserId();

        Map<String,Object> map = new HashMap<String,Object>();

        map.put("userId",userId);
        map.put("paperId",paperId);
        map.put("result",result);
        map.put("typeCode",typeCode);

        Integer integer = custTestResultService.saveCustTestResult(map);

        if(integer == 1){
            return "/zhyq/saveUserResult_success";
        }else{
            return "/zhyq/saveUserResult_error";
        }

    }

    /**
     * 根据测评类型码查询出所有试卷类型集合
     * @param typeCode
     */
    @RequestMapping("guest/custTestResult/queryResultByTypeCode/{typeCode}")
    public String queryResultByTypeCode(@PathVariable("typeCode") String typeCode, Map<String,Object> map, HttpSession session){

        User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);

        Map<String, Object> map1 = new HashMap<String,Object>();
        map1.put("userId",user.getUserId());
        map1.put("typeCode",typeCode);
        List<Map<String, Object>> list=
                custTestResultService.queryResultByTypeCode(map1);

        map.put("user",user);
        map.put("typeCode",typeCode);
        map.put("list",list);

        return "zhyq/result_type";
    }


    /**
     * 导出 PDF
     */
    @RequestMapping("guest/custTestResultHandler/exportPDF/{resultIds}")
    public void exportPDF(@PathVariable("resultId") String resultIds){
         //1。传过来一个试题id字符串（用@分割）。
        String[] split = resultIds.split("@");
        List<String> resultIdList = Arrays.asList(split);
        //2。通过 第一个resultId（题目id） 查询tb_cust_test_result表的 客户id字段 通过多表关联 查出 公司 部门 个人信息
        Integer id = Integer.parseInt(resultIdList.get(0));
        TbCustTestResult tbCustTestResultByTestPaperId = custTestResultService.getTbCustTestResultByTestPaperId(id);
        User user = userService.getEntity(Integer.parseInt(tbCustTestResultByTestPaperId.getTestUserId()));
        String userName = user.getUserName();//需要替换的字段
        CustomerDetailVo vo = customerRelationService.getRelationInfoByUserId(Integer.parseInt(tbCustTestResultByTestPaperId.getTestUserId()));
        String companyName = vo.getCompanyNameCN();//需要替换的字段
        String departmentName = vo.getDepartmentName();//需要替换的字段
        String positionName = vo.getPositionName();//需要替换的字段
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);//需要替换的字段（年）
        int month = now.get(Calendar.MONTH) + 1;//需要替换的字段（月）
        int day = now.get(Calendar.DAY_OF_MONTH);//需要替换的字段（日）
        //3.计算出需要替换的值
         // ①。 个人与公司信息与时间
         // ②。（职业性格）在 tb_cust_test_paper 取值 TEST_TYPE_CODE = PA_PC 找到  QUESTION_IDS 所有的问题
         // 通过 循环 tb_pa_answer_rule（规则表）算出相应的得分，出现最多来进行判断是哪种人然后替换（写死）（根据文件规则进行相应的类型去匹配）
         // 获取所有的客户测试题表的对象
         TbCustTestPaper paPcReport = null; // 职业性格测试表对象
         TbCustTestPaper paPhReport = null; // 心理健康测评表对象
         TbCustTestPaper paEcReport = null; // 情绪能力测评表对象
         TbCustTestPaper paCaReport = null; // 职业锚测评表对象
         for( String paperId : resultIdList){ // 获取各个测评显示的对象
             TbCustTestPaper custTestPaper = custTestPaperService.getCustTestResultAndPaperInfoByTestResultId(Integer.parseInt(paperId));
             if(PA_PC.getCode().equals(custTestPaper.getTestTypeCode())){
                 paPcReport = custTestPaper;
             }
             if(PA_PH.getCode().equals(custTestPaper.getTestTypeCode())){
                 paPhReport = custTestPaper;
             }
             if(PA_EC.getCode().equals(custTestPaper.getTestTypeCode())){
                 paEcReport = custTestPaper;
             }
             if(PA_CA.getCode().equals(custTestPaper.getTestTypeCode())){
                 paCaReport = custTestPaper;
             }
         }
        TbCustTestResult result = custTestResultService.getTbCustTestResultByTestPaperId(paPcReport.getId());
        String questionIds = paPcReport.getQuestionIds();//获取试题集
        String[] split1 = questionIds.split("@");
        List<String> strings = Arrays.asList(split1);//试题集合
        String testResult = result.getTestResult(); //获取测试结果集（以"@"区分开）
        String[] split2 = testResult.split("@");
        List<String> strings1 = Arrays.asList(split2);//测试结果集合
        int jCount = 0; //J的数量
        int pCount = 0; //P的数量
        int tCount = 0; //T的数量
        int fCount = 0; //F的数量
        int sCount = 0; //S的数量
        int nCount = 0; //N的数量
        int eCount = 0; //E的数量
        int iCount = 0; //I的数量
        for( int i = 0 ; i <  strings.size() ; i++){
             //通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
            Integer questionId = Integer.parseInt(strings.get(i));
            List<TbPaAnswerRule> answerList = paAnswerRuleService.getPaAnswerRuleByQuestionId(questionId);
            for(TbPaAnswerRule pa : answerList){
                String s = strings1.get(i); //测试结果
                String answerBitmap = pa.getAnswerBitmap(); //标准答案
                if(answerBitmap.equals(s)){
                     if("J".equals(pa.getAnswerAnalysis())){
                         jCount++;
                     }
                    if("P".equals(pa.getAnswerAnalysis())){
                         pCount++;
                    }
                    if("T".equals(pa.getAnswerAnalysis())){
                         tCount++;
                    }
                    if("F".equals(pa.getAnswerAnalysis())){
                         fCount++;
                    }
                    if("S".equals(pa.getAnswerAnalysis())){
                         sCount++;
                    }
                    if("N".equals(pa.getAnswerAnalysis())){
                         nCount++;
                    }
                    if("E".equals(pa.getAnswerAnalysis())){
                         eCount++;
                    }
                    if("I".equals(pa.getAnswerAnalysis())){
                         iCount++;
                    }
                }
            }
        }
        //拼装所属类型
        String styleTypeCode = new String();
        if(eCount > iCount){
            styleTypeCode += "E";
        }else{
            styleTypeCode += "I";
        }
        if(sCount > nCount){
            styleTypeCode += "S";
        }else{
            styleTypeCode += "N";
        }
        if(tCount > fCount){
            styleTypeCode += "T";
        }else{
            styleTypeCode += "F";
        }
        if(jCount > pCount){
            styleTypeCode += "J";
        }else{
            styleTypeCode += "P";
        }
        //根据所属类型查出相对应的信息
        TbPaPcReport paPcReportResult = paPcReportService.getPaPcDetailByStyleTypeCode(styleTypeCode);
        String styleTypeTendency = paPcReportResult.getStyleTypeTendency();//需要替换的字段
        String styleTypeName  =  paPcReportResult.getStyleTypeName();//需要替换的字段
        String styleTypeDesc = paPcReportResult.getStyleTypeDesc();//需要替换的字段
        String blindSpotTips = paPcReportResult.getBlindSpotTips();//需要替换的字段
        String suitedCareer = paPcReportResult.getSuitedCareer();//需要替换的字段
        // ③。 (心理健康测评) 在 tb_cust_test_paper 取值 TEST_TYPE_CODE = PA_PH 找到 QUESTION_IDS 所有的问题
         // 通过 循环 tb_pa_answer_rule（规则表）算出相应的得分，在tb_pa_ph_report表里进行对比拿数据，出现最多来进行判断是哪种人然后替换（写死）（根据文件规则进行相应的类型去匹配）
        TbCustTestResult paPhResult = custTestResultService.getTbCustTestResultByTestPaperId(paPhReport.getId());
        String paPhQuestionIds = paPhReport.getQuestionIds();//获取试题集
        String[] paPhSplit1 = paPhQuestionIds.split("@");
        List<String> paPhStrings = Arrays.asList(paPhSplit1);//试题集合
        String paPhTestResult = paPhResult.getTestResult(); //获取测试结果集（以"@"区分开）
        String[] paPhSplit2 = paPhTestResult.split("@");
        List<String> paPhStrings1 = Arrays.asList(split2);//测试结果集合
        int totalCount = paPhStrings.size();//答题总个数
        int totalScore = 0;//答题总分数
        int somatizationCount = 0;//躯体化症状个数
        int somatizationScore = 0;//躯体化症状分数
        int obsessionCount =  0;//强迫症状个数
        int obsessionScore = 0;//强迫症状分数
        int interpersonalCount = 0;//人际关系症状个数
        int interpersonalScore = 0;//人际关系症状分数
        int depressedCount = 0;//抑郁症状个数
        int depressedScore = 0;//抑郁症状分数
        int anxiousCount = 0;//焦虑症状个数
        int anxiousScore = 0;//焦虑症状分数
        int hostileCount = 0;//敌对症状个数
        int hostileScore = 0;//敌对症状分数
        int terrorCount = 0;//恐怖症状个数
        int terrorScore = 0;//恐怖症状分数
        int paranoidCount = 0;//偏执症状个数
        int paranoidScore = 0;//偏执症状分数
        int psychosisCount =0;//精神病性症状个数
        int psychosisScore =0;//精神病性症状分数
        int otherCount =0;//其他症状个数
        int otherScore =0;//其他症状分数
        int positiveCount =0;//阳性症状个数
        int positiveScore =0;//阳性症状分数
        for(int i = 0; i< paPhStrings.size() ;i++){
            //通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
            Integer questionId = Integer.parseInt(paPhStrings.get(i));
            List<TbPaAnswerRule> answerList = paAnswerRuleService.getPaAnswerRuleByQuestionId(questionId);
            for(TbPaAnswerRule pa : answerList){
                String s = paPhStrings1.get(i);//测试结果
                String answerBitmap = pa.getAnswerBitmap(); //标准答案
                if(answerBitmap.equals(s)){
                    if(PA_PH_SOMATIZATION.getCode().equals(pa.getRuleTypeCode())){//躯体化
                        somatizationCount++;
                        somatizationScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_OBSESSION.getCode().equals(pa.getRuleTypeCode())){//强迫症状
                        obsessionCount++;
                        obsessionScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_INTERPERSONAL.getCode().equals(pa.getRuleTypeCode())){//人际关系
                        interpersonalCount++;
                        interpersonalScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_DEPRESSED.getCode().equals(pa.getRuleTypeCode())){//抑郁
                        depressedCount++;
                        depressedScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_ANXIOUS.getCode().equals(pa.getRuleTypeCode())){//焦虑
                        anxiousCount++;
                        anxiousScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_HOSTILE.getCode().equals(pa.getRuleTypeCode())){//敌对
                        hostileCount++;
                        hostileScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_TERROR.getCode().equals(pa.getRuleTypeCode())){//恐怖
                        terrorCount++;
                        terrorScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_PARANOID.getCode().equals(pa.getRuleTypeCode())){//偏执
                        paranoidCount++;
                        paranoidScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_PSYCHOSIS.getCode().equals(pa.getRuleTypeCode())){//精神病性
                        psychosisCount++;
                        psychosisScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_PH_OTHER.getCode().equals(pa.getRuleTypeCode())){//其他
                        otherCount++;
                        otherScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(2 < Integer.parseInt(pa.getAnswerAnalysis())){//阳性
                        positiveCount++;//阳性症状个数
                        positiveScore += Integer.parseInt(pa.getAnswerAnalysis());//阳性症状分数
                    }
                    totalCount++;//总个数
                    totalScore += Integer.parseInt(pa.getAnswerAnalysis());//总分数
                }
            }
        }
        double allAverageScore = deciMal(totalScore,totalCount);//总均分
        double somatizationAverage = deciMal(somatizationScore,somatizationCount);//躯体化症状均分
        double obsessionAverage = deciMal(obsessionScore,obsessionCount);//强迫症均分
        double interpersonalAverage = deciMal(interpersonalScore,interpersonalCount);//人际关系均分
        double depressedAverage  = deciMal(depressedScore,depressedCount);//抑郁症均分
        double anxiousAverage  = deciMal(anxiousScore,anxiousCount);//焦虑均分
        double hostileAverage  = deciMal(hostileScore,hostileCount);//敌对症状均分
        double terrorAverage  = deciMal(terrorScore,terrorCount);//恐怖症均分
        double paranoidAverage  = deciMal(paranoidScore,paranoidCount);//偏执症状均分
        double psychosisAverage  = deciMal(psychosisScore,psychosisCount);//精神病均分
        double otherAverage  = deciMal(otherScore,otherCount);//其他症均分
        double positiveAverage  = deciMal(positiveScore,positiveCount);//阳性均分

        BigDecimal allAverageScoreBD = new BigDecimal(allAverageScore);//总均分
        BigDecimal somatizationAverageBD = new BigDecimal(somatizationAverage);//躯体化症状均分
        BigDecimal obsessionAverageBD = new BigDecimal(obsessionAverage);//强迫症均分
        BigDecimal interpersonalAverageBD = new BigDecimal(interpersonalAverage);//人际关系均分
        BigDecimal depressedAverageBD = new BigDecimal(depressedAverage);//抑郁症均分
        BigDecimal anxiousAverageBD = new BigDecimal(anxiousAverage);//焦虑均分
        BigDecimal hostileAverageBD = new BigDecimal(hostileAverage);//敌对症状均分
        BigDecimal terrorAverageBD = new BigDecimal(terrorAverage);//恐怖症均分
        BigDecimal paranoidAverageBD = new BigDecimal(paranoidAverage);//偏执症状均分
        BigDecimal psychosisAverageBD = new BigDecimal(psychosisAverage);//精神病均分
        BigDecimal otherAverageBD = new BigDecimal(otherAverage);//其他症均分
        BigDecimal positiveAverageBD = new BigDecimal(positiveAverage);//阳性均分

        String score_ALL_AVERAGE = allAverageScore+"";//总均分
        String count_ALL_POSITIVENUM = positiveCount + "";//阳性项目数
        String score_ALL_POSITIVEAVERAGE = deciMal(positiveScore,positiveCount) +"";//阳性症状均分
        String score_PART_SOMATIZATION =somatizationAverage+"";//躯体化均分
        String score_PART_OBSESSION =obsessionAverage+"";//强迫症状均分
        String score_PART_INTERPERSONAL =interpersonalAverage+"";//人际敏感均分
        String score_PART_DEPRESSED =depressedAverage+"";//抑郁均分
        String score_PART_ANXIOUS =anxiousAverage+"";//焦虑均分
        String score_PART_HOSTILE =hostileAverage+"";//敌对均分
        String score_PART_TERROR =terrorAverage+"";//恐怖均分
        String score_PART_PARANOID =paranoidAverage+"";//偏执均分
        String score_PART_PSYCHOSIS =psychosisAverage+"";//精神病性均分
        String score_PART_OTHER =otherAverage+"";//其他均分
        String desc_ALL_AVERAGE ="";//总均分描述
        String symptomDesc_PART_SOMATIZATION ="";//躯体化描述
        String symptomDesc_PART_OBSESSION ="";//强迫症状描述
        String symptomDesc_PART_INTERPERSONAL ="";//人际关系敏感描述
        String symptomDesc_PART_DEPRESSED ="";//抑郁描述
        String symptomDesc_PART_ANXIOUS ="";//焦虑描述
        String symptomDesc_PART_HOSTILE ="";//敌对描述
        String symptomDesc_PART_TERROR ="";//恐怖描述
        String symptomDesc_PART_PARANOID ="";//偏执描述
        String symptomDesc_PART_PSYCHOSIS ="";//精神性病描述
        String symptomDesc_PART_OTHER ="";//其他描述
        //获取所有的心理健康测评数据
        Map<String,Object> map = new HashMap<String,Object>();
        List<TbPaPhReport> resultList = paPhReportService.getAll(map);
        for(TbPaPhReport pa: resultList){
            BigDecimal begin = pa.getStandardValueBgn();
            BigDecimal end = pa.getStandardValueEnd();
            String code = pa.getSymptomTypeCode();
            String symptomDesc = pa.getSymptomDesc();
            if(PA_PH_ALL_ALL_AVERAGE.getCode().equals(code)){//总症状
                 if(allAverageScoreBD.compareTo(begin) > 0 && allAverageScoreBD.compareTo(end) < 0 ){
                     desc_ALL_AVERAGE = symptomDesc;
                 }
             }
             if(PA_PH_SOMATIZATION.getCode().equals(code)){//躯体化
                if(somatizationAverageBD.compareTo(begin) > 0 && somatizationAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_SOMATIZATION  = symptomDesc;
                }
             }
            if(PA_PH_OBSESSION.getCode().equals(code)){//强迫症
                if(obsessionAverageBD.compareTo(begin) > 0 && obsessionAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_OBSESSION  = symptomDesc;
                }
            }
            if(PA_PH_INTERPERSONAL.getCode().equals(code)){//人际关系
                if(interpersonalAverageBD.compareTo(begin) > 0 && interpersonalAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_INTERPERSONAL  = symptomDesc;
                }
            }
            if(PA_PH_DEPRESSED.getCode().equals(code)){//抑郁
                if(depressedAverageBD.compareTo(begin) > 0 && depressedAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_DEPRESSED  = symptomDesc;
                }
            }
            if(PA_PH_ANXIOUS.getCode().equals(code)){//焦虑
                if(anxiousAverageBD.compareTo(begin) > 0 && anxiousAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_ANXIOUS  = symptomDesc;
                }
            }
            if(PA_PH_HOSTILE.getCode().equals(code)){//敌对
                if(hostileAverageBD.compareTo(begin) > 0 && hostileAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_HOSTILE  = symptomDesc;
                }
            }
            if(PA_PH_TERROR.getCode().equals(code)){//恐怖
                if(terrorAverageBD.compareTo(begin) > 0 && terrorAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_TERROR  = symptomDesc;
                }
            }
            if(PA_PH_PARANOID.getCode().equals(code)){//偏执
                if(paranoidAverageBD.compareTo(begin) > 0 && paranoidAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_PARANOID  = symptomDesc;
                }
            }
            if(PA_PH_PSYCHOSIS.getCode().equals(code)){//精神性病
                if(psychosisAverageBD.compareTo(begin) > 0 && psychosisAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_PSYCHOSIS  = symptomDesc;
                }
            }
            if(PA_PH_OTHER.getCode().equals(code)){//其他
                if(otherAverageBD.compareTo(begin) > 0 && otherAverageBD.compareTo(end)< 0){
                    symptomDesc_PART_OTHER  = symptomDesc;
                }
            }
        }
         // ④。 (情绪能力测评) 在 tb_cust_test_paper 取值 TEST_TYPE_CODE = PA_EC 找到 QUESTION_IDS 所有的问题
         // 通过 循环 tb_pa_answer_rule（规则表）得出相应的总分得分，在tb_pa_ec_report表 里分数进行匹配（根据文件规则进行相应的类型去匹配）
        TbCustTestResult paEcResult = custTestResultService.getTbCustTestResultByTestPaperId(paEcReport.getId());
        String paEcQuestionIds = paEcReport.getQuestionIds();//获取试题集
        String[] paEcSplit1 = paEcQuestionIds.split("@");
        List<String> paEcStrings = Arrays.asList(paEcSplit1);//试题集合
        String paEcTestResult = paEcResult.getTestResult(); //获取测试结果集（以"@"区分开）
        String[] paEcsplit2 = paEcTestResult.split("@");
        List<String> paEcStrings1 = Arrays.asList(paEcsplit2);//测试结果集合
        int totalScore1 = 0;
        for(int i = 0; i< paEcStrings.size() ;i++) {
            //通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
            Integer questionId = Integer.parseInt(paEcStrings.get(i));
            List<TbPaAnswerRule> answerList = paAnswerRuleService.getPaAnswerRuleByQuestionId(questionId);
            for (TbPaAnswerRule pa : answerList) {
                String s = paEcStrings1.get(i);//测试结果
                String answerBitmap = pa.getAnswerBitmap(); //标准答案
                if (answerBitmap.equals(s)) {
                    totalScore1 += Integer.parseInt(pa.getAnswerAnalysis());
                }
            }
        }
        String pa_ec_score = totalScore1 +"";//需要替换
        String sectionDesc = "";//需要替换
        List<TbPaEcReport> paEcList =  paEcReportService.getAll(map);
        for(TbPaEcReport pa : paEcList){
               if(totalScore1 > pa.getStandardValueBgn() &&  totalScore1 < pa.getStandardValueEnd()){
                   sectionDesc = pa.getSectionDesc();
               }
        }

         // ⑤。 (职业价值观测评) 在 tb_cust_test_paper 取值 TEST_TYPE_CODE = PA_CA 找到 QUESTION_IDS 所有的问题
         // 通过 循环 tb_pa_answer_rule（规则表）得出相应的枚举（RULe）各个类型的得分，tb_pa_ca_report 里进行匹配拿出数据进行替换（根据文件规则进行相应的类
        TbCustTestResult paCaResult = custTestResultService.getTbCustTestResultByTestPaperId(paCaReport.getId());
        String paCaQuestionIds = paCaReport.getQuestionIds();//获取试题集
        String[] paCaSplit1 = paCaQuestionIds.split("@");
        List<String> paCaStrings = Arrays.asList(paCaSplit1);//试题集合
        String paCaTestResult = paCaResult.getTestResult(); //获取测试结果集（以"@"区分开）
        String[] paCasplit2 = paCaTestResult.split("@");
        List<String> paCaStrings1 = Arrays.asList(paCasplit2);//测试结果集合
        int tfScore = 0;//TF分数
        int gmScore = 0;//GM分数
        int auScore = 0;//AU分数
        int seScore = 0;//SE分数
        int ecScore = 0;//EC分数
        int svScore = 0;//SV分数
        int chScore = 0;//CH分数
        int lsScore = 0;//LS分数
        for(int i = 0; i< paCaStrings.size() ;i++) {
            //通过试题编号 从测评解读表（tb_pa_answer_rule）中取出相应的解析
            Integer questionId = Integer.parseInt(paCaStrings.get(i));
            List<TbPaAnswerRule> answerList = paAnswerRuleService.getPaAnswerRuleByQuestionId(questionId);
            for (TbPaAnswerRule pa : answerList) {
                String s = paCaStrings1.get(i);//测试结果
                String answerBitmap = pa.getAnswerBitmap(); //标准答案
                if (answerBitmap.equals(s)) {
                    if(PA_CA_TF.getCode().equals(pa.getRuleTypeCode())){
                        tfScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_CA_GM.getCode().equals(pa.getRuleTypeCode())){
                        gmScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_CA_AU.getCode().equals(pa.getRuleTypeCode())){
                        auScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_CA_SE.getCode().equals(pa.getRuleTypeCode())){
                        seScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_CA_EC.getCode().equals(pa.getRuleTypeCode())){
                        ecScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_CA_SV.getCode().equals(pa.getRuleTypeCode())){
                        svScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_CA_CH.getCode().equals(pa.getRuleTypeCode())){
                        chScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                    if(PA_CA_LS.getCode().equals(pa.getRuleTypeCode())){
                        lsScore += Integer.parseInt(pa.getAnswerAnalysis());
                    }
                }
            }
        }
        Map<String,Integer> map2 = new HashMap<String,Integer>();
        map2.put("TF",tfScore);
        map2.put("GM",gmScore);
        map2.put("AU",auScore);
        map2.put("SE",seScore);
        map2.put("EC",ecScore);
        map2.put("SV",svScore);
        map2.put("CH",chScore);
        map2.put("LS",lsScore);
        String diskMax = getDiskMax(map2);
        String star1 = "";
        String star2 = "";
        String star3 = "";
        String star4 = "";
        String star5 = "";
        String star6 = "";
        String star7 = "";
        String star8 = "";
        if("TF".equals(diskMax)){
            star1 = "★";
        }
        if("GM".equals(diskMax)){
            star2 = "★";
        }
        if("AU".equals(diskMax)){
            star3 = "★";
        }
        if("SE".equals(diskMax)){
            star4 = "★";
        }
        if("EC".equals(diskMax)){
            star5 = "★";
        }
        if("SV".equals(diskMax)){
            star6 = "★";
        }
        if("CH".equals(diskMax)){
            star7 = "★";
        }
        if("LS".equals(diskMax)){
            star7 = "★";
        }
        String html = PdfUtil.getHtml();
        //替换模板中的字符
        
        // ⑥。 (个人发展建议)（目前是写死的） 在 tb_cust_test_paper 取值 TEST_TYPE_CODE = PA_CA 找到 QUESTION_IDS 所有的问题
         // 通过 循环 tb_pa_answer_rule（规则表）得出相应的枚举（RULe）各个类型的得分，tb_pa_ca_report 里进行匹配拿出数据进行替换（根据文件规则进
         //3。通过PdfUtil的getHtml方法 读出的是SB，替换相应的值

    }

    /**
     * 两个int相除保留两位小数
     * @param top
     * @param below
     * @return
     */
    private double deciMal(int top, int below) {
        double result = new BigDecimal((float)top / below).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return result;
    }

    /**
     * 求Map里的最大值并且返回key
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
            max = (max>size)?max:size;
        }
        for (String key : map.keySet()) {
            if (max == map.get(key)) {
                return key;
            }
        }
        return null;
    }

}

