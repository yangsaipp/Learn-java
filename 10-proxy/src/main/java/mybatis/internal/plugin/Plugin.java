/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.internal.plugin;

import java.lang.reflect.InvocationHandler;

/**
 * 插件对象，通过动态代理模式，对需要处理的方法进行控制。
 * 
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public abstract class Plugin implements InvocationHandler {

	 //　这个就是我们要代理控制的真实对象
	protected Object target;
	
	
	public Plugin(Object target) {
		super();
		this.target = target;
	}
}
