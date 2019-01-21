/******************************************************************************
* Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
* All Rights Reserved.
* 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体不得使用、
* 复制、修改或发布本软件.
*****************************************************************************/

package CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author  杨赛
 * @since   jdk1.7
 * @version 2019年1月16日 杨赛
 */
public class CountDownLatchTest {
	
	
	public static void main(String[] args) throws InterruptedException {
		CountDownLatch countDownLatch = new CountDownLatch(1);
		new Thread(new MyTask(countDownLatch), "1").start();
		new Thread(new MyTask(countDownLatch), "2").start();
		Thread.sleep(4000);
		new Thread(new MyTask(countDownLatch), "3").start();
		Thread.sleep(4000);
		countDownLatch.countDown();
		new Thread(new MyTask(countDownLatch), "4").start();
		Thread.sleep(4000);
	}
	
	static class MyTask implements Runnable {
		private CountDownLatch countDownLatch;
		
		/**
		 * 构造函数
		 * @param countDownLatch
		 */
		public MyTask(CountDownLatch countDownLatch) {
			this.countDownLatch = countDownLatch;
		}

		/**
		 * 
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			try {
				System.out.println(Thread.currentThread().getName() + " : await. ");
				boolean notTimeout = countDownLatch.await(5, TimeUnit.SECONDS);
				if(notTimeout) {
					System.out.println("未超时");
				}else{
					System.out.println("超时");
				}
			} catch (InterruptedException e) {
				System.out.println("异常");
				e.printStackTrace();
			}
			
		}
	}
}
