/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis;

import mybatis.internal.DefaultExecutor;
import mybatis.internal.Executor;
import mybatis.internal.plugin.PluginHelper;

import org.junit.Test;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class Main {

	/**
	 * 使用代理模式来实现插件，问题：插件实现会拦截所有方法。
	 */
	@Test
	public void test1() {
		Executor executor = new DefaultExecutor();
		// 增加插件功能。
		executor = (Executor) PluginHelper.wrap(executor);
		executor.query(null, null);
		System.out.println();
		System.out.println(executor.update(null, null));
	}

	/**
	 * 使用代理模式来实现插件，
	 */
	@Test
	public void test2() {
		Executor executor = new DefaultExecutor();
		// 增加插件功能。
		executor = (Executor) mybatis.internal.plugin2.PluginHelper.wrap(executor);
		executor.query(null, null);
		System.out.println();
		System.out.println(executor.update(null, null));
	}
	
	
	
}
