package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 测评试卷开关类型枚举
 */
public enum PaperOnFlagEnum{
	
	PAPER_ON_FLAG_Y("Y", "打开"),
	PAPER_ON_FLAG_N("CHECKBOX", "关闭");
	
    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private PaperOnFlagEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (PaperOnFlagEnum c : PaperOnFlagEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getCode(String name) {  
        for (PaperOnFlagEnum c : PaperOnFlagEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (PaperOnFlagEnum all : PaperOnFlagEnum.values()) {
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
