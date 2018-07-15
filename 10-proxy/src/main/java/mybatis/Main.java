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
	 * 使用代理模式来实现插件，现状插件实现会拦截所有方法。带来的问题：
	 * 1. 插件实现者在实现时需要处理被拦截对象的所有被调用方法时的逻辑。
	 * 2. 后续若是被拦截的类增加新方法，会影响已有的插件逻辑。
	 * 总之，这种拦截类所有方法的方式，需要插件实现者严密的实现逻辑，清楚的知道此时的逻辑道理是拦截的那个类方法，否则就容易出现问题。
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
	 * 使用代理模式来实现插件，只拦截指定的方法。
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
	
	/**
	 * 使用代理模式来实现插件，只拦截指定的方法。一个拦截对象一个插件类。
	 */
	@Test
	public void test3() {
		Executor executor = new DefaultExecutor();
		// 增加插件功能。
		executor = (Executor) mybatis.internal.plugin3.PluginHelper.wrap(executor);
		executor.query(null, null);
		System.out.println();
		System.out.println(executor.update(null, null));
	}
	
	
}
