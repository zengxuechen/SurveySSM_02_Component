package com.atguigu.survey.ehcache;

import java.lang.reflect.Method;
import org.springframework.cache.interceptor.KeyGenerator;
import com.atguigu.survey.utils.DataProcessUtils;

/**
 * 缓存区Map的key生成器
 */
public class SurveyKeyGenerator implements KeyGenerator {

	public Object generate(Object target, Method method, Object... params) {

		StringBuilder builder = new StringBuilder();

		// 目标对象的全类名
		String targetClassName = target.getClass().getName();
		builder.append(targetClassName).append(".");

		// 目标方法的名称
		String methodName = method.getName();
		builder.append(methodName).append(".");

		// 参数名称
		for (Object object : params) {
			builder.append(object).append(".");
		}

		String key = DataProcessUtils.removeComma(builder.toString(), ".");

		System.out.println(this.getClass() + " - key : " + key);

		return key;
	}

}
