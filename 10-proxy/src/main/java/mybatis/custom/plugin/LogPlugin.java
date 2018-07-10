/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.custom.plugin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class LogPlugin implements InvocationHandler {
	
	/**
	 * 在原有类方法上增加日志记录。
	 * @param obj
	 * @return
	 */
	public static Object wrap(Object obj) {
		LogPlugin logPlugin = new LogPlugin(obj);
		return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), logPlugin);
	}

	//　这个就是我们要代理的真实对象
    private Object subject;
	
	/**
	 * 构造函数
	 * @param subject
	 */
	public LogPlugin(Object subject) {
		this.subject = subject;
	}

	/**
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("log before invoke....");
        Object result = method.invoke(subject, args);
        System.out.println("log after invoke....");
		return result ;
	}

	
}
