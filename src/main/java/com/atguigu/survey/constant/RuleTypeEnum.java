package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 指标类型枚举
 */
public enum RuleTypeEnum{
	
	PA_PC_EI("PA_PC_EI", "人才测评_职业性格_态度倾向"),
	PA_PC_SN("PA_PC_SN", "人才测评_职业性格_接受信息"),
	PA_PC_TF("PA_PC_TF", "人才测评_职业性格_处理信息"),
	PA_PC_JP("PA_PC_JP", "人才测评_职业性格_行动方式"),
	PA_PH_SOMATIZATION("PA_PH_SOMATIZATION", "人才测评_心理健康_躯体化"),
	PA_PH_OBSESSION("PA_PH_OBSESSION", "人才测评_心理健康_强迫症状"),
	PA_PH_INTERPERSONAL("PA_PH_INTERPERSONAL", "人才测评_心理健康_人际关系敏感"),
	PA_PH_DEPRESSED("PA_PH_DEPRESSED", "人才测评_心理健康_抑郁"),
	PA_PH_ANXIOUS("PA_PH_ANXIOUS", "人才测评_心理健康_焦虑"),
	PA_PH_HOSTILE("PA_PH_HOSTILE", "人才测评_心理健康_敌对"),
	PA_PH_TERROR("PA_PH_TERROR", "人才测评_心理健康_恐怖"),
	PA_PH_PARANOID("PA_PH_PARANOID", "人才测评_心理健康_偏执"),
	PA_PH_PSYCHOSIS("PA_PH_PSYCHOSIS", "人才测评_心理健康_精神病性"),
	PA_PH_OTHER("PA_PH_OTHER", "人才测评_心理健康_其他（睡眠及饮食情况）"),
	PA_EC("PA_EC", "人才测评_情绪能力"),
	PA_CA_TF("PA_CA_TF", "人才测评_职业锚_技术/职能型"),
	PA_CA_GM("PA_CA_GM", "人才测评_职业锚_管理型"),
	PA_CA_AU("PA_CA_AU", "人才测评_职业锚_自主/独立型"),
	PA_CA_SE("PA_CA_SE", "人才测评_职业锚_安全/稳定型"),
	PA_CA_EC("PA_CA_EC", "人才测评_职业锚_创造/创业型"),
	PA_CA_SV("PA_CA_SV", "人才测评_职业锚_服务型"),
	PA_CA_CH("PA_CA_CH", "人才测评_职业锚_挑战型"),
	PA_CA_LS("PA_CA_LS", "人才测评_职业锚_生活型");
	
    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private RuleTypeEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (RuleTypeEnum c : RuleTypeEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getNCode(String name) {  
        for (RuleTypeEnum c : RuleTypeEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (RuleTypeEnum all : RuleTypeEnum.values()) {
        	MAP.put(all.getCode(), all.getName());
        }
    }
    
    // getter
    public String getName() {
		return name;
	}

    // getter
	public String getCode() {
		return code;
	}
}
