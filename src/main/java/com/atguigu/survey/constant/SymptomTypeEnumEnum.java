package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 心理症状类型枚举
 */
public enum SymptomTypeEnumEnum{
	
	ALL_AVERAGE("ALL_AVERAGE", "总症状_总均分"),
	ALL_POSITIVENUM("ALL_POSITIVENUM", "总症状_阳性项目数"),
	ALL_POSITIVEAVERAGE("ALL_POSITIVEAVERAGE", "总症状_阳性症状均分"),
	PART_SOMATIZATION("PART_SOMATIZATION", "分症状均分_躯体化"),
	PART_OBSESSION("PART_OBSESSION", "分症状均分_强迫症状"),
	PART_INTERPERSONAL("PART_INTERPERSONAL", "分症状均分_人际关系敏感"),
	PART_DEPRESSED("PART_DEPRESSED", "分症状均分_抑郁"),
	PART_ANXIOUS("PART_ANXIOUSN", "分症状均分_焦虑"),
	PART_HOSTILE("PART_HOSTILEN", "分症状均分_敌对"),
	PART_TERROR("PART_TERROR", "分症状均分_恐怖"),
	PART_PARANOID("PART_PARANOID", "分症状均分_偏执"),
	PART_PSYCHOSIS("PART_PSYCHOSIS", "分症状均分_精神病性"),
	PART_OTHER("PART_OTHER", "分症状均分_其他（睡眠及饮食情况）");
	
    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private SymptomTypeEnumEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (SymptomTypeEnumEnum c : SymptomTypeEnumEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getNCode(String name) {  
        for (SymptomTypeEnumEnum c : SymptomTypeEnumEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (SymptomTypeEnumEnum all : SymptomTypeEnumEnum.values()) {
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
