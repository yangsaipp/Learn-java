/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.custom.plugin3;

import java.util.List;

import mybatis.internal.Executor;
import mybatis.internal.plugin3.ExecutorPlugin;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class LogPlugin extends ExecutorPlugin {
	
	/**
	 * 构造函数
	 * @param targetExecutor
	 */
	public LogPlugin(Executor targetExecutor) {
		super(targetExecutor);
	}

	@Override
	public <E> List<E> query(String statementId, Object paramter) {
		System.out.println("log before invoke....");
		List<E> result =super.query(statementId, paramter);
		System.out.println("log after invoke....");
		return result ;
	}

	
}
