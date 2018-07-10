/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package mybatis.internal.plugin;

import mybatis.custom.plugin.LogPlugin;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月10日 杨赛
 */
public class Plugin {

	/**
	 * 在对应的对象上增加对应插件逻辑 
	 * @param executor
	 * @return
	 */
	public static Object wrap(Object obj) {
		Object wrapObj = LogPlugin.wrap(obj);
//		Object wrapObj = otherPlugin.wrap(wrapObj);
		return wrapObj;
	}

}
