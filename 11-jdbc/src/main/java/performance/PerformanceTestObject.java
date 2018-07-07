/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package performance;

import java.lang.reflect.Method;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月5日 杨赛
 */
public class PerformanceTestObject {
	
	private Method method;
	
	private Object o;
	
	private PerformanceTest test;
	
	/**
	 * 构造函数
	 * @param method
	 * @param o
	 */
	public PerformanceTestObject(Method method, Object o, PerformanceTest test) {
		this.method = method;
		this.o = o;
		this.test = test;
	}

	public void run() throws Exception {
		method.invoke(o);
	}

	public String getMethodName() {
		return method.getName();
	}

	/**
	 * @return 获取 method属性值
	 */
	public Method getMethod() {
		return method;
	}

	/**
	 * @param method 设置 method 属性值为参数值 method
	 */
	public void setMethod(Method method) {
		this.method = method;
	}

	public PerformanceTest getTest() {
		return test;
	}

	public void setTest(PerformanceTest test) {
		this.test = test;
	}

}
