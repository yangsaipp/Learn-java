package lock;

/**
 * 测试使用volatile的内存可见性
 * 结论：使用volatile来修饰变量比如下面例子中count++的情况下会出现线程安全问题，
 * 因为i++不是原子操作无法保证原子性，两个线程同时拿到最新值，同时进行+1,并写回主存。
 * @author yangsai
 */
public class VolatileMapLock {

	static class MyTask implements Runnable {
		
		public MyTask(String lockId) {
			super();
		}


		public void run() {
			// 不是原子操作
			count++;
		}
	}
	
	
	static Integer count = 0;
	public static void main(String[] args) throws InterruptedException {
		/**
		 * 若能保证同步，则只有一次put记录。结果是多次put记录
		 */
		for(int i=1; i<=200; i++) {
			new Thread(new MyTask("a"), "t"+i).start();
		}
		Thread.sleep(500);
		// 输出值小于200
		System.out.println(count);
	}

}
