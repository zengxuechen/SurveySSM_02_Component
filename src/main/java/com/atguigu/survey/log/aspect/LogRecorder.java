package com.atguigu.survey.log.aspect;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;

import com.atguigu.survey.component.service.i.LogService;
import com.atguigu.survey.entities.guest.User;
import com.atguigu.survey.entities.manager.Admin;
import com.atguigu.survey.entities.manager.Log;
import com.atguigu.survey.log.router.LogRouterDataSource;
import com.atguigu.survey.log.router.RouterKeyBinder;
import com.atguigu.survey.log.thread.local.RequestBinder;
import com.atguigu.survey.utils.GlobalNames;

/**
 * 日志切面类：记录系统日志
 */
public class LogRecorder {

	@Autowired
	private LogService logService;

	public Object logRecord(ProceedingJoinPoint joinPoint) throws Throwable {

		// 获取目标方法的参数
		Object[] args = joinPoint.getArgs();
		Signature signature = joinPoint.getSignature();

		Object resultObject = null;

		String exceptionType = null;
		String exceptionMessage = null;
		try {

			// 调用目标方法
			resultObject = joinPoint.proceed(args);

		} catch (Throwable e) {
			e.printStackTrace();

			exceptionType = e.getClass().toString();
			exceptionMessage = e.getMessage();

			// ******②获取异常消息*****************************************
			// 考虑异常可能是由于其他原因引起的，所以，需要获取异常的原因，并获取其消息
			Throwable cause = e.getCause();
			while (cause != null) { // 由于异常可能是多层的，所以，异常原因可能还有原因，所以需要使用循环
				exceptionType = cause.getClass().toString();
				exceptionMessage = cause.getMessage();
				cause = cause.getCause(); // 多次循环，只保留最后一次异常原因对象的信息

				// cause = e.getCause(); //这里的赋值，不能是e.getCause();否则，会造成无限死循环
			}

			/*
			 * 将while循环改成for循环，是不是也是可以的呢。 for (Throwable cause = e.getCause();
			 * cause!=null; cause = cause.getCause()) { exceptionType =
			 * cause.getClass().toString() ; exceptionMessage =
			 * cause.getMessage() ; }
			 */

			throw e;
		} finally {

			// ******③获取用户信息*****************************************
			HttpServletRequest request = RequestBinder.getRequset();
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(GlobalNames.LOGIN_USER);
			Admin admin = (Admin) session.getAttribute(GlobalNames.LOGIN_Admin);

			String userPart = (user == null) ? "User未登录" : user.getUserName();
			String adminPart = (admin == null) ? "Admin未登录" : admin
					.getAdminName();

			String operator = userPart + " / " + adminPart;

			// ******①收集常规消息*****************************************
			String operateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.format(new Date()).toString();
			String methodName = signature.getName();
			String methodType = signature.getDeclaringTypeName();
			String methodReturnValue = (resultObject == null) ? "无返回值"
					: resultObject.toString();
			String methodArgs = (args == null) ? "无参数" : Arrays.asList(args)
					.toString();

			// 创建日志对象
			Log log = new Log(null, operator, operateTime, methodName,
					methodType, methodReturnValue, methodArgs, exceptionType,
					exceptionMessage);

			// 保存实体对象
			// logService.saveEntity(log);

			// *********************************
			// 设置路由器数据源需要的key
			RouterKeyBinder.bindKey(LogRouterDataSource.DATASOURCE_LOG);

			// 数据分流——分表保存日志信息
			logService.saveLog(log);

		}

		// 将目标方法的返回结果继续返回给上层方法。
		return resultObject;
	}

}
