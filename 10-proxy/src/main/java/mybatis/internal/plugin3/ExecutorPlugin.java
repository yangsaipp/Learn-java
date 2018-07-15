/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.internal.plugin3;

import java.util.List;

import mybatis.internal.Executor;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月12日 杨赛
 */
public abstract class ExecutorPlugin extends Plugin<Executor> implements Executor{

	/**
	 * 构造函数
	 * @param target
	 */
	public ExecutorPlugin(Executor targetExecutor) {
		super(targetExecutor);
	}

	/**
	 * 
	 * @see mybatis.internal.Executor#query(java.lang.String, java.lang.Object)
	 */
	@Override
	public <E> List<E> query(String statementId, Object paramter) {
		return target.query(statementId, paramter);
	}

	/**
	 * 
	 * @see mybatis.internal.Executor#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(String statementId, Object paramter) {
		return target.update(statementId, paramter);
	}

}
