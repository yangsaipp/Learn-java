/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.custom.plugin;

import java.lang.reflect.Method;

import mybatis.internal.plugin.Plugin;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class Log2Plugin extends Plugin {
	

	public Log2Plugin(Object target) {
		super(target);
	}

	/**
	 * 
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("log2 before invoke....");
        Object result = method.invoke(target, args);
        System.out.println("log2 after invoke....");
		return result ;
	}

	
}
