package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 测试类型枚举
 */
public enum TestTypeEnum{
	
	PA_PC("PA_PC", "人才测评_职业性格"),
	PA_PH("PA_PH", "人才测评_心理健康"),
	PA_EC("PA_EC", "人才测评_情绪能力"),
	PA_CA("PA_CA", "人才测评_职业锚"),
	MN_PT("MN_PT", "管理测评_管理潜力");
	
    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private TestTypeEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (TestTypeEnum c : TestTypeEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getNCode(String name) {  
        for (TestTypeEnum c : TestTypeEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (TestTypeEnum all : TestTypeEnum.values()) {
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
