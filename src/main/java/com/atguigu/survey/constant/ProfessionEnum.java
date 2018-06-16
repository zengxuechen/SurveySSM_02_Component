package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 职业类型枚举
 */
public enum ProfessionEnum{
	
	TF("TF", "技术/职能型TF"),
	GM("GM", "管理型GM"),
	AU("AU", "自主/独立型AU"),
	SE("SE", "安全/稳定型SE"),
	EC("EC", "创造/创业型EC"),
	SV("SV", "服务型SV"),
	CH("CH", "挑战型CH"),
	LS("LS", "生活型LS");

    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private ProfessionEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (ProfessionEnum c : ProfessionEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getCode(String name) {  
        for (ProfessionEnum c : ProfessionEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (ProfessionEnum all : ProfessionEnum.values()) {
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
