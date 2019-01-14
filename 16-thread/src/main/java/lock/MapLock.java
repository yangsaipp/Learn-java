package lock;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 测试使用Map来存放锁对象是否能保证同步
 * 结论：HashMap与ConcurrentHashMap都不能保证同步存放锁对象，存在并发安全问题。
 * @author yangsai
 */
public class MapLock {

	static class MyTask implements Runnable {
		private String lockId;
		
		public MyTask(String lockId) {
			super();
			this.lockId = lockId;
		}


		public void run() {
			if(mapLock.containsKey(lockId)) {
				System.out.printf("%tc %s [has] :%s\n",new Date(), Thread.currentThread().getName(), lockId);
			}else {
				System.out.printf("%tc %s [+put] :%s\n",new Date(), Thread.currentThread().getName(), lockId);
				mapLock.put(lockId, new MyLock(lockId));
			}
		}
	}
	
	static class MyLock {
		
		private String id;

		public MyLock(String id) {
			super();
			this.id = id;
		}
	}
	
	static Map<String, MyLock> mapLock = new ConcurrentHashMap<>();
	public static void main(String[] args) {
		/**
		 * 若能保证同步，则只有一次put记录。结果是多次put记录
		 */
		for(int i=1; i<=100; i++) {
			new Thread(new MyTask("a"), "t"+i).start();
		}
	}

}
