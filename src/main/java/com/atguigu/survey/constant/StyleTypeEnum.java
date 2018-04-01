package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 行为风格类型类型枚举
 */
public enum StyleTypeEnum{
	
	ESTP("ESTP", "创业者"),
	ESTJ("ESTJ", "监督者"),
	ENFJ("ENFJ", "领导者"),
	ENTJ("ENTJ", "领袖者"),
	ESFP("ESFP", "表演者"),
	ESFJ("ESFJ", "供应者"),
	ENFP("ENFP", "奋斗者"),
	ENTP("ENTP", "发明家"),
	ISTP("ISTP", "手艺者"),
	ISTJ("ISTJ", "检查者"),
	INFJ("INFJ", "劝告者"),
	INTJ("INTJ", "策划者"),
	ISFP("ISFP", "创作者"),
	ISFJ("ISFJ", "保护者"),
	INFP("INFP", "化解者"),
	INTP("INTP", "思想家");
	
    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private StyleTypeEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (StyleTypeEnum c : StyleTypeEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getNCode(String name) {  
        for (StyleTypeEnum c : StyleTypeEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (StyleTypeEnum all : StyleTypeEnum.values()) {
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
