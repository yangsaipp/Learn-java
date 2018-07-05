/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package performance;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月5日 杨赛
 */
public class TestObject {
	
	@PerformanceTest(name = "")
	public void test() throws InterruptedException {
		Thread.sleep(200);
	}
	
	@PerformanceTest(name = "")
	public void test2() throws InterruptedException {
		Thread.sleep(300);
	}
}
