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
 * 由于需求会变化，所以一般程序或者软件都会提供一种扩展机制来应对需求的变化。插件就是程序对外提供扩展的一个实现方式。
 * mybaties中的插件机制就可以让用户在几个关键的点进行扩展。比如说翻页插件可以在业务SQL上加上翻页SQL。
 * 
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class Main {

	/**
	 * 使用代理模式直接实现插件。带来的问题：
	 * 这种拦截类所有方法的方式，需要插件实现者额外处理不需要被拦截的方法，同时还需考虑下个版本拦截的方法可能会变多。若是实现不当，会导致拦截到未期望的方法。
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
	 * 改进方式一：使用代理模式来实现插件，只拦截指定的方法。
	 * 该方式也是mybaties的实现方式。该方式扩展者只需实现一个拦截器，在拦截器上申明要拦截的对象和方法即可。该方式下一个拦截器可拦截多个对象里的多个方法。
	 * 优点：只会拦截指定的方法，只需要实现一个拦截器即可实现多个插件功能
	 * 缺点：拦截器里需要自行判断拦截的对象和方法，若插件功能需要拦截多个对象，则拦截器得实现逻辑会比较复杂。
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
	 * 改进方式二：使用代理模式来实现插件，只拦截指定的方法。
	 * 该方式扩展者需要继承拦截对象的对应的插件类，实现时只需重写实现需要拦截的方法即可。
	 * 优点：只会拦截指定的方法，实现插件时无需判断要拦截的对象和方法。
	 * 缺点：每个插件都需要编写一个类继承对拦截对象对应的插件类。若一个插件需要拦截多个对象，则需要编写多个类->最终加载的插件类较多，维护较麻烦。
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
