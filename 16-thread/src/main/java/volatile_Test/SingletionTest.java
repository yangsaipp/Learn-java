/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package volatile_Test;

/**
 * 测试单例模式是否需要使用Volatile
 * 不使用volatile，第一次判断返回的instance是未初始化完毕的对象从而报错？（无法重现）
 * 初始化之后，其他线程修改单例对象，非修改线程能及时看到变化？不使用Volatile的话无法及时看到
 * @author  杨赛
 * @since   jdk1.7
 * @version 2019年1月23日 杨赛
 */
public class SingletionTest {

	// 利用volatile的禁止指令重排序特性，防止第一次判断返回的instance是未初始化完毕的对象从而报错
//	private volatile static SingletionObject instance;
	
	private static SingletionObject instance;
	
	public static SingletionObject getInstance() {
		if(instance != null) { // 第一次检查
			return instance;
		}
		synchronized (SingletionTest.class) {
			if(instance != null) {	//第二次检查
				return instance;
			}
			// （1）分配内存空间。
            // （2）将内存空间的地址赋值给对应的引用。
            // （3）初始化对象
			instance = new SingletionObject("sdfasdfas");
			return instance;
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		for(int i=1; i<100; i++) {
			new Thread(new Runnable() {
				public void run() {
					SingletionObject obj = getInstance();
					System.out.println(obj.getDataSourceId());
				}
			}, "test-thread-" + i).start();;
		}
		Thread.sleep(1000);
	}
	
}
