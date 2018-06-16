package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 问题类型枚举
 */
public enum QuestionTypeEnum{
	
	RADIO("RADIO", "单选题"),
	CHECKBOX("CHECKBOX", "多选题"),
	JUDGE("JUDGE", "判断题");
	
    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private QuestionTypeEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (QuestionTypeEnum c : QuestionTypeEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getCode(String name) {  
        for (QuestionTypeEnum c : QuestionTypeEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (QuestionTypeEnum all : QuestionTypeEnum.values()) {
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
