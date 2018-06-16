package com.atguigu.survey.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 曾雪晨
 * @comment 管理潜力类型枚举
 */
public enum ManagementPotentialEnum{
	
	MN_PT_TOTAL("MN_PT_TOTAL", "总体潜力分析"),
	MN_PT_TIME("MN_PT_TIME", "提高自己的时间回报"),
	MN_PT_OTHER("MN_PT_OTHER", "激发他人及培养他人"),
	MN_PT_MASTER("MN_PT_MASTER", "成为创意及执行大师"),
	MN_PT_SEARCH("MN_PT_SEARCH", "研究客户、对手及环境"),
	MN_PT_THINK("MN_PT_THINK", "提高思考及判断能力");

    // 成员变量  
	private String code;
    private String name;  
    
    // 构造方法  
    private ManagementPotentialEnum(String code, String name) {  
        this.name = name;  
        this.code = code;  
    }
    
    // 普通方法  
    public static String getName(String code) {  
        for (ManagementPotentialEnum c : ManagementPotentialEnum.values()) {  
            if (c.getCode().equals(code)) {  
                return c.name;  
            }  
        }  
        return null;
    }
    
    // 普通方法  
    public static String getCode(String name) {  
        for (ManagementPotentialEnum c : ManagementPotentialEnum.values()) {  
            if (c.getName().equals(name)) {  
                return c.code;  
            }  
        }  
        return null;
    }
    
    // 转成map
    private final static Map<String, String> MAP = new HashMap<String, String>();

    static {
        for (ManagementPotentialEnum all : ManagementPotentialEnum.values()) {
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
