/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.custom.plugin2;

import mybatis.internal.Executor;
import mybatis.internal.plugin2.Interceptor;
import mybatis.internal.plugin2.Invocation;
import mybatis.internal.plugin2.annotation.Intercepts;
import mybatis.internal.plugin2.annotation.Signature;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
@Intercepts({ @Signature(type = Executor.class, method = "query", args = { String.class, Object.class}) })
public class Log2Interceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) {
		try {
			System.out.println("log2 before invoke....");
	        Object result = invocation.proceed();
	        System.out.println("log2 after invoke....");
			return result ;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	
}
