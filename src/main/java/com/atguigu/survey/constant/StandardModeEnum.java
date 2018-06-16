package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 标准值计算方式枚举
 */
public enum StandardModeEnum{
	
	SCORE("SCORE", "分数"),
	COUNT("COUNT", "数量");
	
    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private StandardModeEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (StandardModeEnum c : StandardModeEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getCode(String name) {  
        for (StandardModeEnum c : StandardModeEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (StandardModeEnum all : StandardModeEnum.values()) {
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
