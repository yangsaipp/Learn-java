package volatile_Test;

/**
 * 200个线程同时进行i++操作，i使用Volatile修饰
 * 测试结果：输出998、999、1000
 * 结论：不保证原子性
 * @author yangsai
 */
public class NumberPlusTest extends Thread {
	
	volatile static Integer count = 0;
	
	static class MyTask implements Runnable {

		public MyTask(String lockId) {
			super();
		}

		public void run() {
			// 不是原子操作
			count++;
		}
	}


	public static void main(String[] args) throws InterruptedException {

		for (int i = 1; i <= 1000; i++) {
			new Thread(new MyTask("a"), "t" + i).start();
		}
		Thread.sleep(500);
		// 输出值小于200
		System.out.println(count);
	}

}