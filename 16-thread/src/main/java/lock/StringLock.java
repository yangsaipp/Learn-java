package lock;

import java.util.Date;

/**
 * 测试内置锁且锁的对象是String对象时是否会出现同步执行的现象
 * 结论：不建议使用String对象当锁，因为使用String对象的话需要用到String.intern，该方法会不断往常量池里加对象，导致持久代内存溢出
 * @author yangsai
 *
 */
public class StringLock {

	static class MyTask implements Runnable {
		private String lock;
		
		public MyTask(String lock) {
			super();
			this.lock = lock;
		}


		public void run() {
			System.out.printf("[%s] %s\n",Thread.currentThread().getName(),lock);
			// 必须使用intern方法，否则new String("aa")和"aa"就是两个对象引用，不会同步执行。
			// 原因：intern就是返回常量池中对应字符串引用地址，若没有则在常量池中增加。故这里统一使用的是字符串常量池的引用地址
			// 这样会导致常量池越来越大，可能会导致持久代内存溢出。
			synchronized (lock.intern()) {
				try {
					Thread.sleep(3000);
					System.out.printf("%tc %s [%s] :%s\n",new Date(), Thread.currentThread().getName(), lock, "MyTask running");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		String lock = "aa";
		System.out.println(lock);
		new Thread(new MyTask("aa"), "t1").start();
		new Thread(new MyTask(lock), "t2").start();
		new Thread(new MyTask("a"+"a"), "t3").start();
		new Thread(new MyTask("aa"+""), "t4").start();
		new Thread(new MyTask(new StringBuilder().append("a").append("a").toString()), "t5").start();
		new Thread(new MyTask(new String("aa")), "t6").start();
	}

}
