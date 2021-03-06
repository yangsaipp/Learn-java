/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.internal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class DefaultExecutor implements Executor {

	/**
	 * 
	 * @see mybatis.internal.Executor#query(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<Object> query(String statementId, Object paramter) {
		System.out.println("Default query.");
		return new ArrayList<Object>();
	}

	/**
	 * 
	 * @see mybatis.internal.Executor#update(java.lang.String, java.lang.Object)
	 */
	@Override
	public int update(String statementId, Object paramter) {
		System.out.println("Default update.");
		return 22;
	}

}
