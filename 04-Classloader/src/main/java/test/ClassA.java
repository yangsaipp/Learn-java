/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package test;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2017年6月13日 杨赛
 */
public class ClassA {
	
	{
		System.out.println("ClassA");
	}
	
	private ClassB classB = new ClassB();

	/**
	 * @return 获取 classB属性值
	 */
	public ClassB getClassB() {
		return classB;
	}

	/**
	 * @param classB 设置 classB 属性值为参数值 classB
	 */
	public void setClassB(ClassB classB) {
		this.classB = classB;
	}
	
}
