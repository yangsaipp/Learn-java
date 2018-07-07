/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package performance;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2018年7月5日 杨赛
 */
public class PerformanceTestRunner {

	private List<PerformanceTestObject> lstPerformanceTestObject;
	
	private Method reset;
	
	private Object o;
	
	public PerformanceTestRunner(
			List<PerformanceTestObject> lstPerformanceTestObject, Method reset,
			Object o) {
		super();
		this.lstPerformanceTestObject = lstPerformanceTestObject;
		this.reset = reset;
		this.o = o;
	}

	public static void main(String[] args) {
		run(new TestObject(), 3);
	}
	
	public static void run(Object o, int num)  {
		PerformanceTestRunner runner = createRunner(o);
		runner.resetTest();
		long startTime;
		for(PerformanceTestObject testObj : runner.lstPerformanceTestObject) {
			System.out.println();
			System.out.println("测试名：" + testObj.getTest().name());
			System.out.println("测试方法：" + testObj.getMethod());
			StringBuilder sb = new StringBuilder();
			long totalTime = 0;
			for(int i=0; i< num; i++) {
				startTime = System.currentTimeMillis();
				try {
					testObj.run();
				}catch (Exception e) {
					throw new RuntimeException(String.format("测试方法运行出错。方法：%s", testObj), e);
				}
				long time = System.currentTimeMillis() - startTime;
				sb.append(time);
				sb.append(" | ");
				totalTime  = totalTime + time;
				runner.resetTest();
			}
			sb.append(String.format("平均时间：%d", totalTime / num));
			System.out.println(sb);
		}
	}

	private void resetTest()
			 {
		if(reset != null) {
			try {
				reset.invoke(o);
			}catch (Exception e) {
				throw new RuntimeException(String.format("重置测试环境出错。方法：%s", reset), e);
			}
			
		}
	}

	/**
	 * @param o
	 * @return
	 */
	private static PerformanceTestRunner createRunner(Object o) {
		List<PerformanceTestObject> lstPerformanceTestObject = new ArrayList<PerformanceTestObject>();
		Method reset = null;
		for(Method m : o.getClass().getMethods()) {
			PerformanceTest anTest = m.getAnnotation(PerformanceTest.class);
			if(anTest != null) {
				lstPerformanceTestObject.add(new PerformanceTestObject(m, o, anTest));
			}
			TestReset anRest = m.getAnnotation(TestReset.class);
			if(anRest != null) {
				reset = m;
			}
		}
		return new PerformanceTestRunner(lstPerformanceTestObject, reset, o);
	}
}
